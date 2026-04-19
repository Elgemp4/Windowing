package be.groupe18.windowing.strategies.build;

import static org.junit.jupiter.api.Assertions.*;

import be.groupe18.windowing.application.strategy.build.BuildStrategy;
import be.groupe18.windowing.application.strategy.build.RecursiveBuildStrategy;
import be.groupe18.windowing.application.strategy.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.application.strategy.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.application.strategy.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.domain.model.CompositeDouble;
import be.groupe18.windowing.domain.model.PST;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.domain.model.Vector2D;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RecursiveBuildTest {

  private BuildStrategy buildStrategy;
  private int[] batchSizes = { 10, 100, 200, 400, 1000, 10000, 100000 };

  @BeforeEach
  public void configureStrategies() {
    this.buildStrategy = new RecursiveBuildStrategy(
      new LinearMinimumStrategy<>(),
      new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>())
    );
  }

  private List<Segment> createSegmentListOf(
    int n,
    boolean isVertical,
    boolean duplicates
  ) {
    ArrayList<Segment> segments = new ArrayList<>();

    int origin = (int) Math.floor(Math.random() * n * 100);
    int intStart = (int) Math.floor(Math.random() * n * 100);
    int intEnd = (int) Math.floor(Math.random() * n * 100);

    for (int i = 0; i < n; i++) {
      if (!duplicates) {
        origin = (int) Math.floor(Math.random() * n * 100);
        intStart = (int) Math.floor(Math.random() * n * 100);
        intEnd = (int) Math.floor(Math.random() * n * 100);
      }

      if (isVertical) {
        segments.add(
          new Segment(
            new Vector2D(origin, intStart),
            new Vector2D(origin, intEnd)
          )
        );
      } else {
        segments.add(
          new Segment(
            new Vector2D(intStart, origin),
            new Vector2D(intEnd, origin)
          )
        );
      }
    }

    return segments;
  }

  @ParameterizedTest(
    name = "PST Heigh check | Vertical : {0}, Duplicates : {1}"
  )
  @CsvSource({ "true, true", "true, false", "false, true", "false, false" })
  public void checkTreeHeight(boolean vertical, boolean duplicates) {
    for (int currentLength : batchSizes) {
      PST result = buildStrategy.build(
        createSegmentListOf(currentLength, vertical, duplicates),
        0,
        currentLength
      );
      assertEquals(
        Math.ceil(Math.log(currentLength) / Math.log(2)),
        result.getHeight()
      );
    }
  }

  private void checkChild(PST parent, PST children, boolean isLeftChild) {
    if (children == null) {
      return;
    }
    CompositeDouble parentMinInterval = children
      .getSegment()
      .getInterval()
      .getIntervalMin();
    double parentMedian = parent.getMedian();

    double childOrigin = children.getSegment().getOrigin().getAsDouble();
    CompositeDouble childMinInterval = children
      .getSegment()
      .getInterval()
      .getIntervalMin();

    if (isLeftChild) {
      assertTrue(
        parentMedian >= childOrigin,
        () ->
          "Erreur de Médiane (gauche) : La médiane du parent " +
          parentMedian +
          " n'est pas plus grande que l'origine de l'enfant " +
          children.getSegment().getOrigin().getAsDouble()
      );
      assertTrue(
        CompositeDouble.equalOrGreaterThan(childMinInterval, parentMinInterval),
        () ->
          "Erreur de MinInterval (gauche) : Le MinInterval du parent " +
          parentMinInterval.getAsDouble() +
          " n'est pas plus grand que celui de l'enfant " +
          childMinInterval.getAsDouble()
      );
    } else {
      assertTrue(
        childOrigin >= parentMedian,
        () ->
          "Erreur de Médiane (droite) : La médiane du parent " +
          parent.getMedian() +
          " n'est pas plus grande que l'origine de l'enfant " +
          children.getSegment().getOrigin().getAsDouble()
      );
      assertTrue(
        CompositeDouble.equalOrGreaterThan(childMinInterval, parentMinInterval),
        () ->
          "Erreur de MinInterval (droite) : Le MinInterval du parent " +
          parentMinInterval.getAsDouble() +
          " n'est pas plus grand que celui de l'enfant " +
          childMinInterval.getAsDouble()
      );
    }

    checkChild(children, children.getLeftChild(), true);
    checkChild(children, children.getRightChild(), false);
  }

  @ParameterizedTest(
    name = "PST Data Location | Vertical : {0}, Probable duplicates : {1}"
  )
  @CsvSource({ "true, true", "true, false", "false, true", "false, false" })
  public void checkLocalInvariant(boolean vertical, boolean duplicates) {
    for (int currentLength : batchSizes) {
      PST result = buildStrategy.build(
        createSegmentListOf(currentLength, vertical, duplicates),
        0,
        currentLength
      );
      checkChild(result, result.getLeftChild(), true);
      checkChild(result, result.getRightChild(), false);
    }
  }

  private int countNodes(PST current) {
    if (current == null) {
      return 0;
    }

    return (
      1 +
      countNodes(current.getLeftChild()) +
      countNodes(current.getRightChild())
    );
  }

  @ParameterizedTest(name = "PST Data Loss | Vertical : {0}, Duplicates : {1}")
  @CsvSource({ "true, true", "true, false", "false, true", "false, false" })
  public void checkDataLoss(boolean vertical, boolean duplicates) {
    for (int currentLength : batchSizes) {
      PST result = buildStrategy.build(
        createSegmentListOf(currentLength, vertical, duplicates),
        0,
        currentLength
      );

      assertEquals(currentLength, countNodes(result));
    }
  }

  @Test
  @DisplayName("Check empty list building")
  public void checkEmptyList() {
    ArrayList<Segment> segments = new ArrayList<>();

    PST result = buildStrategy.build(segments, 0, 0);

    assertNull(result);
    assertEquals(0, countNodes(result));
  }

  @Test
  @DisplayName("Check single element list building")
  public void checkSingleList() {
    ArrayList<Segment> segments = new ArrayList<>();
    segments.add(new Segment(new Vector2D(10, 10), new Vector2D(10, 20)));

    PST result = buildStrategy.build(segments, 0, segments.size());

    assertEquals(1, countNodes(result));
    assertEquals(1, result.getHeight());
  }
}

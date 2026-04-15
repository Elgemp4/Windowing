package be.groupe18.windowing.strategies.minimum;

import static org.junit.jupiter.api.Assertions.*;

import be.groupe18.windowing.application.strategy.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.application.strategy.minimum.MinimumStrategy;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.domain.model.Vector2D;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LinearMinimumTest {

  private MinimumStrategy<Double> doubleMinimumStrategy;
  private MinimumStrategy<Segment> segmentMinimumStrategy;

  @BeforeEach
  public void initStrategies() {
    this.doubleMinimumStrategy = new LinearMinimumStrategy<>();
    this.segmentMinimumStrategy = new LinearMinimumStrategy<>();
  }

  private <T> ArrayList<T> mutableList(T... elements) {
    return new ArrayList<>(Arrays.asList(elements));
  }

  @Test
  @DisplayName("Normal double minimum")
  public void testNormalDouble() {
    ArrayList<Double> testSuit = mutableList(
      10.0,
      20.0,
      45.0,
      200.0,
      -10.0,
      -100.0,
      100.0
    );
    int minIndex = doubleMinimumStrategy.getMinimum(
      testSuit,
      (d1, d2) -> d1 > d2,
      0,
      testSuit.size()
    );
    double min = testSuit.get(minIndex);
    assertEquals(-100, min);
  }

  @Test
  @DisplayName("All duplicates double")
  public void testDuplicateDouble() {
    ArrayList<Double> testSuit = mutableList(
      10.0,
      10.0,
      10.0,
      10.0,
      10.0,
      10.0,
      10.0
    );
    int minIndex = doubleMinimumStrategy.getMinimum(
      testSuit,
      (d1, d2) -> d1 > d2,
      0,
      testSuit.size()
    );
    double min = testSuit.get(minIndex);
    assertEquals(0, minIndex);
    assertEquals(10, min);
  }

  @Test
  @DisplayName("Normal segment minimum")
  public void testNormalSegment() {
    ArrayList<Segment> testSuit = mutableList(
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(10, -10), new Vector2D(10, 50)),
      new Segment(new Vector2D(10, 100), new Vector2D(10, -20)),
      new Segment(new Vector2D(10, -10), new Vector2D(10, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, -50))
    );
    int minIndex = segmentMinimumStrategy.getMinimum(
      testSuit,
      Segment.greaterMinIntervalThan,
      0,
      testSuit.size()
    );
    Segment min = testSuit.get(minIndex);
    assertEquals(-50, min.getInterval().getIntervalMin().getAsDouble());
  }

  @Test
  @DisplayName("Strict duplicate Segment")
  public void testStrictDuplicateSegment() {
    ArrayList<Segment> testSuit = mutableList(
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20))
    );
    int minIndex = segmentMinimumStrategy.getMinimum(
      testSuit,
      Segment.greaterMinIntervalThan,
      0,
      testSuit.size()
    );
    Segment min = testSuit.get(minIndex);
    assertEquals(0, minIndex);
    assertEquals(10, min.getInterval().getIntervalMin().getAsDouble());
  }

  @Test
  @DisplayName("Duplicate Interval but different origin")
  public void testDuplicateSegment() {
    ArrayList<Segment> testSuit = mutableList(
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(0, 10), new Vector2D(0, 20)),
      new Segment(new Vector2D(100, 10), new Vector2D(100, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20)),
      new Segment(new Vector2D(-10, 10), new Vector2D(-10, 20)),
      new Segment(new Vector2D(10, 10), new Vector2D(10, 20))
    );
    int minIndex = segmentMinimumStrategy.getMinimum(
      testSuit,
      Segment.greaterMinIntervalThan,
      0,
      testSuit.size()
    );
    Segment min = testSuit.get(minIndex);
    assertEquals(4, minIndex);
    assertEquals(10, min.getInterval().getIntervalMin().getAsDouble());
    assertEquals(-10, min.getOrigin().getAsDouble());
  }
}

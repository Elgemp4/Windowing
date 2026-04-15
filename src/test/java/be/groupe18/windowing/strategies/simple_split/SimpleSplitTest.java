package be.groupe18.windowing.strategies.simple_split;

import static org.junit.jupiter.api.Assertions.*;

import be.groupe18.windowing.application.strategy.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.application.strategy.pivot_split.PivotSplitStrategy;
import be.groupe18.windowing.application.strategy.simple_split.LinearSimpleSplitStrategy;
import be.groupe18.windowing.application.strategy.simple_split.SimpleSplitStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleSplitTest {

  private SimpleSplitStrategy<Double> simpleSplitStrategy;
  private List<Double> testData;
  private Function<Double, Boolean> belongsToFirst;

  private <T> ArrayList<T> mutableList(T... elements) {
    return new ArrayList<>(Arrays.asList(elements));
  }

  @BeforeEach
  void setupStrategies() {
    this.simpleSplitStrategy = new LinearSimpleSplitStrategy<>();
    this.testData = mutableList(
      15.0,
      10.0,
      9.0,
      25.0,
      32.0,
      45.0,
      0.0,
      -54.0,
      -62.0,
      -5.0,
      122.0
    );
    this.belongsToFirst = (Double d1) -> d1 % 2 == 0;
  }

  @Test
  @DisplayName("Should work with a pivot at the start of the list")
  void test() {
    int splitIndex = simpleSplitStrategy.split(
      testData,
      belongsToFirst,
      0,
      testData.size()
    );
    assertEquals(6, splitIndex);

    for (int i = 0; i < splitIndex; i++) {
      int finalI = i;
      assertTrue(() -> testData.get(finalI) % 2 == 0);
    }

    for (int i = splitIndex; i < testData.size(); i++) {
      int finalI = i;
      assertTrue(() -> testData.get(finalI) % 2 != 0);
    }
  }
}

package be.groupe18.windowing.application.strategy.median;

import java.util.List;
import java.util.function.BiFunction;

/**
 * An interface for a median algorithm using the strategy design pattern
 * @param <T> The abstract type that the median algorithm is runned on
 */
public interface MedianStrategy<T> {
  /**
   * In place median finding on the provided list inside the subset provided by the bounds. For memory efficiency
   * elements are not copied and the input list order is being altered
   * @param elements the list of elements in which to find the median
   * @param greaterThan a function which specifies how to compare two elements
   * @param start the inclusive start index in which to look for the median
   * @param end the exclusive end index in which to look for the median cannot be greater or equal to start
   * @return The resulting index of the median of the subset
   */
  int computeMedian(
    List<T> elements,
    BiFunction<T, T, Boolean> greaterThan,
    int start,
    int end
  );
}

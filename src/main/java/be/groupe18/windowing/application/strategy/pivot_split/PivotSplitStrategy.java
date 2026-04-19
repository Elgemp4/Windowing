package be.groupe18.windowing.application.strategy.pivot_split;

import java.util.List;
import java.util.function.BiFunction;

/**
 * An interface for a pivot split algorithm using the strategy design pattern
 * @param <T> The abstract type that the pivot split algorithm is runned on
 */
public interface PivotSplitStrategy<T> {
  /**
   * Take a list of elements and split them based on a provided pivotIndex, putting the element at pivotIndex in the middle of the split
   * @param elements The list of elements on which to perform the pivotIndex split
   * @param greaterThan A comparator of {@link T}
   * @param start The inclusive start index of the data on which to run the algorithm
   * @param end The exclusive end index of the data on which to run the algorithm
   * @param pivotIndex The index of the pivot on which to use for the split
   * @return The index of the pivot after the split
   */
  int pivotSplit(
    List<T> elements,
    BiFunction<T, T, Boolean> greaterThan,
    int start,
    int end,
    int pivotIndex
  );
}

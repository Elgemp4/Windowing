package be.groupe18.windowing.application.strategy.median;

import static java.util.Collections.swap;

import be.groupe18.windowing.application.strategy.pivot_split.PivotSplitStrategy;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Concrete implementation of the median algorithm with the quick select median with the
 * median of medians method
 * @param <T> The abstract type that the median algorithm is runned out
 */
public class QuickSelectMedianStrategy<T> implements MedianStrategy<T> {

  private final PivotSplitStrategy<T> pivotSplitStrategy;

  public QuickSelectMedianStrategy(PivotSplitStrategy<T> pivotSplitStrategy) {
    this.pivotSplitStrategy = pivotSplitStrategy;
  }

  @Override
  public int computeMedian(
    List<T> elements,
    BiFunction<T, T, Boolean> greaterThan,
    int start,
    int end
  ) {
    int medianIndex = start + (end - start) / 2;
    return quickSelect(elements, greaterThan, start, end, medianIndex);
  }

  /**
   * In place quick select implementation return the index to of the median,
   * with afterwards all elements from start to the median index smaller than the median and all element from
   * the median index to end-1 greater than the median
   * @param elements the list of elements on which to perform the median selection
   * @param greaterThan the funtion to compare two {@link T}
   * @param start The inclusive start index of the data
   * @param end The exclusive end index of the data
   * @param medianIndex The index of where the median should be
   * @return The median index
   */
  private int quickSelect(
    List<T> elements,
    BiFunction<T, T, Boolean> greaterThan,
    int start,
    int end,
    int medianIndex
  ) {
    if (elements == null || start >= end) {
      throw new IllegalArgumentException();
    }

    int elementCount = end - start;

    if (elementCount <= 5) {
      selectionSortMedian(elements, greaterThan, start, end);

      return medianIndex;
    }

    //Compute the medians of cluster of <=5 elements and put them at the start of the list
    //The N/5 first elements are medians of group of <=5 elements
    int nbOfClusters = (int) Math.ceil(elementCount / 5.0);
    for (int clusterIndex = 0; clusterIndex < nbOfClusters; clusterIndex++) {
      int clusterStart = start + clusterIndex * 5;
      int currentCount = Math.min(5, end - clusterStart);
      int clusterEnd = clusterStart + currentCount;
      int currentMedianIndex = clusterStart + currentCount / 2;

      swap(
        elements,
        start + clusterIndex,
        quickSelect(
          elements,
          greaterThan,
          clusterStart,
          clusterEnd,
          currentMedianIndex
        )
      );
    }

    int clusterMedianIndex = start + nbOfClusters / 2;
    //Calculate the medians of median (of the cluster of 5 elements)
    int pivotIndex = quickSelect(
      elements,
      greaterThan,
      start,
      start + nbOfClusters,
      clusterMedianIndex
    );

    //Partition the elements in two group those greater than the pivot before "rank" and those greater than
    //the pivot after "rank". At "rank" the pivot is stored
    int rank = pivotSplitStrategy.pivotSplit(
      elements,
      greaterThan,
      start,
      end,
      pivotIndex
    );

    //If the pivot's rank is less than the median's one search the median in the top group
    if (rank < medianIndex) {
      return quickSelect(elements, greaterThan, rank + 1, end, medianIndex); //Note rank+1 because we know that the pivot isn't the median
    }
    //If the pivot's rank is greater than the median's one search the median in the bottom group
    else if (rank > medianIndex) {
      return quickSelect(elements, greaterThan, start, rank, medianIndex);
    }
    return medianIndex;
  }

  /**
   * Performs a bubble sort and then return the index of the middle element (the median)
   * @param elements the list of {@link T} on which to perform the bubble sort and return the median
   * @param greaterThan a comparator between two {@link T}
   * @param start the inclusive start index of the data in elements
   * @param end the exclusive end index of the data in elements
   * @return the index of the median
   */
  private int selectionSortMedian(
    List<T> elements,
    BiFunction<T, T, Boolean> greaterThan,
    int start,
    int end
  ) {
    int elementCount = end - start;
    int medianIndex = elementCount / 2 + start;

    for (int j = start; j < start + elementCount; j++) {
      T min = null;
      int minIndex = -1;
      for (int k = j; k < start + elementCount; k++) {
        T current = elements.get(k);
        if (min == null || greaterThan.apply(min, current)) {
          min = current;
          minIndex = k;
        }
      }
      swap(elements, j, minIndex);
    }
    return medianIndex;
  }
}

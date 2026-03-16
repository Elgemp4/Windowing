package be.groupe18.windowing.strategies.median;

import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.pivot_split.PivotSplitStrategy;

import java.util.List;
import java.util.function.BiFunction;

import static java.util.Collections.swap;

public class QuickSelectMedianStrategy<T> implements MedianStrategy<T> {


    private final PivotSplitStrategy<T> pivotSplitStrategy;

    public QuickSelectMedianStrategy(PivotSplitStrategy<T> pivotSplitStrategy) {
        this.pivotSplitStrategy = pivotSplitStrategy;
    }

    @Override
    public int computeMedian(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end) {
        return quickSelect(elements, greaterThan, start, end);
    }

    private int quickSelect(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end){
        int elementCount = end-start;
        int medianIndex = start + elementCount/2;

        if(elementCount <= 5){ // Simple linear time bubble sort on <= 5 elements for their median
            return bubbleMedian(elements, greaterThan, start, end);
        }

        //Compute the medians of cluster of <=5 elements and put them at the start of the list
        //The N/5 first elements are medians of group of <=5 elements
        int clusterCount = (int)Math.ceil(elementCount / 5.0);
        for (int clusterIndex = 0; clusterIndex < clusterCount; clusterIndex++) {
            int clusterStart = start + clusterIndex*5;
            int currentCount = Math.min(5, elementCount - clusterStart );

            int clusterEnd = clusterStart + currentCount;
            swap(elements, start + clusterIndex, quickSelect(elements, greaterThan, clusterStart, clusterEnd));
        }

        //Calculate the medians of median (of the cluster of 5 elements)
        int pivotIndex = quickSelect(elements,greaterThan, start, start + clusterCount);

        //Partition the elements in two group those greater than the pivot before "rank" and those greater than
        //the pivot after "rank". At "rank" the pivot is stored
        int rank = pivotSplitStrategy.partition(elements, greaterThan, start, end, pivotIndex);

        if(rank < medianIndex) { //If the pivot's rank is less than the median's one search the median in the top group
            return quickSelect(elements, greaterThan, rank, end);
        }
        else if(rank > medianIndex){ //If the pivot's rank is greater than the median's one search the median in the bottom group
            return quickSelect(elements, greaterThan, start, rank);
        }
        return rank;
    }

    /**
     * Performs a bubble sort and then return the index of the middle element (the median)
     * @param elements
     * @param greaterThan
     * @param start
     * @param end
     * @return the index of the median
     */
    private int bubbleMedian(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end) {
        int elementCount = end-start;
        int medianIndex = elementCount / 2 + start;

        for (int j=start; j < start + elementCount; j++){
            T min = null;
            int minIndex = -1;
            for(int k=j; k < start + elementCount; k++){
                T current = elements.get(k);
                if(min == null || greaterThan.apply(min, current)){
                    min = current;
                    minIndex = k;
                }
            }
            swap(elements, j, minIndex);
        }
        return medianIndex;
    }
}

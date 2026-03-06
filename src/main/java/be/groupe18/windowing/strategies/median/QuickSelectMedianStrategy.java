package be.groupe18.windowing.strategies.median;

import be.groupe18.windowing.strategies.splitting.SplitStrategy;
import be.groupe18.windowing.utils.Pair;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class QuickSelectMedianStrategy<T> implements MedianStrategy<T> {
    private SplitStrategy<T> splitStrategy;

    public QuickSelectMedianStrategy(SplitStrategy<T> splitStrategy) {
        this.splitStrategy = splitStrategy;
    }

    @Override
    public int computeMedian(List<T> elements, BiFunction<T, T, Boolean> greaterThan) {
        return quickSelect(elements, greaterThan, 0, elements.size());
    }

    private int quickSelect(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end){
        int elementCount = end-start;
        int medianIndex = elementCount / 2 + start;

        int finalMedianIndex = elements.size()/2;

        if(elementCount <= 5){ // Simple bubble sort and return index
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

        int clusterCount = (int)Math.ceil(elementCount / 5.0);

        for (int clusterIndex = 0; clusterIndex < clusterCount; clusterIndex++) {
            int clusterStart = start + clusterIndex*5;
            int currentCount = Math.min(5, elementCount - clusterStart );

            int clusterEnd = clusterStart + currentCount;
            swap(elements, start + clusterIndex, quickSelect(elements, greaterThan, clusterStart, clusterEnd));
        }

        int pivotIndex = quickSelect(elements,greaterThan, start, start + clusterCount);

        int rank = split(elements, greaterThan, start, end, pivotIndex);

        if(rank < finalMedianIndex) {
            return quickSelect(elements, greaterThan, rank, end);
        }
        else if(rank > finalMedianIndex){
            return quickSelect(elements, greaterThan, start, rank);
        }
        return rank;
    }

    private void swap(List<T> elements, int i1, int i2) {
        T temp = elements.get(i1);
        elements.set(i1, elements.get(i2));
        elements.set(i2, temp);
    }

    public int split(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end, int pivot) {
        swap(elements, pivot, end-1);

        int j = start;
        for(int i =start; i < end-1; i++){
            if(greaterThan.apply(elements.get(end-1), elements.get(i))){
                swap(elements,i,j);
                j+=1;
            }
        }

        swap(elements, j, end-1);
        return j;
    }
}

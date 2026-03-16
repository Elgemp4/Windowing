package be.groupe18.windowing.strategies.pivot_split;

import java.util.List;
import java.util.function.BiFunction;

import static java.util.Collections.swap;

public class LinearPivotSplitStrategy<T> implements PivotSplitStrategy<T> {
    @Override
    public int partition(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end, int pivot) {
        swap(elements, pivot, end-1);
        T pivotEL = elements.get(end-1);
        int j = start;
        for(int i =start; i < end-1; i++){
            T current = elements.get(i);
            if(greaterThan.apply(pivotEL, current)){
                swap(elements,i,j);
                j+=1;
            }
        }

        swap(elements, j, end-1);
        return j;
    }
}

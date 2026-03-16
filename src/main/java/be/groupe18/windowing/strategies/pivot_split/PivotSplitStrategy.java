package be.groupe18.windowing.strategies.pivot_split;


import java.util.List;
import java.util.function.BiFunction;


public interface PivotSplitStrategy<T> {

    int partition(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end, int pivot);
}

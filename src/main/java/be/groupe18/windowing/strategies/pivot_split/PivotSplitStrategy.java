package be.groupe18.windowing.strategies.pivot_split;

import be.groupe18.windowing.models.Segment;

import java.util.List;
import java.util.function.BiFunction;

import static java.util.Collections.swap;

public interface PivotSplitStrategy<T> {
    int partition(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end, int pivot);
}

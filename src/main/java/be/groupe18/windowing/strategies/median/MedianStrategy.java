package be.groupe18.windowing.strategies.median;


import java.util.List;
import java.util.function.BiFunction;

public interface MedianStrategy<T> {
    int computeMedian(List<T> elements, BiFunction<T,T,Boolean> greaterThan, int start, int end);
}

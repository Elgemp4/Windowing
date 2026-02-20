package be.groupe18.windowing.strategies.median;


import java.util.List;
import java.util.function.BiFunction;

public interface MedianStrategy<T> {
    T computeMedian(List<T> elements, BiFunction<T,T,Boolean> greaterThan);
}

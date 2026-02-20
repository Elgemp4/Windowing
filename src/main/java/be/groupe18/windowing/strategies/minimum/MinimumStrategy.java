package be.groupe18.windowing.strategies.minimum;

import java.util.List;
import java.util.function.BiFunction;

public interface MinimumStrategy<T> {
    T getMinimum(List<T> segments, BiFunction<T, T, Boolean> greaterThan);
}

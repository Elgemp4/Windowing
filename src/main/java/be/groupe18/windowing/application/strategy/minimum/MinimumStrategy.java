package be.groupe18.windowing.application.strategy.minimum;

import java.util.List;
import java.util.function.BiFunction;

/**
 * An interface for a minimum finding algorithm using the strategy design pattern
 * @param <T> The abstract type that the minimum finding algorithm is runned on
 */
public interface MinimumStrategy<T> {
    /**
     * Takes in a list of elements and returns the smallest one withing the subset provided by the bounds
     * @param elements The elements on which to search
     * @param greaterThan The function to compare two elements
     * @param start The inclusive start index of the subset of elements
     * @param end The exclusive end index of the subset of elements
     * @return The index of the smallest element withing the subset
     */
    int getMinimum(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end);
}

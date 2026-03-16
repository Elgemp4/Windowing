package be.groupe18.windowing.strategies.median;


import java.util.List;
import java.util.function.BiFunction;

public interface MedianStrategy<T> {
    /**
     * Find the median on the provided list inside the subset provided by the bounds
     * @param elements the list of elements in which to find the median
     * @param greaterThan a function which specifie how to compare two elements
     * @param start the inclusive start index in which to look for the median
     * @param end the exclusive end index in which to look for the median
     * @return The resulting index of the median of the subset
     */
    int computeMedian(List<T> elements, BiFunction<T,T,Boolean> greaterThan, int start, int end);
}

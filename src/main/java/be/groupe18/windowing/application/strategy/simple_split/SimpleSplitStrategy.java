package be.groupe18.windowing.application.strategy.simple_split;

import java.util.List;
import java.util.function.Function;

/**
 * An interface for the simple split algorithm which simply split the provided list in place
 * @param <T> The abstract type that the algorithm is runned on
 */
public interface SimpleSplitStrategy<T> {

    /**
     * Split (in place) the provided list based on the provided comparator
     * @param elements the list of elements to split
     * @param belongsToFirst the function which determine in which two groups the element should be
     * @param start The inclusive start index on which to run the algorithm
     * @param end The exclusive end index on which to run the algorithm
     * @return The index of the start of the second group
     */
    int split(List<T> elements, Function<T, Boolean> belongsToFirst, int start, int end);

    /**
     * Split (in place) the provided list based on the provided comparator
     * @param elements the list of elements to split
     * @param belongsToFirst the function which determine in which two groups the element should be
     * @return The index of the start of the second group
     */
    default int split(List<T> elements, Function<T, Boolean> belongsToFirst){
        return split(elements,belongsToFirst,0,elements.size());
    }
}

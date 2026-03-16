package be.groupe18.windowing.strategies.simple_split;

import java.util.List;
import java.util.function.Function;

public interface SimpleSplitStrategy<T> {

    int split(List<T> elements, Function<T, Boolean> belongsToFirst, int start, int end);
    default int split(List<T> elements, Function<T, Boolean> belongsToFirst){
        return split(elements,belongsToFirst,0,elements.size());
    }
}

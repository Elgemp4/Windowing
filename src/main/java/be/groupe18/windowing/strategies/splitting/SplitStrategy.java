package be.groupe18.windowing.strategies.splitting;

import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.utils.Pair;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public interface SplitStrategy<T> {

    int split(List<T> elements, Function<T, Boolean> belongsToFirst, int start, int end);
    default int split(List<T> elements, Function<T, Boolean> belongsToFirst){
        return split(elements,belongsToFirst,0,elements.size());
    }
}

package be.groupe18.windowing.strategies.splitting;

import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.utils.Pair;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public interface SplitStrategy<T> {

    Pair<List<T>, List<T>> split(List<T> elements, Function<T, Boolean> belongsToFirst);
}

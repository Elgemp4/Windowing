package be.groupe18.windowing.strategies.splitting;

import be.groupe18.windowing.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LinearSplitStrategy<T> implements SplitStrategy<T> {
    @Override
    public Pair<List<T>, List<T>> split(List<T> elements, Function<T,Boolean> belongsToFirst) {
        List<T> first = new ArrayList<>();
        List<T> second = new ArrayList<>();

        for (T e : elements) {
            if (belongsToFirst.apply(e)) {
                second.add(e);
            }
            else {
                first.add(e);
            }
        }

        return new Pair<>(first, second);
    }
}

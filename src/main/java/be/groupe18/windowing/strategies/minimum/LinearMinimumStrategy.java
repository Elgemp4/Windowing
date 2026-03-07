package be.groupe18.windowing.strategies.minimum;

import java.util.List;
import java.util.function.BiFunction;

public class LinearMinimumStrategy<T> implements MinimumStrategy<T> {
    @Override
    public int getMinimum(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end) {
        T minElement = elements.getFirst();
        int minIndex = 0;

        for (int i = start; i < end; i++) {
            T e = elements.get(i);
            if(greaterThan.apply(minElement, e)){
                minElement = e;
                minIndex = i;
            }
        }

        return minIndex;
    }
}

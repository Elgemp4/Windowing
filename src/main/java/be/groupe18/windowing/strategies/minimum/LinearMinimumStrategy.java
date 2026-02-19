package be.groupe18.windowing.strategies.minimum;

import java.util.List;
import java.util.function.BiFunction;

public class LinearMinimumStrategy<T> implements MinimumStrategy<T> {
    @Override
    public T getMinimum(List<T> elements, BiFunction<T, T, Boolean> greaterThan) {
        T minElement = elements.getFirst();

        for(T e : elements){
            if(greaterThan.apply(minElement, e)){
                minElement = e;
            }
        }

        return minElement;
    }
}

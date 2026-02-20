package be.groupe18.windowing.strategies.sort;

import java.util.function.BiFunction;

// Peut-être plus tard, si on veut améliorer la médiane ou la recherche du minimum
public interface SortStrategy<T> {
    T sort(T data, BiFunction<T,T, Integer> compare);
}

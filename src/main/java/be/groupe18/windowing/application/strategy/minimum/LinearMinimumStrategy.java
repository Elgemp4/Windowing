package be.groupe18.windowing.application.strategy.minimum;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A concrete linear implementation of the minimum strategy, which find the minimum by doing a linear scan
 * @param <T> The type of data on which to search the minimum
 */
public class LinearMinimumStrategy<T> implements MinimumStrategy<T> {

  @Override
  public int getMinimum(
    List<T> elements,
    BiFunction<T, T, Boolean> greaterThan,
    int start,
    int end
  ) {
    T minElement = elements.get(start);
    int minIndex = start;

    for (int i = start; i < end; i++) {
      T e = elements.get(i);
      if (greaterThan.apply(minElement, e)) {
        minElement = e;
        minIndex = i;
      }
    }

    return minIndex;
  }
}

package be.groupe18.windowing.strategies.median;

import be.groupe18.windowing.strategies.splitting.SplitStrategy;

import java.util.List;
import java.util.function.BiFunction;

public class QuickSelectMedianStrategy<T> implements MedianStrategy<T> {
    private SplitStrategy<T> splitStrategy;

    public QuickSelectMedianStrategy(SplitStrategy<T> splitStrategy) {
        this.splitStrategy = splitStrategy;
    }

    @Override
    public T computeMedian(List<T> elements, BiFunction<T, T, Boolean> greaterThan) {
        return quickSelect(elements, greaterThan, elements.size()/2);
    }

    private T quickSelect(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int searched){
        for (int i = 0; i < elements.size() / 5; i++) {
            int elementCount = Math.min(5, elements.size() - 5*i);
            for (int j=i*5; j <= elementCount; j++){
                T min = null;
                int minIndex = -1;
                for(int k=j; k <= elementCount; k++){
                    T current = elements.get(k);
                    if(min == null || greaterThan.apply(min, current)){
                        min = current;
                        minIndex = k;
                    }
                }
                swap(elements, j, minIndex);
            }
            swap(elements, i, elementCount/2);
        }

        T pivot = quickSelect(elements,greaterThan, elements.size()/10);
        int rank = splitStrategy.split(elements, (T el) -> greaterThan.apply(pivot, el));


        if(elements.indexOf(pivot))
    }

    private void swap(List<T> elements, int i1, int i2) {
        T temp = elements.get(i1);
        elements.set(i1, elements.get(i2));
        elements.set(i2, temp);
    }
}

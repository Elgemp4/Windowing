package be.groupe18.windowing.strategies.splitting;

import be.groupe18.windowing.utils.Pair;

import java.util.List;
import java.util.function.Function;

public class LinearSplitStrategy<T> implements SplitStrategy<T> {
    @Override
    public int split(List<T> elements, Function<T,Boolean> belongsToFirst, int start, int end) {
        int j = start;
        for(int i =start; i < end; i++){
            if(belongsToFirst.apply(elements.get(i))){
                swap(elements,i,j);
                j+=1;
            }
        }
        return j;
    }

    private void swap(List<T> elements, int i1, int i2) {
        T temp = elements.get(i1);
        elements.set(i1, elements.get(i2));
        elements.set(i2, temp);
    }
}

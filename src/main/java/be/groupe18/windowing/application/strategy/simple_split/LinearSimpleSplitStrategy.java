package be.groupe18.windowing.application.strategy.simple_split;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.swap;

public class LinearSimpleSplitStrategy<T> implements SimpleSplitStrategy<T> {
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
}

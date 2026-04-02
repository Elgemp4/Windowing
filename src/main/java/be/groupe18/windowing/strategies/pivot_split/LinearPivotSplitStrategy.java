package be.groupe18.windowing.strategies.pivot_split;

import java.util.List;
import java.util.function.BiFunction;

import static java.util.Collections.swap;

public class LinearPivotSplitStrategy<T> implements PivotSplitStrategy<T> {
    @Override
    public int partition(List<T> elements, BiFunction<T, T, Boolean> greaterThan, int start, int end, int pivot) {
        swap(elements, pivot, end-1);
        T pivotEL = elements.get(end-1);
        int j = start;

        boolean toggle = false;

        for(int i =start; i < end-1; i++){
            T current = elements.get(i);

            boolean isStrictlyLess = greaterThan.apply(current, pivotEL);

            boolean isStrictlyGreater = greaterThan.apply(pivotEL, current);
            boolean isEqual = !isStrictlyGreater && !isStrictlyLess;

            //If it's stricly greater we swap, however to avoid the worst case partition
            //where if all the data are the same, the pivot even if it's the median of the
            //the datagroup will be place at end-1, so to avoid this we accept half of the time
            //to swap a duplicate data. Bringin our pivot in the middle of the array
            //For normal data it changes nothing, because if the data is the same as our pivot
            //it doesn't matter if it's swapped or not.
            if (isStrictlyGreater || (isEqual && toggle)) {
                swap(elements, i, j);
                j += 1;
            }

            if (isEqual) {
                toggle = !toggle;
            }
        }

        swap(elements, j, end-1);
        return j;
    }
}

package be.groupe18.windowing;

import be.groupe18.windowing.strategies.median.MedianStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.pivot_split.LinearPivotSplitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSelectMedianTest {

    private MedianStrategy<Double> quickSelectMedianStrategy;

    private final BiFunction<Double, Double, Boolean> greaterThan = (a, b) -> a > b;

    @BeforeEach
    public void initStrategies() {
        quickSelectMedianStrategy = new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>());
    }

    private <T> ArrayList<T> mutableList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }


    private void assertCorrectlyOrdered(List<Double> list, int medianIndex){
        assertCorrectlyOrdered(list, medianIndex, 0, list.size());
    }

    private void assertCorrectlyOrdered(List<Double> list, int medianIndex, int start, int end) {
        for(int i = start; i < medianIndex; i++){
            assertTrue(list.get(i) <= list.get(medianIndex));
        }

        for(int i = medianIndex+1; i < end; i++){
            assertTrue(list.get(i) >= list.get(medianIndex));
        }
    }


    @Test
    @DisplayName("Find the median of an odd count of element (<= 5 elements)")
    public void testOddSizedSmallList() {
        ArrayList<Double> list = mutableList(5.0, 1.0, 3.0);

        int medianIndex = quickSelectMedianStrategy.computeMedian(list, greaterThan, 0, list.size());

        assertEquals(3.0, list.get(medianIndex));
    }

    @Test
    @DisplayName("Find the median of an even count of element (<= 5 elements)")
    public void testEvenSizedSmallList() {
        ArrayList<Double> list = mutableList(5.0, 1.0, 3.0, 2.0);

        int medianIndex = quickSelectMedianStrategy.computeMedian(list, greaterThan, 0, list.size());

        assertEquals(3.0, list.get(medianIndex));

        assertCorrectlyOrdered(list, medianIndex);
    }

    @Test
    @DisplayName("Correctly use medians of medians on list with > 5 elements")
    public void testLargeListWithClusters() {
        ArrayList<Double> list = mutableList(10.0, 2.0, 8.0, 4.0, 6.0, 1.0, 9.0, 3.0, 7.0, 5.0); // 1 2 3 4 5 6 7 8 9 10
        int medianIndex = quickSelectMedianStrategy.computeMedian(list, greaterThan, 0, list.size());
        assertEquals(6.0, list.get(medianIndex));
        assertCorrectlyOrdered(list, medianIndex);

    }

    @Test
    @DisplayName("Works correctly on a subset of nodes")
    public void testSubListPartitioning() {
        ArrayList<Double> list = mutableList(99.0, 99.0, 99.0, 99.0, 99.0, 1.0, 3.0, 2.0, 99.0);

        int medianIndex = quickSelectMedianStrategy.computeMedian(list, greaterThan, 4, list.size());

        //Check correctness of median
        assertEquals(3.0, list.get(medianIndex));
        //Check if elemens outside the subset has not been altered
        assertEquals(99.0, list.get(0));
        assertEquals(99.0, list.get(1));
        assertEquals(99.0, list.get(2));
        assertEquals(99.0, list.get(3));
        //Check if the subset is correctly orderded relative to the median
        assertCorrectlyOrdered(list, medianIndex, 5, list.size());
    }

    @Test
    @DisplayName("Should run quickly and not cause any stack overflow with duplicates data")
    public void testMassiveDuplicates() {
        int size = 100000;
        ArrayList<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(42.0);
        }

        assertDoesNotThrow(() -> {
            int medianIndex = quickSelectMedianStrategy.computeMedian(list, greaterThan, 0, size);
            assertEquals(42.0, list.get(medianIndex));
            assertCorrectlyOrdered(list, medianIndex);
        });
    }

    @Test
    @DisplayName("Should reject illegal arguments")
    public void testIllegalArgumentExceptions() {
        ArrayList<Double> validList = mutableList(1.0, 2.0, 3.0);

        assertThrows(IllegalArgumentException.class, () ->
                quickSelectMedianStrategy.computeMedian(null, greaterThan, 0, 3)
        );

        assertThrows(IllegalArgumentException.class, () ->
                quickSelectMedianStrategy.computeMedian(validList, greaterThan, 2, 2)
        );

        assertThrows(IllegalArgumentException.class, () ->
                quickSelectMedianStrategy.computeMedian(validList, greaterThan, 3, 1)
        );
    }
}

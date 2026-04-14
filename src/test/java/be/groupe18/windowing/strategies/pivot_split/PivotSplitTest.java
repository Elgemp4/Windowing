package be.groupe18.windowing.strategies.pivot_split;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import be.groupe18.windowing.application.strategy.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.application.strategy.pivot_split.PivotSplitStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public class PivotSplitTest {
    private PivotSplitStrategy<Double> pivotSplitStrategy;
    private List<Double> testData;
    private BiFunction<Double, Double, Boolean> greaterThan;


    private <T> ArrayList<T> mutableList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    @BeforeEach
    void setupStrategies() {
        this.pivotSplitStrategy = new LinearPivotSplitStrategy<>();
        this.testData = mutableList(15.0,10.0,9.0,25.0,32.0,45.0,0.0,-54.0,-62.0,-5.0,122.0);
        this.greaterThan = (Double d1, Double d2) -> d1 > d2;
    }

    @Test
    @DisplayName("Should return an exception when the pivot is out of bounds")
    void testOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> pivotSplitStrategy.pivotSplit(testData, greaterThan, 0, testData.size(), -1));
        assertThrows(IndexOutOfBoundsException.class, () -> pivotSplitStrategy.pivotSplit(testData, greaterThan, 0, testData.size(), testData.size()));
    }

    @Test
    @DisplayName("Should work with a pivot at the start of the list")
    void testAtStart() {
        int pivotIndex = pivotSplitStrategy.pivotSplit(testData, greaterThan, 0, testData.size(), 0);
        assertEquals(6, pivotIndex);
    }

    @Test
    @DisplayName("Should work with a pivot at the end of the list")
    void testAtEnd() {
        int pivotIndex = pivotSplitStrategy.pivotSplit(testData, greaterThan, 0, testData.size(), testData.size() - 1);
        assertEquals(10, pivotIndex);
    }
}

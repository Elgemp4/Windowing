package be.groupe18.windowing;
import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.models.Vector2D;
import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.pivot_split.LinearPivotSplitStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PRTRecursiveBuildTest {

    private BuildStrategy buildStrategy;

    @BeforeEach
    public void configureStrategies() {
        this.buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()),
                new LinearPivotSplitStrategy<>());
    }

    private List<Segment> createVerticalListOf(int n){
        ArrayList<Segment> segments = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int x = (int) Math.floor(Math.random() * 1000);
            segments.add(new Segment(new Vector2D(x,(int) Math.floor(Math.random() * 1000)), new Vector2D(x,(int) Math.floor(Math.random() * 1000))));
        }

        return segments;
    }

    @Test
    public void checkLogarithmicHeight(){

        int[] lengths = {100,200,400,1000,10000,100000,1000000};

        for(int currentLength : lengths){
            System.out.println(currentLength);
            PRT result = buildStrategy.build(createVerticalListOf(currentLength), 0, currentLength);

            assertEquals(Math.ceil(Math.log(currentLength) / Math.log(2)), result.getHeight());
        }

    }

}

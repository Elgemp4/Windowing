package be.groupe18.windowing;
import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PRT;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.models.Vector2D;
import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.pivot_split.LinearPivotSplitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PRTRecursiveBuildTest {

    private BuildStrategy buildStrategy;
    private int[] batchSizes = {10, 100,200,400,1000,10000,100000};

    @BeforeEach
    public void configureStrategies() {
        this.buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()),
                new LinearPivotSplitStrategy<>());
    }

    private List<Segment> createSegmentlListOf(int n, boolean isVertical, int mult){
        ArrayList<Segment> segments = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int origin = (int) Math.floor(Math.random() * mult);
            int intStart = (int) Math.floor(Math.random() * mult);
            int intEnd = (int) Math.floor(Math.random() * mult);
            if(isVertical){
                segments.add(new Segment(new Vector2D(origin,intStart), new Vector2D(origin,intEnd)));
            }
            else{
                segments.add(new Segment(new Vector2D(intStart,origin), new Vector2D(intEnd,origin)));
            }
        }

        return segments;
    }

    @ParameterizedTest(name = "PRT Heigh check | Vertical : {0}, Probable duplicates : {1}")
    @CsvSource({
            "true, true",
            "true, false",
            "false, true",
            "false, false"
    })
    @DisplayName("Check for the height of a vertical tree, with improbable duplicated")
    public void checkTreeHeight(boolean vertical, boolean duplicates){
        for(int currentLength : batchSizes){
            PRT result = buildStrategy.build(createSegmentlListOf(currentLength, vertical, duplicates ? currentLength*100 : currentLength/2), 0, currentLength);
            assertEquals(Math.ceil(Math.log(currentLength) / Math.log(2)), result.getHeight());
        }
    }

    private void checkChild(PRT parent, PRT children, boolean isLeftChild) {
        if(children == null){
            return;
        }

        if(isLeftChild) {
            assertTrue(CompositeDouble.equalOrGreaterThan(parent.getMedian(), children.getSegment().getOrigin()),
                    () -> "Erreur de Médiane (gauche) : La médiane du parent " + parent.getMedian().getAsDouble() + " n'est pas plus grande que l'origine de l'enfant " + children.getSegment().getOrigin().getAsDouble());
            assertTrue(CompositeDouble.equalOrGreaterThan(children.getSegment().getMinInterval(), parent.getSegment().getMinInterval()),
                    () -> "Erreur de MinInterval (gauche) : Le MinInterval du parent " + parent.getSegment().getMinInterval().getAsDouble() + " n'est pas plus grand que celui de l'enfant " + children.getSegment().getMinInterval().getAsDouble());
        }
        else{
            assertTrue(CompositeDouble.equalOrGreaterThan(children.getSegment().getOrigin(), parent.getMedian()),
                    () -> "Erreur de Médiane (droite) : La médiane du parent " + parent.getMedian().getAsDouble() + " n'est pas plus grande que l'origine de l'enfant " + children.getSegment().getOrigin().getAsDouble());
            assertTrue(CompositeDouble.equalOrGreaterThan(children.getSegment().getMinInterval(), parent.getSegment().getMinInterval()),
                    () -> "Erreur de MinInterval (droite) : Le MinInterval du parent " + parent.getSegment().getMinInterval().getAsDouble() + " n'est pas plus grand que celui de l'enfant " + children.getSegment().getMinInterval().getAsDouble());
        }


        checkChild(children, children.getLeftChild(), true);
        checkChild(children, children.getRightChild(), false);
    }

    @ParameterizedTest(name = "PRT Data Location | Vertical : {0}, Probable duplicates : {1}")
    @CsvSource({
            "true, true",
            "true, false",
            "false, true",
            "false, false"
    })
    public void checkDataLocation(boolean vertical, boolean duplicates){
        for(int currentLength : batchSizes){
            PRT result = buildStrategy.build(createSegmentlListOf(currentLength, vertical, duplicates ? currentLength*100 : currentLength/10), 0, currentLength);
            checkChild(result, result.getLeftChild(), true);
            checkChild(result, result.getRightChild(), false);
        }
    }
}

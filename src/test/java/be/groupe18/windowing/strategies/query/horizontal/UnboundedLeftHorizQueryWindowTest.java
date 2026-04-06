package be.groupe18.windowing.strategies.query.horizontal;

import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.models.Vector2D;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.strategies.query.AbstractWindowQueryTest;
import be.groupe18.windowing.strategies.query.SimpleQueryStrategy;

import java.util.Arrays;
import java.util.List;

public class UnboundedLeftHorizQueryWindowTest extends AbstractWindowQueryTest {

    private static final List<Segment> INSIDE = Arrays.asList(
            new Segment(new Vector2D(10, 20), new Vector2D(40, 20)),
            new Segment(new Vector2D(-150, 35), new Vector2D(-50, 35))
    );

    private static final List<Segment> THROUGH = Arrays.asList(
            new Segment(new Vector2D(-100, 25), new Vector2D(100, 25)),
            new Segment(new Vector2D(-200, 10), new Vector2D(80, 10))
    );

    private static final List<Segment> PARTIALLY_INSIDE = Arrays.asList(
            new Segment(new Vector2D(40, 40), new Vector2D(70, 40))
    );

    private static final List<Segment> OUTSIDE_GOOD_INT = Arrays.asList(
            new Segment(new Vector2D(60, 10), new Vector2D(90, 10)),
            new Segment(new Vector2D(80, 40), new Vector2D(120, 40))
    );

    private static final List<Segment> OUTSIDE_GOOD_ORIGIN = Arrays.asList(
            new Segment(new Vector2D(-50, 80), new Vector2D(-10, 80)),
            new Segment(new Vector2D(-50, -20), new Vector2D(-10, -20))
    );

    private static final List<Segment> OUTSIDE = Arrays.asList(
            new Segment(new Vector2D(60, 80), new Vector2D(90, 80)),
            new Segment(new Vector2D(60, -20), new Vector2D(90, -20))
    );

    private static final List<Segment> POINT = Arrays.asList(
            new Segment(new Vector2D(20, 20), new Vector2D(20, 20))
    );

    public UnboundedLeftHorizQueryWindowTest() {
        super(
                new RecursiveBuildStrategy(
                        new LinearMinimumStrategy<>(),
                        new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()),
                        new LinearPivotSplitStrategy<>()
                ),
                QueryWindow.buildQueryWindows(Double.NEGATIVE_INFINITY, 50, 0, 50).getV2(),
                new SimpleQueryStrategy()
        );
    }

    @Override protected List<Segment> getInsideSegments() { return INSIDE; }
    @Override protected List<Segment> getThroughSegments() { return THROUGH; }
    @Override protected List<Segment> getPartiallyInsideSegments() { return PARTIALLY_INSIDE; }
    @Override protected List<Segment> getOutsideGoodIntSegments() { return OUTSIDE_GOOD_INT; }
    @Override protected List<Segment> getOutsideGoodOriginSegments() { return OUTSIDE_GOOD_ORIGIN; }
    @Override protected List<Segment> getOutsideSegments() { return OUTSIDE; }
    @Override protected List<Segment> getInsidePoint() { return POINT; }
}
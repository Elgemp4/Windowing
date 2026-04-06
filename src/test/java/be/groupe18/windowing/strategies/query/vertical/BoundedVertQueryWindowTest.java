package be.groupe18.windowing.strategies.query.vertical;

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

public class BoundedVertQueryWindowTest extends AbstractWindowQueryTest {

    private static final List<Segment> INSIDE = Arrays.asList(
            new Segment(new Vector2D(20, 10), new Vector2D(20, 40)),
            new Segment(new Vector2D(35, 5), new Vector2D(35, 45))
    );

    private static final List<Segment> THROUGH = Arrays.asList(
            new Segment(new Vector2D(25, -20), new Vector2D(25, 80)),
            new Segment(new Vector2D(10, -100), new Vector2D(10, 100))
    );

    private static final List<Segment> PARTIALLY_INSIDE = Arrays.asList(
            new Segment(new Vector2D(15, -10), new Vector2D(15, 20)),
            new Segment(new Vector2D(40, 40), new Vector2D(40, 70))
    );

    private static final List<Segment> OUTSIDE_GOOD_INT = Arrays.asList(
            new Segment(new Vector2D(-10, 10), new Vector2D(-10, 40)),
            new Segment(new Vector2D(80, 10), new Vector2D(80, 40))
    );

    private static final List<Segment> OUTSIDE_GOOD_ORIGIN = Arrays.asList(
            new Segment(new Vector2D(30, 60), new Vector2D(30, 90)),
            new Segment(new Vector2D(30, -50), new Vector2D(30, -10))
    );

    private static final List<Segment> OUTSIDE = Arrays.asList(
            new Segment(new Vector2D(-20, 60), new Vector2D(-20, 90)),
            new Segment(new Vector2D(80, -50), new Vector2D(80, -10))
    );

    private static final List<Segment> POINT = Arrays.asList(
            new Segment(new Vector2D(10, 10), new Vector2D(10, 10))
    );

    public BoundedVertQueryWindowTest() {
        super(
                new RecursiveBuildStrategy(
                        new LinearMinimumStrategy<>(),
                        new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()),
                        new LinearPivotSplitStrategy<>()
                ),
                QueryWindow.buildQueryWindows(0, 50, 0, 50).getV1(),
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
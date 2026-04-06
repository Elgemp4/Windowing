package be.groupe18.windowing.models;

import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;
import be.groupe18.windowing.utils.Tuple;

import java.util.List;

/**
 * A wrapper class for the interaction with the UI abstract the underlying data structures to provide a high level API
 * for the GUI.
 */
public class Scene {
    private PST horizontalTree;
    private PST verticalTree;

    private final BuildStrategy buildStrategy;
    private final QueryStrategy queryStrategy;

    public Scene(BuildStrategy buildStrategy, QueryStrategy queryStrategy) {
        this.buildStrategy = buildStrategy;
        this.queryStrategy = queryStrategy;
    }

    /**
     * Query the data structures and return the result
     * @param minX The minimal x coordinates of the query
     * @param maxX The maximal x coordinates of the query
     * @param minY The minimal y coordinates of the query
     * @param maxY The maximal y coordinates of the query
     * @return
     */
    public List<Segment> querySegmentsInQueryWindow(double minX, double maxX, double minY, double maxY) {
        Tuple<QueryWindow,QueryWindow> queryWindows = QueryWindow.buildQueryWindows(minX, maxX, minY, maxY);

        List<Segment> segments = queryStrategy.query(verticalTree, queryWindows.getV1());
        segments.addAll(queryStrategy.query(horizontalTree, queryWindows.getV2()));
        return segments;
    }

    public void buildHorizontalTree(List<Segment> segments, int start, int end) {
        this.horizontalTree = buildPST(segments, start, end);
    }

    public void buildVerticalTree(List<Segment> segments, int start, int end) {
        this.verticalTree = buildPST(segments, start, end);
    }

    private PST buildPST(List<Segment> segments, int start, int end) {
        return buildStrategy.build(segments, start, end);
    }
}

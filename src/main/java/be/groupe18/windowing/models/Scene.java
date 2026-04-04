package be.groupe18.windowing.models;

import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;

import java.util.List;

public class Scene {
    private PST horizontalTree;
    private PST verticalTree;

    private final BuildStrategy buildStrategy;
    private final QueryStrategy queryStrategy;

    public Scene(BuildStrategy buildStrategy, QueryStrategy queryStrategy) {
        this.buildStrategy = buildStrategy;
        this.queryStrategy = queryStrategy;
    }

    public List<Segment> querySegmentsInQueryWindow(QueryWindow queryWindow) {
        List<Segment> segments =  queryStrategy.query(horizontalTree, queryWindow);
        segments.addAll(queryStrategy.query(verticalTree, queryWindow));
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

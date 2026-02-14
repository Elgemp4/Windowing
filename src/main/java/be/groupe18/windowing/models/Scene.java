package be.groupe18.windowing.models;

import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;

import java.util.List;

public class Scene {
    private PRT horizontalTree;
    private PRT verticalTree;

    private BuildStrategy buildStrategy;
    private QueryStrategy queryStrategy;

    public Scene(BuildStrategy buildStrategy, QueryStrategy queryStrategy) {
        this.buildStrategy = buildStrategy;
        this.queryStrategy = queryStrategy;
    }

    public List<Segment> querySegmentsInQueryWindow(QueryWindow queryWindow) {
        List<Segment> segments =  queryStrategy.query(horizontalTree, queryWindow);
        segments.addAll(queryStrategy.query(verticalTree, queryWindow));
        return segments;
    }

    public void buildHorizontalTree(List<Segment> segments) {
        this.horizontalTree = buildPRT(segments);
    }

    public void buildVerticalTree(List<Segment> segments) {
        this.verticalTree = buildPRT(segments);
    }

    private PRT buildPRT(List<Segment> segments) {
        return buildStrategy.build(segments);
    }
}

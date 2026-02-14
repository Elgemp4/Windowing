package be.groupe18.windowing.models;

import java.util.List;

public class Scene {
    private PRT horizontalTree;
    private PRT verticalTree;

    public void querySegmentsInQueryWindow(QueryWindow queryWindow) {

    }

    public void buildHorizontalTree(List<Segment> segments) {
        this.horizontalTree = buildPRT(segments);
    }

    public void buildVerticalTree(List<Segment> segments) {
        this.verticalTree = buildPRT(segments);
    }

    private PRT buildPRT(List<Segment> segments) {

    }
}

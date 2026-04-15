package be.groupe18.windowing.domain.model;

import be.groupe18.windowing.application.strategy.build.BuildStrategy;
import java.util.List;

/**
 * A wrapper class for the interaction with the UI abstract the underlying data structures to provide a high level API
 * for the GUI.
 */
public class Scene {

  private PST horizontalTree;
  private PST verticalTree;

  private final BuildStrategy buildStrategy;

  public Scene(BuildStrategy buildStrategy) {
    this.buildStrategy = buildStrategy;
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

  public PST getHorizontalPst() {
    return horizontalTree;
  }

  public PST getVerticalPst() {
    return verticalTree;
  }
}

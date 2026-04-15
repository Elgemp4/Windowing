package be.groupe18.windowing.application.service.sceneBuilding;

import be.groupe18.windowing.application.strategy.build.BuildStrategy;
import be.groupe18.windowing.application.strategy.query.QueryStrategy;
import be.groupe18.windowing.application.strategy.simple_split.SimpleSplitStrategy;
import be.groupe18.windowing.domain.model.Scene;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.infrastructure.repository.pst.IPSTRepository;
import java.util.List;

public class BuildSceneService implements ISceneBuilderService {

  private final BuildStrategy buildStrategy;
  private final SimpleSplitStrategy<Segment> splitStrategy;

  private final IPSTRepository PSTRepository;

  public BuildSceneService(
    BuildStrategy buildStrategy,
    QueryStrategy queryStrategy,
    SimpleSplitStrategy<Segment> splitStrategy,
    IPSTRepository PSTRepository
  ) {
    this.buildStrategy = buildStrategy;
    this.splitStrategy = splitStrategy;
    this.PSTRepository = PSTRepository;
  }

  public Void execute(List<Segment> segments) {
    Scene scene = new Scene(buildStrategy);
    int splitIndex = splitStrategy.split(segments, Segment::isVertical);
    scene.buildVerticalTree(segments, 0, splitIndex);
    scene.buildHorizontalTree(segments, splitIndex, segments.size());
    PSTRepository.saveScene(scene);
    return null;
  }
}

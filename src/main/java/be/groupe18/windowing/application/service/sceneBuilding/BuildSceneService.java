package be.groupe18.windowing.application.service.sceneBuilding;

import be.groupe18.windowing.application.strategy.build.BuildStrategy;
import be.groupe18.windowing.application.strategy.query.QueryStrategy;
import be.groupe18.windowing.application.strategy.simple_split.SimpleSplitStrategy;
import be.groupe18.windowing.domain.model.Scene;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.infrastructure.repository.pst.IPSTRepository;
import java.util.List;

/**
 * Application service (Use Case) responsible for initializing and building a new {@link Scene}.
 * <p>
 * This service orchestrates the preparation of spatial data. It categorizes a raw list 
 * of segments by their orientation (vertical vs. horizontal), triggers the construction 
 * of the corresponding Priority Search Trees (PST), and stores the resulting scene 
 * in the repository for future querying.
 */
public class BuildSceneService implements ISceneBuilderService {

  private final BuildStrategy buildStrategy;
  private final SimpleSplitStrategy<Segment> splitStrategy;

  private final IPSTRepository PSTRepository;

  /**
   * @param buildStrategy The algorithm used to build the Priority Search Trees (PST).
   * @param splitStrategy The algorithm used to partition the list of segments based on a specific predicate.
   * @param PSTRepository The repository where the fully constructed scene will be saved.
   */
  public BuildSceneService(
    BuildStrategy buildStrategy,
    SimpleSplitStrategy<Segment> splitStrategy,
    IPSTRepository PSTRepository
  ) {
    this.buildStrategy = buildStrategy;
    this.splitStrategy = splitStrategy;
    this.PSTRepository = PSTRepository;
  }

  /**
   * Executes the scene building workflow.
   * <p>
   * The execution follows these steps:
   * <ol>
   * <li>Instantiates a new {@link Scene}.</li>
   * <li>Partitions the input segments to separate vertical segments from horizontal ones.</li>
   * <li>Builds the vertical PST using the first partition.</li>
   * <li>Builds the horizontal PST using the second partition.</li>
   * <li>Persists the completely built scene into the repository.</li>
   * </ol>
   *
   * @param segments The raw list of {@link Segment}s to process and structure.
   * @return {@code null}, as this command-oriented use case does not return a direct result.
   */
  public Void execute(List<Segment> segments) {
    Scene scene = new Scene(buildStrategy);
    int splitIndex = splitStrategy.split(segments, Segment::isVertical);
    scene.buildVerticalTree(segments, 0, splitIndex);
    scene.buildHorizontalTree(segments, splitIndex, segments.size());
    PSTRepository.saveScene(scene);
    return null;
  }
}

package be.groupe18.windowing.application.service.windowQuerying;

import be.groupe18.windowing.application.strategy.query.QueryStrategy;
import be.groupe18.windowing.domain.model.QueryWindow;
import be.groupe18.windowing.domain.model.Scene;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.domain.utils.Tuple;
import be.groupe18.windowing.infrastructure.repository.pst.IPSTRepository;
import java.util.List;

/**
 * Application service (Use Case) responsible for executing window queries.
 * <p>
 * This class orchestrates the spatial search process to find segments within a specific area. 
 * It retrieves the current scene via its repository and delegates the algorithmic logic 
 * to a {@link QueryStrategy}, querying both the vertical and horizontal Priority Search Trees (PST).
 */
public class QueryWindowService implements IWindowQueryingService {

  private final QueryStrategy queryStrategy;
  private final IPSTRepository pstRepository;

  /**
   * @param queryStrategy The algorithm used to traverse the PSTs and find matching segments.
   * @param pstRepository The repository providing access to the currently loaded {@link Scene}.
   */
  public QueryWindowService(
    QueryStrategy queryStrategy,
    IPSTRepository pstRepository
  ) {
    this.queryStrategy = queryStrategy;
    this.pstRepository = pstRepository;
  }

  /**
   * Executes a spatial query to find all segments that are included in, or intersect with, 
   * the defined search window.
   * <p>
   * The method queries the vertical and horizontal segment trees separately and merges the results.
   *
   * @param windowQueryRequest The data transfer object containing the boundary coordinates (min/max X and Y).
   * @return A list containing all {@link Segment}s found within the window boundaries, 
   * or {@code null} if no scene is currently loaded in the repository.
   */
  @Override
  public List<Segment> execute(WindowQueryRequest windowQueryRequest) {
    Scene scene = pstRepository.getScene();
    if (scene == null) {
      return null;
    }
    Tuple<QueryWindow, QueryWindow> queryWindows =
      QueryWindow.buildQueryWindows(
        windowQueryRequest.minX(),
        windowQueryRequest.maxX(),
        windowQueryRequest.minY(),
        windowQueryRequest.maxY()
      );
    List<Segment> segments = queryStrategy.query(
      scene.getVerticalPst(),
      queryWindows.getV1()
    );
    segments.addAll(
      queryStrategy.query(scene.getHorizontalPst(), queryWindows.getV2())
    );
    return segments;
  }
}

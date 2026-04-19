package be.groupe18.windowing.infrastructure.repository.segment;

import be.groupe18.windowing.domain.exception.RepositoryException;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.infrastructure.SceneLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@link ISegmentRepository}.
 * <p>
 * This adapter uses a {@link SceneLoader} to read spatial data from the file system 
 * and caches the loaded {@link Segment}s in memory.
 */
public class SegmentsRepository implements ISegmentRepository {

  private final SceneLoader sceneLoader;

  private final List<Segment> segments = new ArrayList<>();

  /**
   * @param sceneLoader The utility component responsible for physically reading and parsing the files.
   */
  public SegmentsRepository(SceneLoader sceneLoader) {
    this.sceneLoader = sceneLoader;
  }

  /**
   * Reads segments from the specified file and updates the internal cache.
   * <p>
   * If the loading is successful, any previously stored segments are cleared and replaced 
   * by the new ones. If a low-level I/O error occurs, it is caught and re-thrown as a 
   * domain-specific {@link RepositoryException} to prevent leaking infrastructure details 
   * to the upper architectural layers.
   *
   * @param ressourcePath The path to the file to be loaded.
   * @throws RepositoryException If the file cannot be found, read, or parsed properly.
   */
  @Override
  public void loadFromFile(String ressourcePath) throws RepositoryException {
    List<Segment> loadedSegments = new ArrayList<>();
    try {
      loadedSegments = sceneLoader.loadScene(ressourcePath);
    } catch (IOException e) {
      System.err.println(
        "Echec du chargement de la scène de segments sélectionnée."
      );
      e.printStackTrace();
      throw new RepositoryException(
        "Impossible de charger les segments depuis la source",
        e
      );
    }
    segments.clear();
    segments.addAll(loadedSegments);
  }

  /**
   * Returns the cached list of segments.
   *
   * @return A list of {@link Segment}s. If no file has been successfully loaded yet, 
   * this will return an empty list.
   */
  @Override
  public List<Segment> getAllSegments() {
    return segments;
  }
}

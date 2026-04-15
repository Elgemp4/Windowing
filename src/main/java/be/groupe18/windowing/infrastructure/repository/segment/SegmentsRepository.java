package be.groupe18.windowing.infrastructure.repository.segment;

import be.groupe18.windowing.domain.exception.RepositoryException;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.infrastructure.SceneLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SegmentsRepository implements ISegmentRepository {

  private final SceneLoader sceneLoader;

  private final List<Segment> segments = new ArrayList<>();

  public SegmentsRepository(SceneLoader sceneLoader) {
    this.sceneLoader = sceneLoader;
  }

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

  @Override
  public List<Segment> getAllSegments() {
    return segments;
  }
}

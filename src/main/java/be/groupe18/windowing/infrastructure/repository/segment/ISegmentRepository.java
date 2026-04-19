package be.groupe18.windowing.infrastructure.repository.segment;

import be.groupe18.windowing.domain.model.Segment;
import java.util.List;

/**
 * Defines the contract for the repository responsible for managing the raw collection of {@link Segment}s.
 */
public interface ISegmentRepository {
  void loadFromFile(String ressourcePath);
  List<Segment> getAllSegments();
}

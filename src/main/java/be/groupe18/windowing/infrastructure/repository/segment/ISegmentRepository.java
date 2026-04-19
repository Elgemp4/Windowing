package be.groupe18.windowing.infrastructure.repository.segment;

import be.groupe18.windowing.domain.model.Segment;
import java.util.List;

public interface ISegmentRepository {
  void loadFromFile(String ressourcePath);
  List<Segment> getAllSegments();
}

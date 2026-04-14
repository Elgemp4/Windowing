package be.groupe18.windowing.infrastructure.repository;

import java.util.List;

import be.groupe18.windowing.domain.model.Segment;

public interface ISegmentRepository {
    void loadFromFile(String ressourcePath);
    List<Segment> getAllSegments();
}

package be.groupe18.windowing.infrastructure;

import java.io.IOException;
import java.util.List;

import be.groupe18.windowing.domain.model.Segment;

public interface SceneLoader {
    List<Segment> loadScene(String ressourcePath) throws IOException;
}

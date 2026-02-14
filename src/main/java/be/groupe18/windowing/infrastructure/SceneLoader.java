package be.groupe18.windowing.infrastructure;

import be.groupe18.windowing.models.Segment;

import java.io.IOException;
import java.util.List;

public interface SceneLoader {
    List<Segment> loadScene(String ressourcePath) throws IOException;
}

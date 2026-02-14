package be.groupe18.windowing.infrastructure;

import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.models.Vector2D;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileSceneLoader implements SceneLoader{
    @Override
    public List<Segment> loadScene(String ressourcePath) throws IOException {
        try(Stream<String> lines = Files.lines(Paths.get(ressourcePath))) {
            return lines
                .map(line -> line.trim().split(" "))
                .map(splitLine -> {
                    double[] parsed = Arrays.stream(splitLine).mapToDouble(Double::parseDouble).toArray();
                    return new Segment(new Vector2D(parsed[0], parsed[1]), new Vector2D(parsed[2], parsed[3]));
                }).toList();
        }
    }
}

package be.groupe18.windowing;

import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.models.Segment;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() {
        SceneLoader loader = new FileSceneLoader();

        List<Segment> segments = null;
        try  {
            segments = loader.loadScene("C:\\Users\\elgem\\Documents\\scenes\\scenes\\1000.txt");
        } catch (IOException e) {
            System.exit(0);
        }

        System.out.println(segments.get(20).getFirstPoint().getFirst() + "" + segments.get(20).getFirstPoint().getSecond());
        System.out.println(segments.get(20).getSecondPoint().getFirst() + "" + segments.get(20).getSecondPoint().getSecond());

    }
}

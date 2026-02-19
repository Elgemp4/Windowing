package be.groupe18.windowing;

import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.models.Scene;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.minimum.MinimumStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;
import be.groupe18.windowing.strategies.splitting.LinearSplitStrategy;
import be.groupe18.windowing.strategies.splitting.SplitStrategy;
import be.groupe18.windowing.utils.Pair;

import java.io.IOException;
import java.util.List;

public class Main {
    static void main() {
        SceneLoader loader = new FileSceneLoader();
        SplitStrategy<Segment> splitStrategy = new LinearSplitStrategy<>();
        BuildStrategy buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                null,
                new LinearSplitStrategy<>());

        QueryStrategy queryStrategy = null; //TODO: implement query strategy

        List<Segment> segments = null;
        try  {
            segments = loader.loadScene("C:\\Users\\elgem\\Documents\\scenes\\scenes\\1000.txt");
        } catch (IOException e) {
            System.exit(0);
        }

        Pair<List<Segment>, List<Segment>> splitResult = splitStrategy.split(segments, Segment::isVertical);
        Scene scene = new Scene(buildStrategy, queryStrategy);
        scene.buildVerticalTree(splitResult.getFirst());
        scene.buildHorizontalTree(splitResult.getSecond());

        //TODO: querying
    }
}

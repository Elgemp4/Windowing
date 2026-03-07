package be.groupe18.windowing;

import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.models.Scene;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.median.MedianStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.minimum.MinimumStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;
import be.groupe18.windowing.strategies.splitting.LinearSplitStrategy;
import be.groupe18.windowing.strategies.splitting.SplitStrategy;
import be.groupe18.windowing.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static void main() {


        SceneLoader loader = new FileSceneLoader();
        SplitStrategy<Segment> splitStrategy = new LinearSplitStrategy<>();
        BuildStrategy buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(),
                splitStrategy);

        QueryStrategy queryStrategy = null; //TODO: implement query strategy

        List<Segment> segments = null;
        try  {
            segments = loader.loadScene("/home/elgem/Downloads/Windowing/scenes/10.txt");
        } catch (IOException e) {
            System.exit(0);
        }

        int splitIndex  = splitStrategy.split(segments, Segment::isVertical);
        Scene scene = new Scene(buildStrategy, queryStrategy);
        scene.buildVerticalTree(segments, 0, splitIndex);
        scene.buildHorizontalTree(segments, splitIndex, segments.size());
        System.out.println(scene);
        //TODO: querying
    }
}

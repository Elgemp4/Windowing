package be.groupe18.windowing;

import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.models.Scene;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;
import be.groupe18.windowing.strategies.simple_split.LinearSimpleSplitStrategy;
import be.groupe18.windowing.strategies.simple_split.SimpleSplitStrategy;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        SceneLoader loader = new FileSceneLoader();
        BuildStrategy buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()),
                new LinearPivotSplitStrategy<>());

        QueryStrategy queryStrategy = null; //TODO: implement query strategy

        List<Segment> segments = null;
        try  {
            segments = loader.loadScene("/home/elgem/Downloads/Windowing/scenes/100000.txt");
        } catch (IOException e) {
            System.exit(0);
        }

        System.out.println("Before");
        SimpleSplitStrategy<Segment> simpleSplitStrategy = new LinearSimpleSplitStrategy<>();
        int splitIndex  = simpleSplitStrategy.split(segments, Segment::isVertical);
        Scene scene = new Scene(buildStrategy, queryStrategy);
        scene.buildVerticalTree(segments, 0, splitIndex);
        scene.buildHorizontalTree(segments, splitIndex, segments.size());
        System.out.println(scene);
        //TODO: querying
    }
}

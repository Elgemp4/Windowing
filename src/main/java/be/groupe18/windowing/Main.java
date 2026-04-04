package be.groupe18.windowing;

import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Scene;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;
import be.groupe18.windowing.strategies.query.SimpleQueryStrategy;
import be.groupe18.windowing.strategies.simple_split.LinearSimpleSplitStrategy;
import be.groupe18.windowing.strategies.simple_split.SimpleSplitStrategy;
import be.groupe18.windowing.utils.Pair;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        SceneLoader loader = new FileSceneLoader();
        BuildStrategy buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()),
                new LinearPivotSplitStrategy<>());

        QueryStrategy queryStrategy = new SimpleQueryStrategy();

        List<Segment> segments = null;
        String scenePath = System.getenv("sdd_java_scene");
        if (scenePath == null || scenePath.isBlank()) {
            System.err.println("'sdd_java_scene' variable environment isn't set.");
            System.exit(1);
        }
        try  {
            segments = loader.loadScene(scenePath); ///home/elgem/Downloads/Windowing/scenes/100000.txt
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

        Pair<QueryWindow,QueryWindow> queryWindows = QueryWindow.buildQueryWindows(0, 1000, 0, 1000);
        //TODO: querying
        //queryStrategy.query(PST tree, queryWindow);
    }
}

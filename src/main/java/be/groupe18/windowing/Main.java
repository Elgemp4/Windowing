package be.groupe18.windowing;

import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PST;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Scene;
import be.groupe18.windowing.models.Segment;
import be.groupe18.windowing.models.Vector2D;
import be.groupe18.windowing.strategies.build.BuildStrategy;
import be.groupe18.windowing.strategies.build.RecursiveBuildStrategy;
import be.groupe18.windowing.strategies.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.strategies.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.strategies.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.strategies.query.QueryStrategy;
import be.groupe18.windowing.strategies.query.SimpleQueryStrategy;
import be.groupe18.windowing.strategies.simple_split.LinearSimpleSplitStrategy;
import be.groupe18.windowing.strategies.simple_split.SimpleSplitStrategy;
import be.groupe18.windowing.utils.Tuple;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final double WINDOW_MIN_X = 0;
        final double WINDOW_MAX_X = 1000;
        final double WINDOW_MIN_Y = 0;
        final double WINDOW_MAX_Y = 1000;

        final double SCENE_WIDTH = 1000;
        final double SCENE_HEIGHT = 1000;

        SceneLoader loader = new FileSceneLoader();
        BuildStrategy buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>()));

        QueryStrategy queryStrategy = new SimpleQueryStrategy();

        List<Segment> segments = null;
        String scenePath = System.getenv("sdd_java_scene");
        if (scenePath == null || scenePath.isBlank()) {
            System.err.println("'sdd_java_scene' variable environment isn't set.");
            System.exit(1);
        }
        try  {
            segments = loader.loadScene(scenePath); ///home/elgem/Downloads/Windowing/scenes/100000.txt
            /*
            segments = new ArrayList<>();
            Segment a = new Segment(new Vector2D(2,2), new Vector2D(5,2));
            Segment b = new Segment(new Vector2D(3,3), new Vector2D(3,4));
            Segment c = new Segment(new Vector2D(5,6), new Vector2D(6,7));
            Segment d = new Segment(new Vector2D(4,4), new Vector2D(6,4));
            segments.add(a);
            segments.add(b);
            segments.add(c);
            segments.add(d);
            */
            if(segments == null) throw new IOException("exception");
        } catch (IOException e) {
            System.exit(0);
        }
        System.out.println("Before");
        SimpleSplitStrategy<Segment> simpleSplitStrategy = new LinearSimpleSplitStrategy<>();
        int splitIndex  = simpleSplitStrategy.split(segments, Segment::isVertical);
        Scene modelScene = new Scene(buildStrategy, queryStrategy);
        modelScene.buildVerticalTree(segments, 0, splitIndex);
        modelScene.buildHorizontalTree(segments, splitIndex, segments.size());
        System.out.println(modelScene);

        Tuple<QueryWindow,QueryWindow> queryWindows = QueryWindow.buildQueryWindows(WINDOW_MIN_X, WINDOW_MAX_X, WINDOW_MIN_Y, WINDOW_MAX_Y);
        List<Segment> queriedSegments = new ArrayList<>();
        queriedSegments.addAll(queryStrategy.query(modelScene.getVerticalPst(), queryWindows.getV1()));
        queriedSegments.addAll(queryStrategy.query(modelScene.getHorizontalPst(), queryWindows.getV2()));

        Canvas canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        drawSegments(gc, queriedSegments, WINDOW_MIN_X, WINDOW_MAX_X, WINDOW_MIN_Y, WINDOW_MAX_Y);

        Group root = new Group(canvas);
        javafx.scene.Scene fxScene = new javafx.scene.Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITESMOKE);

        primaryStage.setTitle("Windowing Project - Groupe 18");
        primaryStage.setScene(fxScene);
        primaryStage.show();
    }

    private void drawSegments(GraphicsContext gc, List<Segment> segments, double minX, double maxX, double minY, double maxY) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        double scaleX = canvasWidth / (maxX - minX);
        double scaleY = canvasHeight / (maxY - minY);

        gc.setStroke(Color.rgb(0, 0, 0, 0.1));
        gc.setLineWidth(1.0);

        for (Segment segment : segments) {
            double x1 = segment.getFirstPoint().getX().getAsDouble();
            double y1 = segment.getFirstPoint().getY().getAsDouble();
            double x2 = segment.getSecondPoint().getX().getAsDouble();
            double y2 = segment.getSecondPoint().getY().getAsDouble();
            
            // 2. Application de l'échelle et translation par rapport à l'origine de la fenêtre
            double screenX1 = (x1 - minX) * scaleX;
            double screenX2 = (x2 - minX) * scaleX;
            
            // 3. Application de l'échelle sur Y avec inversion de l'axe (optionnel mais recommandé)
            // (Canvas : Y=0 est en haut / Math : Y=0 est souvent en bas)
            double screenY1 = canvasHeight - ((y1 - minY) * scaleY); 
            double screenY2 = canvasHeight - ((y2 - minY) * scaleY);

            System.out.println("Dessin de : " + screenX1 + ", " + screenY1 + " à " + screenX2 + ", " + screenY2);
            gc.strokeLine(screenX1, screenY1, screenX2, screenY2);
        }
    }
}

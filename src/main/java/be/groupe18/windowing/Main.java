package be.groupe18.windowing;

import be.groupe18.windowing.application.service.IFileDialogService;
import be.groupe18.windowing.application.service.JavaFXFileDialogService;
import be.groupe18.windowing.application.strategy.build.BuildStrategy;
import be.groupe18.windowing.application.strategy.build.RecursiveBuildStrategy;
import be.groupe18.windowing.application.strategy.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.application.strategy.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.application.strategy.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.application.strategy.query.QueryStrategy;
import be.groupe18.windowing.application.strategy.query.SimpleQueryStrategy;
import be.groupe18.windowing.application.strategy.simple_split.LinearSimpleSplitStrategy;
import be.groupe18.windowing.application.strategy.simple_split.SimpleSplitStrategy;
import be.groupe18.windowing.domain.model.QueryWindow;
import be.groupe18.windowing.domain.model.Scene;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.domain.utils.Tuple;
import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.infrastructure.repository.ISegmentRepository;
import be.groupe18.windowing.infrastructure.repository.SegmentsRepository;
import be.groupe18.windowing.presentation.viewController.MainViewController;
import be.groupe18.windowing.presentation.viewmodel.MainViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
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
            segments = loader.loadScene(scenePath);
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

        try {
            IFileDialogService fileDialogService = new JavaFXFileDialogService(primaryStage);
            ISegmentRepository segmentRepository = new SegmentsRepository(loader);
            MainViewModel mainViewModel = new MainViewModel(fileDialogService, segmentRepository);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/MainView.fxml"));
            fxmlLoader.setControllerFactory(type -> {
            if (type == MainViewController.class) {
                return new MainViewController(mainViewModel); 
            }
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                System.err.println("Erreur lors de l'instanciation du viewModel !");
                e.printStackTrace();
                System.exit(0);
                throw new RuntimeException();
            }
            });
            Parent root = fxmlLoader.load();
            MainViewController viewController = fxmlLoader.getController();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            primaryStage.setTitle("Windowing Project - Groupe 18");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML !");
            e.printStackTrace();
            System.exit(0);
        }
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

package be.groupe18.windowing;

import be.groupe18.windowing.application.service.fileDialog.IFileDialogService;
import be.groupe18.windowing.application.service.fileDialog.JavaFXFileDialogService;
import be.groupe18.windowing.application.service.sceneBuilding.BuildSceneService;
import be.groupe18.windowing.application.service.sceneBuilding.ISceneBuilderService;
import be.groupe18.windowing.application.service.windowQuerying.IWindowQueryingService;
import be.groupe18.windowing.application.service.windowQuerying.QueryWindowService;
import be.groupe18.windowing.application.strategy.build.BuildStrategy;
import be.groupe18.windowing.application.strategy.build.RecursiveBuildStrategy;
import be.groupe18.windowing.application.strategy.median.QuickSelectMedianStrategy;
import be.groupe18.windowing.application.strategy.minimum.LinearMinimumStrategy;
import be.groupe18.windowing.application.strategy.pivot_split.LinearPivotSplitStrategy;
import be.groupe18.windowing.application.strategy.query.QueryStrategy;
import be.groupe18.windowing.application.strategy.query.SimpleQueryStrategy;
import be.groupe18.windowing.application.strategy.simple_split.LinearSimpleSplitStrategy;
import be.groupe18.windowing.application.strategy.simple_split.SimpleSplitStrategy;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.infrastructure.FileSceneLoader;
import be.groupe18.windowing.infrastructure.SceneLoader;
import be.groupe18.windowing.infrastructure.repository.pst.IPSTRepository;
import be.groupe18.windowing.infrastructure.repository.pst.PSTRepository;
import be.groupe18.windowing.infrastructure.repository.segment.ISegmentRepository;
import be.groupe18.windowing.infrastructure.repository.segment.SegmentsRepository;
import be.groupe18.windowing.presentation.viewController.MainViewController;
import be.groupe18.windowing.presentation.viewmodel.MainViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String MAIN_FXML_PATH = "view/MainView.fxml";
    private static final String WINDOW_TITLE = "Windowing Project - Groupe 18";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            MainViewModel mainViewModel = assembleDependencies(primaryStage);
            initializeView(primaryStage, mainViewModel);
        } catch (IOException e) {
            e.printStackTrace();
            showFatalErrorDialog(e);
        }
    }

    private MainViewModel assembleDependencies(Stage primaryStage) {
        BuildStrategy buildStrategy = new RecursiveBuildStrategy(
                new LinearMinimumStrategy<>(),
                new QuickSelectMedianStrategy<>(new LinearPivotSplitStrategy<>())
        );
        QueryStrategy queryStrategy = new SimpleQueryStrategy();
        SimpleSplitStrategy<Segment> simpleSplitStrategy = new LinearSimpleSplitStrategy<>();

        SceneLoader sceneLoader = new FileSceneLoader();
        ISegmentRepository segmentRepository = new SegmentsRepository(sceneLoader);
        IPSTRepository pstRepository = new PSTRepository();

        IFileDialogService fileDialogService = new JavaFXFileDialogService(primaryStage);
        ISceneBuilderService sceneQueryService = new BuildSceneService(buildStrategy, queryStrategy, simpleSplitStrategy, pstRepository);
        IWindowQueryingService windowQueryingService = new QueryWindowService(queryStrategy, pstRepository);

        return new MainViewModel(fileDialogService, segmentRepository, sceneQueryService, windowQueryingService);
    }

    private void initializeView(Stage primaryStage, MainViewModel mainViewModel) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MAIN_FXML_PATH));
        
        fxmlLoader.setControllerFactory(type -> createController(type, mainViewModel));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Object createController(Class<?> type, MainViewModel mainViewModel) {
        if (type == MainViewController.class) {
            return new MainViewController(mainViewModel);
        }
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'instanciation du contrôleur : " + type.getName());
            e.printStackTrace();
            System.exit(1);
            throw new RuntimeException(e);
        }
    }

    private void showFatalErrorDialog(Exception e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Erreur fatale");
        alert.setHeaderText("L'application n'a pas pu démarrer");
        alert.setContentText("Une erreur inattendue s'est produite : " + e.getMessage());
        alert.showAndWait();
        javafx.application.Platform.exit();
    }
}

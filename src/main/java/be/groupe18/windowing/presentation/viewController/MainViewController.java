package be.groupe18.windowing.presentation.viewController;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import be.groupe18.windowing.presentation.viewmodel.MainViewModel;
import javafx.fxml.FXML;

public class MainViewController {

    @FXML private Canvas mainCanvas;
    @FXML private Button loadSceneButton;

    private final MainViewModel viewModel;

    public MainViewController(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        mainCanvas.widthProperty().bind(viewModel.canvasWidthProperty());
        mainCanvas.heightProperty().bind(viewModel.canvasHeightProperty());

        //TEST
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTBLUE); // Couleur de test
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    @FXML
    public void onLoadClicked() {
        viewModel.onLoadClicked();
    }
}
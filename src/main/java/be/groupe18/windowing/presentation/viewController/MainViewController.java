package be.groupe18.windowing.presentation.viewController;

import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.presentation.components.NumericTextField;
import be.groupe18.windowing.presentation.viewmodel.MainViewModel;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MainViewController {

  @FXML
  private Pane canvasContainer;

  @FXML
  private Canvas mainCanvas;

  @FXML
  private Button loadSceneButton;

  @FXML
  private Label errorLabel;

  @FXML
  private NumericTextField xMinField;

  @FXML
  private NumericTextField xMaxField;

  @FXML
  private NumericTextField yMinField;

  @FXML
  private NumericTextField yMaxField;

  private final MainViewModel viewModel;

  public MainViewController(MainViewModel viewModel) {
    this.viewModel = viewModel;
  }

  @FXML
  public void initialize() {
    mainCanvas.widthProperty().bind(canvasContainer.widthProperty());
    mainCanvas.heightProperty().bind(canvasContainer.heightProperty());

    mainCanvas
      .widthProperty()
      .addListener((observable, oldValue, newValue) -> draw());
    mainCanvas
      .heightProperty()
      .addListener((observable, oldValue, newValue) -> draw());

    errorLabel.textProperty().bind(viewModel.errorMessageProperty());

    xMinField.valueProperty().bindBidirectional(viewModel.xMinProperty());
    xMaxField.valueProperty().bindBidirectional(viewModel.xMaxProperty());
    yMinField.valueProperty().bindBidirectional(viewModel.yMinProperty());
    yMaxField.valueProperty().bindBidirectional(viewModel.yMaxProperty());

    viewModel
      .segmentsProperty()
      .addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          draw();
        }
      });

    draw();
  }

  @FXML
  public void onLoadClicked() {
    viewModel.onLoadClicked();
  }

  @FXML
  public void onQueryClicked() {
    viewModel.onQueryClicked();
  }

  private void draw() {
    drawBackground();

    List<Segment> currentSegments = viewModel.segmentsProperty().getValue();
    if (currentSegments != null && !currentSegments.isEmpty()) {
      drawSegments(currentSegments);
    }
  }

  private void drawBackground() {
    double width = mainCanvas.getWidth();
    double height = mainCanvas.getHeight();
    if (width == 0 || height == 0) return;

    GraphicsContext gc = mainCanvas.getGraphicsContext2D();
    gc.clearRect(0, 0, width, height);
    gc.setFill(Color.LIGHTBLUE);
    gc.fillRect(0, 0, width, height);
  }

  private void drawSegments(List<Segment> segments) {
    double width = mainCanvas.getWidth();
    double height = mainCanvas.getHeight();
    if (width == 0 || height == 0 || segments.isEmpty()) return;

    double renderXMin = viewModel.xMinProperty().get();
    double renderXMax = viewModel.xMaxProperty().get();
    double renderYMin = viewModel.yMinProperty().get();
    double renderYMax = viewModel.yMaxProperty().get();

    if (
      Double.isInfinite(renderXMin) ||
      Double.isInfinite(renderXMax) ||
      Double.isInfinite(renderYMin) ||
      Double.isInfinite(renderYMax)
    ) {
      double actualMinX = Double.MAX_VALUE;
      double actualMaxX = -Double.MAX_VALUE;
      double actualMinY = Double.MAX_VALUE;
      double actualMaxY = -Double.MAX_VALUE;

      for (Segment s : segments) {
        double x1 = s.getFirstPoint().getX().getAsDouble();
        double y1 = s.getFirstPoint().getY().getAsDouble();
        double x2 = s.getSecondPoint().getX().getAsDouble();
        double y2 = s.getSecondPoint().getY().getAsDouble();

        actualMinX = Math.min(actualMinX, Math.min(x1, x2));
        actualMaxX = Math.max(actualMaxX, Math.max(x1, x2));
        actualMinY = Math.min(actualMinY, Math.min(y1, y2));
        actualMaxY = Math.max(actualMaxY, Math.max(y1, y2));
      }

      if (Double.isInfinite(renderXMin)) renderXMin = actualMinX;
      if (Double.isInfinite(renderXMax)) renderXMax = actualMaxX;
      if (Double.isInfinite(renderYMin)) renderYMin = actualMinY;
      if (Double.isInfinite(renderYMax)) renderYMax = actualMaxY;
    }

    if (renderXMax - renderXMin == 0) {
      renderXMin -= 10;
      renderXMax += 10;
    }
    if (renderYMax - renderYMin == 0) {
      renderYMin -= 10;
      renderYMax += 10;
    }

    GraphicsContext gc = mainCanvas.getGraphicsContext2D();
    gc.setStroke(Color.rgb(0, 0, 0, 0.5));
    gc.setLineWidth(1);

    double scaleX = width / (renderXMax - renderXMin);
    double scaleY = height / (renderYMax - renderYMin);

    for (Segment segment : segments) {
      double x1 =
        (segment.getFirstPoint().getX().getAsDouble() - renderXMin) * scaleX;
      double x2 =
        (segment.getSecondPoint().getX().getAsDouble() - renderXMin) * scaleX;

      double y1 =
        height -
        ((segment.getFirstPoint().getY().getAsDouble() - renderYMin) * scaleY);
      double y2 =
        height -
        ((segment.getSecondPoint().getY().getAsDouble() - renderYMin) * scaleY);

      gc.strokeLine(x1, y1, x2, y2);
    }
  }
}

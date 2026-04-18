package be.groupe18.windowing.presentation.viewController;

import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.domain.model.Vector2D;
import be.groupe18.windowing.presentation.components.NumericTextField;
import be.groupe18.windowing.presentation.viewmodel.MainViewModel;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MainViewController {

  private Segment selectedSegment = null;

  @FXML
  private Pane canvasContainer;

  @FXML
  private Canvas mainCanvas;

  @FXML
  private Button loadSceneButton;

  @FXML
  private ListView<Segment> segmentListView;

  @FXML
  private Label errorLabel;
  @FXML
  private Label successLabel;
  @FXML
  private Label totalCountLabel;
  private final List<ChangeListener<?>> strongListeners = new java.util.ArrayList<>();
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
    successLabel.textProperty().bind(viewModel.successMessageProperty());
    totalCountLabel.textProperty().bind(viewModel.totalCountMessageProperty());

    setupStrongBinding(xMinField, viewModel.getXMin());
    setupStrongBinding(xMaxField, viewModel.getXMax());
    setupStrongBinding(yMinField, viewModel.getYMin());
    setupStrongBinding(yMaxField, viewModel.getYMax());

    viewModel
      .getSegments()
      .addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          draw();
        }
      });

    segmentListView.setCellFactory(lv -> {
      return new ListCell<>() {
        @Override
        protected void updateItem(Segment segment, boolean empty) {
          super.updateItem(segment, empty);
          if (empty || segment == null) {
            setText(null);
          } else {
            Vector2D firstPoint = segment.getFirstPoint();
            double x1 = firstPoint.getX().getAsDouble();
            double y1 = firstPoint.getY().getAsDouble();
            Vector2D secondPoint = segment.getSecondPoint();
            double x2 = secondPoint.getX().getAsDouble();
            double y2 = secondPoint.getY().getAsDouble();

            setText(String.format("%s | [%.1f, %.1f] -> [%.1f, %.1f]",
                    (segment.isVertical() ? "Vertical " : "Horizontal"), x1, y1, x2, y2));
          }
        }
      };
    });

    // 2. Listen for clicks (selection changes) on the ListView
    segmentListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      selectedSegment = newSelection;
      draw(); // Redraw the canvas to apply/remove the highlight
    });

    draw();
  }
  private void setupStrongBinding(NumericTextField field, DoubleProperty viewModelProperty) {

    ChangeListener<String> uiListener = (obs, oldVal, newVal) -> {
      try {
        if (newVal == null || newVal.isEmpty()) {
          return;
        }
        double parsed;

        if(newVal.equals("-inf")) {
          parsed = Double.NEGATIVE_INFINITY;
        }
        else if(newVal.equals("+inf")){
          parsed = Double.POSITIVE_INFINITY;
        }
        else{
          parsed = Double.parseDouble(newVal);
        }
        viewModelProperty.set(parsed);
      } catch (NumberFormatException ignored) {
      }
    };

    ChangeListener<Number> modelListener = (obs, oldVal, newVal) -> {
      if (newVal != null) {
        String newText = newVal.toString();
        field.setText(newText);
      }
    };

    field.textProperty().addListener(uiListener);
    viewModelProperty.addListener(modelListener);

    strongListeners.add(uiListener);
    strongListeners.add(modelListener);
  }

  @FXML
  public void onLoadClicked() {
    viewModel.onLoadClicked();
    loadSceneButton.getParent().requestFocus();
  }

  @FXML
  public void onQueryClicked() {
    viewModel.onQueryClicked();
    List<Segment> currentSegments = viewModel.getSegments().getValue();

    segmentListView.getItems().clear();
    if (currentSegments != null) {
      segmentListView.getItems().addAll(currentSegments);
    }
  }



  private void draw() {
    Platform.runLater(() -> {
      drawBackground();

      List<Segment> currentSegments = viewModel.getSegments().getValue();
      if (currentSegments != null && !currentSegments.isEmpty()) {
        drawSegments(currentSegments);
      }
    });
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

  private void drawSegment(Segment segment, double renderXMin, double renderYMin, double scaleX, double scaleY, GraphicsContext gc) {
    double x1 =
            (segment.getFirstPoint().getX().getAsDouble() - renderXMin) * scaleX;
    double x2 =
            (segment.getSecondPoint().getX().getAsDouble() - renderXMin) * scaleX;

    double y1 =
            (segment.getFirstPoint().getY().getAsDouble() - renderYMin) * scaleY;
    double y2 =
            (segment.getSecondPoint().getY().getAsDouble() - renderYMin) * scaleY;

    gc.strokeLine(x1, y1, x2, y2);
  }


  private void drawSegments(List<Segment> segments) {
    double width = mainCanvas.getWidth();
    double height = mainCanvas.getHeight();
    if (width == 0 || height == 0 || segments.isEmpty()) return;

    double renderXMin = viewModel.getXMin().get();
    double renderXMax = viewModel.getXMax().get();
    double renderYMin = viewModel.getYMin().get();
    double renderYMax = viewModel.getYMax().get();

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
      if (segment == selectedSegment) {
        continue;
      }

      drawSegment(segment, renderXMin, renderYMin, scaleX, scaleY, gc);
    }
    if(selectedSegment != null){
      gc.setStroke(Color.RED);
      gc.setLineWidth(3.0);
      drawSegment(selectedSegment, renderXMin, renderYMin, scaleX, scaleY, gc);
    }
  }
}

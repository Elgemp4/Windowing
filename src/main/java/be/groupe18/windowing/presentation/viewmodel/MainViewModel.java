package be.groupe18.windowing.presentation.viewmodel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import be.groupe18.windowing.application.service.fileDialog.IFileDialogService;
import be.groupe18.windowing.application.service.sceneBuilding.ISceneBuilderService;
import be.groupe18.windowing.application.service.windowQuerying.IWindowQueryingService;
import be.groupe18.windowing.application.service.windowQuerying.WindowQueryRequest;
import be.groupe18.windowing.domain.exception.RepositoryException;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.infrastructure.repository.segment.ISegmentRepository;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class MainViewModel {
    private final IFileDialogService fileDialogService;
    private final ISegmentRepository segmentsRepository;
    private final ISceneBuilderService sceneBuildService;
    private final IWindowQueryingService windowQueryService;

    private final StringProperty errorMessage = new SimpleStringProperty("");

    private final DoubleProperty xMin = new SimpleDoubleProperty(0.0);
    private final DoubleProperty xMax = new SimpleDoubleProperty(100.0);
    private final DoubleProperty yMin = new SimpleDoubleProperty(0.0);
    private final DoubleProperty yMax = new SimpleDoubleProperty(100.0);

    private final ListProperty<Segment> segments = new SimpleListProperty<>();

    public StringProperty errorMessageProperty() { return errorMessage; }

    public ListProperty<Segment> segmentsProperty() { return segments; }

    public DoubleProperty xMinProperty() { return xMin; }
    public DoubleProperty xMaxProperty() { return xMax; }
    public DoubleProperty yMinProperty() { return yMin; }
    public DoubleProperty yMaxProperty() { return yMax; }

    public MainViewModel(IFileDialogService fileDialogService, ISegmentRepository segmentsRepository,
        ISceneBuilderService sceneQueryService, IWindowQueryingService windowQueryService) {
        this.fileDialogService = fileDialogService;
        this.segmentsRepository = segmentsRepository;
        this.sceneBuildService = sceneQueryService;
        this.windowQueryService = windowQueryService;
    }

    public void onLoadClicked() {
        errorMessage.set("");
        File selectedFile = fileDialogService.execute("Sélectionner une scène à charger.");

        if(selectedFile == null) {
            errorMessage.set("Aucun fichier sélectionné.");
            return;
        }

        try {
            segmentsRepository.loadFromFile(selectedFile.getAbsolutePath());
            List<Segment> loadedSegments = segmentsRepository.getAllSegments();
            if(loadedSegments == null || loadedSegments.isEmpty()) {
                errorMessage.set("Le fichier chargé ne contient aucun segment.");
                segmentsProperty().set(FXCollections.emptyObservableList());
                return;
            }
            sceneBuildService.execute(loadedSegments);
        } catch (RepositoryException e) {
            errorMessage.set("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    public void onQueryClicked() {
        double minXVal = xMin.get();
        double maxXVal = xMax.get();
        double minYVal = yMin.get();
        double maxYVal = yMax.get();

        if (minXVal > maxXVal || minYVal > maxYVal) {
            errorMessage.set("Les valeurs min doivent être inférieures aux valeurs max.");
            return;
        }

        List<Segment> segments = segmentsRepository.getAllSegments();
        if(segments == null || segments.isEmpty()) {
            segmentsProperty().set(FXCollections.emptyObservableList());
            errorMessage.set("Aucun segment trouvé.");
            return;
        }
        List<Segment> queriedSegments = windowQueryService.execute(new WindowQueryRequest(minXVal, maxXVal, minYVal, maxYVal));
        if (queriedSegments.isEmpty()) {
            errorMessage.set("Aucun segment trouvé dans la zone.");
        }
        segmentsProperty().set(FXCollections.observableArrayList(queriedSegments));
    }
}

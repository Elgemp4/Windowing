package be.groupe18.windowing.presentation.viewmodel;

import java.io.File;

import be.groupe18.windowing.application.service.IFileDialogService;
import be.groupe18.windowing.infrastructure.repository.ISegmentRepository;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MainViewModel {
    private final IFileDialogService fileDialogService;
    private final ISegmentRepository segmentsRepository;

    private final DoubleProperty canvasWidth = new SimpleDoubleProperty(1000);
    private final DoubleProperty canvasHeight = new SimpleDoubleProperty(1000);

    public DoubleProperty canvasWidthProperty() { return canvasWidth; }
    public DoubleProperty canvasHeightProperty() { return canvasHeight; }

    public MainViewModel(IFileDialogService fileDialogService, ISegmentRepository segmentsRepository) {
        this.fileDialogService = fileDialogService;
        this.segmentsRepository = segmentsRepository;
    }

    public void onLoadClicked() {
        File selectedFile = fileDialogService.openFileBrowser("Sélectionner une scène à charger.");
        if(selectedFile == null) {
            //afficher texte pour prévenir que ça a échoué
            return;
        };
        segmentsRepository.loadFromFile(selectedFile.getAbsolutePath());
    }
}

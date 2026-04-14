package be.groupe18.windowing.application.service;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Window;

public class JavaFXFileDialogService implements IFileDialogService{

    private final Window window;

    public JavaFXFileDialogService(Window window) {
        this.window = window;
    }

    @Override
    public File openFileBrowser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Fichiers texte", "*.txt")
        );

        return fileChooser.showOpenDialog(window);
    }
}

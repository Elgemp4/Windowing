package be.groupe18.windowing.application.service.fileDialog;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * JavaFX-specific implementation of the {@link IFileDialogService}.
 * <p>
 * This class utilizes the JavaFX {@link FileChooser} to open a native operating system 
 * file selection dialog. By default, it is configured to filter and accept only text files.
 */
public class JavaFXFileDialogService implements IFileDialogService {

  private final Window window;

  /**
   * @param window The parent {@link Window}
   */
  public JavaFXFileDialogService(Window window) {
    this.window = window;
  }

  /**
   * Opens the file selection dialog and pauses execution until the user selects a file or cancels.
   * <p>
   * This implementation enforces a file extension filter, restricting the user's view 
   * to text files ({@code *.txt}).
   *
   * @param title The text to display in the title bar of the file chooser dialog.
   * @return The selected {@link File}, or {@code null} if the user cancelled or closed the dialog.
   */
  @Override
  public File execute(String title) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(title);

    fileChooser
      .getExtensionFilters()
      .add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));

    return fileChooser.showOpenDialog(window);
  }
}

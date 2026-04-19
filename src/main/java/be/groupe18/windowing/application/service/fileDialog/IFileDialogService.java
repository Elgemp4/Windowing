package be.groupe18.windowing.application.service.fileDialog;

import be.groupe18.windowing.application.service.IService;
import java.io.File;

/**
 * Defines the contract for a file selection dialog service.
 * <p>
 * This service provides an abstraction layer over the system's native file explorer.
 * It allows the application (e.g., ViewModels) to prompt the user to select a file 
 * without becoming tightly coupled to a specific UI framework.
 */
public interface IFileDialogService extends IService<String, File> {}

package be.groupe18.windowing.infrastructure.repository.pst;

import be.groupe18.windowing.domain.model.Scene;

/**
 * A simple, in-memory implementation of the {@link IPSTRepository}.
 * <p>
 * This adapter stores the active {@link Scene} directly in the application's RAM (memory).
 * It is designed to hold the state of the spatial environment during the current session,
 * meaning any stored data will be lost when the application is closed or restarted.
 */
public class PSTRepository implements IPSTRepository {

  private Scene scene;

  /**
   * Stores the provided scene in memory. 
   * <p>
   * Note that calling this method will completely overwrite the previously stored scene.
   *
   * @param scene The new {@link Scene} to hold in memory.
   */
  public void saveScene(Scene scene) {
    this.scene = scene;
  }

  /**
   * Retrieves the scene currently held in memory.
   *
   * @return The stored {@link Scene}, or {@code null} if no scene is currently loaded.
   */
  public Scene getScene() {
    return scene;
  }
}

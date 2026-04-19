package be.groupe18.windowing.infrastructure.repository.pst;

import be.groupe18.windowing.domain.model.Scene;

/**
 * Defines the contract for the repository managing the application's {@link Scene} state.
 */
public interface IPSTRepository {
  void saveScene(Scene scene);
  Scene getScene();
}

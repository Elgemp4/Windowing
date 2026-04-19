package be.groupe18.windowing.infrastructure.repository.pst;

import be.groupe18.windowing.domain.model.Scene;

public interface IPSTRepository {
  void saveScene(Scene scene);
  Scene getScene();
}

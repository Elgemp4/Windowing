package be.groupe18.windowing.infrastructure.repository.pst;

import be.groupe18.windowing.domain.model.Scene;

public class PSTRepository implements IPSTRepository{
    
    private Scene scene;

    public void saveScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() { return scene; }
}

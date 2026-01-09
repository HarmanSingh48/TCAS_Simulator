package main.java.com.tcas_sim.view.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import main.java.com.tcas_sim.view.controller.SceneController;

public class ControlledScene<T extends SceneController> {
    private final Scene scene;
    private final T controller;

    public ControlledScene(final String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            scene = new Scene(loader.load());
            controller = loader.getController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T getController() {
        return controller;
    }

    public Scene getScene() {
        return scene;
    }


}

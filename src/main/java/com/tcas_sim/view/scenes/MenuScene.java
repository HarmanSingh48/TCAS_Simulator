package main.java.com.tcas_sim.view.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.java.com.tcas_sim.view.SceneController;

import java.io.IOException;

public class MenuScene {
    private SceneController controller;
    private final Scene menuScene;

    public MenuScene(SceneController controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/app.fxml"));
        loader.setController(controller);
        try {
            menuScene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SceneController getController() {
        return this.controller;
    }

    public Scene getScene() {
        return this.menuScene;
    }
}

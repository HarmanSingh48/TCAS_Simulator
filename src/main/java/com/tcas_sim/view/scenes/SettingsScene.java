package main.java.com.tcas_sim.view.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import main.java.com.tcas_sim.view.SettingsController;

public class SettingsScene {
    private final SettingsController controller;
    private final Scene settingsScene;

    public SettingsScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/Settings.fxml"));
            settingsScene = new Scene(loader.load());
            controller = loader.getController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(controller == null) {
            throw new RuntimeException("Setting Controller is Null");
        }
    }

    public SettingsController getController() {
        return this.controller;
    }

    public Scene getScene() {
        return this.settingsScene;
    }
}

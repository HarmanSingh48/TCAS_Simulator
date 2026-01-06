package main.java.com.tcas_sim.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.tcas_sim.view.scenes.MenuScene;
import main.java.com.tcas_sim.view.scenes.SettingsScene;

public class SceneController {
    private Stage stage;
    private Parent root;
    private MenuScene menuScene;
    private Scene runScene;
    private SettingsScene settingScene;

    public SceneController() {
        try {
            menuScene = new MenuScene(this);

            settingScene = new SettingsScene();

            root = FXMLLoader.load(getClass().getResource("/main/resources/Simulation.fxml"));
            runScene = new Scene(root);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf(e.getMessage());
        }
    }
    @FXML
    private void switchToMenu(ActionEvent e) {
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(menuScene.getScene());
        stage.show();
    }
    @FXML
    private void startApplication(ActionEvent e) {
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(runScene);
        stage.show();
    }
    @FXML
    private void switchToSettings(ActionEvent e) {
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(settingScene.getScene());
        stage.show();
    }
    @FXML
    private void exitApp(ActionEvent e) {
        Platform.exit();
    }

    public SettingsController getSettingController() {
        return this.settingScene.getController();
    }


}

package main.java.com.tcas_sim.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private Stage stage;
    private Parent root;
    private Scene menuScene;
    private Scene runScene;
    private Scene settingScene;
    private FXMLLoader loader;
    public SceneController() {
        try {
            loader = new FXMLLoader(getClass().getResource("/main/resources/app.fxml"));
            loader.setController(this);
            menuScene = new Scene(loader.load());

            root = FXMLLoader.load(getClass().getResource("/main/resources/Settings.fxml"));
            settingScene = new Scene(root);

            root = FXMLLoader.load(getClass().getResource("/main/resources/Simulation.fxml"));
            runScene = new Scene(root);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf(e.getMessage());
        }
    }
    public void switchToMenu(ActionEvent e) {
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
        stage.show();
    }

    public void startApplication(ActionEvent e) {
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(runScene);
        stage.show();
    }

    public void switchToSettings(ActionEvent e) {
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(settingScene);
        stage.show();
    }

}

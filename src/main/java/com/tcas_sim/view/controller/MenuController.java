package main.java.com.tcas_sim.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController extends SceneController {
    @FXML
    private Button menuStartButton;

    @FXML
    private Button menuSettingsButton;

    @FXML
    private Button menuQuitButton;

    @FXML
    private Button menuMuteButton;


    public Button getMenuStartButton() {
        return this.menuStartButton;
    }

    public Button getMenuSettingsButton() {
        return this.menuSettingsButton;
    }

    public Button getMenuQuitButton() {
        return this.menuQuitButton;
    }

    public Button getMenuMuteButton() {
        return menuMuteButton;
    }

}

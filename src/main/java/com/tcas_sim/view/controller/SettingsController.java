package main.java.com.tcas_sim.view.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends SceneController implements Initializable {
    @FXML
    CheckBox debugCheckBox;
    @FXML
    Slider volumeSlider;
    @FXML
    Button settingsBackButton;

    private final SimpleBooleanProperty isDebugEnabled = new SimpleBooleanProperty(false);

    private final SimpleDoubleProperty volume = new SimpleDoubleProperty();


    private void setUp() {
        volume.bind(volumeSlider.valueProperty());
        isDebugEnabled.bind(debugCheckBox.selectedProperty());
        volumeSlider.setValue(50.0);
    }

    public DoubleProperty getVolumeProperty() {
        return this.volume;
    }

    public BooleanProperty isDebugEnabledProperty() {
        return isDebugEnabled;
    }
    public void setEventHandlerForBackButton(EventHandler<ActionEvent> eH) {
        settingsBackButton.setOnAction(eH);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();
    }
}

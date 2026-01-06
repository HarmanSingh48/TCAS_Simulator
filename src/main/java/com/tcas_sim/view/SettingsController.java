package main.java.com.tcas_sim.view;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    CheckBox debugCheckBox;
    @FXML
    Slider volumeSlider;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();
    }
}

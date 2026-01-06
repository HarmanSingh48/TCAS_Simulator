package main.java.com.tcas_sim.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.java.com.tcas_sim.Main;

import java.io.File;
import java.io.IOException;

public class AppController {
    private final SceneController sc = new SceneController();
    private final MediaPlayer audio;
    public AppController(Stage stage) throws IOException {
        String file = new File("src/main/resources/music.mp3").toURI().toString();
        Media med = new Media(file);
        audio = new MediaPlayer(med);
        init(stage);
    }

    private void init (Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/resources/app.fxml"));
        loader.setController(sc);
        Parent root = loader.load();
        setUpListeners();
        stage.setTitle("TCAS Simulation");
        stage.setScene(new Scene(root));
        stage.show();
        audio.play();
    }

    private void setUpListeners() {
        sc.getSettingController().getVolumeProperty().addListener(
                (observableValue, number, t1) ->
                        audio.setVolume(t1.intValue() / 100d));
        //sc.getSettingController().getVolumeProperty().getValue().intValue()

        sc.getSettingController().isDebugEnabledProperty().addListener(
                (observableValue, aBoolean, t1) ->
                        System.out.println("Debug: " + t1)
        );
    }

}

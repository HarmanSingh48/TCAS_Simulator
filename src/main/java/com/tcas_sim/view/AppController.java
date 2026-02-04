package main.java.com.tcas_sim.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.java.com.tcas_sim.sim.Simulation;
import main.java.com.tcas_sim.view.controller.MenuController;
import main.java.com.tcas_sim.view.controller.RunController;
import main.java.com.tcas_sim.view.controller.SettingsController;
import main.java.com.tcas_sim.view.scenes.ControlledScene;

import java.io.File;

public class AppController {
    private final MediaPlayer audio;
    private Simulation sim;
    private final Stage window;
    private AnimationTimer timer;

    //Scenes
    private ControlledScene<RunController> simScene;
    private ControlledScene<MenuController> menuScene;
    private ControlledScene<SettingsController> settingScene;
    //End Scenes

    private boolean isDebug = false;
    private boolean isMute = false;

    private ImageView volume_on_img;
    private ImageView volume_off_img;

    public AppController(Stage stage) {
        String file = new File("src/main/resources/music.mp3").toURI().toString();
        Media med = new Media(file);
        window = stage;
        audio = new MediaPlayer(med);
        loadResources();
        setUpButtons();
        init(window);
    }

    private void init (Stage stage) {
        setUpListeners();
        setUpStage(stage);
        setUpAudio();
        audio.setMute(true);

        //Display Window
        audio.play();
        stage.show();
    }

    private void loadResources() {
        //Loading Scenes
        simScene = new ControlledScene<>("/main/resources/Simulation.fxml");
        menuScene = new ControlledScene<>("/main/resources/app.fxml");
        settingScene = new ControlledScene<>("/main/resources/Settings.fxml");


        //Loading images
            //Mute Button
        volume_on_img = ImageLoader.loadVolumeIcon("main/resources/volume_on.png");

        volume_off_img = ImageLoader.loadVolumeIcon("main/resources/volume_off.png");
            //End Mute

    }

    private void setUpListeners() {
        settingScene.getController().getVolumeProperty().addListener(
                (observableValue, number, t1) ->
                        audio.setVolume(t1.intValue() / 100d));

        settingScene.getController().isDebugEnabledProperty().addListener(
                (observableValue, aBoolean, t1) ->
                        isDebug = t1
        );
    }

    private void setUpButtons() {
        menuScene.getController().getMenuStartButton().setOnAction((e)->{
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stage.setScene(simScene.getScene());
            stage.show();
            sim = new Simulation(60, isDebug);
            initTimer();
            timer.start();
        });

        menuScene.getController().getMenuSettingsButton().setOnAction((e) -> {
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stage.setScene(settingScene.getScene());
            stage.show();
        });

        menuScene.getController().getMenuMuteButton().setOnAction(e ->{
            if(isMute) {
                audio.setMute(false);
                menuScene.getController().getMenuMuteButton().setGraphic(volume_on_img);
                isMute = false;
            } else {
                audio.setMute(true);
                menuScene.getController().getMenuMuteButton().setGraphic(volume_off_img);
                isMute = true;
            }
        });

        menuScene.getController().getMenuQuitButton().setOnAction(e->{
            window.close();
        });

        settingScene.getController().setEventHandlerForBackButton(e->{
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            stage.setScene(menuScene.getScene());
            stage.show();
        });
    }
    private void setUpStage(Stage stage) {
        stage.setTitle("TCAS Simulation");
        stage.setScene(menuScene.getScene());
        stage.setOnCloseRequest(e -> {
            if (sim != null) {
                sim.stop();
            }
            Platform.exit();
        });
    }

    private void setUpAudio() {
        audio.setCycleCount(MediaPlayer.INDEFINITE);
        audio.setAutoPlay(true);
    }
    private void initTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                sim.run2(l);
                simScene.getController().draw(sim.getAircraftAsDTOs());
            }
        };
    }

    private static class ImageLoader {
        public static ImageView loadVolumeIcon(String s){
            ImageView i = new ImageView(new Image(s));
            i.setPreserveRatio(false);
            i.setFitHeight(50);
            i.setFitWidth(50);
            return i;
        }
    }

}

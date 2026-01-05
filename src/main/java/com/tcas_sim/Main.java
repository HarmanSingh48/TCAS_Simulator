package main.java.com.tcas_sim;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import main.java.com.tcas_sim.sim.MenuController;
import main.java.com.tcas_sim.sim.Simulation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.com.tcas_sim.view.SceneController;

import java.util.Objects;

public class Main extends Application {
    private static Simulation sim;
    private MenuController mc = new MenuController();
    private SceneController sc = new SceneController();
    public static void main(String[] args) {

        //sim = new Simulation(60, false);
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println("Failed to Launch!\n" + e.getMessage());
            e.printStackTrace();
        }
        //sim.stop();
        System.out.println("Sim Stopped!");
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/resources/app.fxml"));
        loader.setController(sc);
        Parent root = loader.load();
        stage.setTitle("TCAS Simulation");
        stage.setScene(new Scene(root));
        stage.show();
        //sim.start();
    }
}
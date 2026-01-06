package main.java.com.tcas_sim;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.tcas_sim.sim.Simulation;
import main.java.com.tcas_sim.view.AppController;

public class Main extends Application {
    private static Simulation sim;
    private AppController appController;
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
        appController = new AppController(stage);
        //sim.start();
    }
}
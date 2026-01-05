package main.java.com.tcas_sim.sim;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MenuController {
    @FXML
    Button menuStartButton;
    public void pressMenuButton(ActionEvent e) {
        System.out.println("Start Pressed!");
    }
}

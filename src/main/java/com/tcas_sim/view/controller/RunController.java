package main.java.com.tcas_sim.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.java.com.tcas_sim.math.Vector3d;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RunController extends SceneController implements Initializable {
    @FXML
    private Canvas simCanvas;

    private GraphicsContext gContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gContext = simCanvas.getGraphicsContext2D();
        gContext.setFill(Color.BLACK);
        gContext.fillRect(0,0,1920,1080);
    }

    public GraphicsContext getgContext() {
        return gContext;
    }

    public void draw(List<Vector3d> positions) {
        gContext.setFill(Color.web("#00FF00"));
        gContext.setLineWidth(1);
        for(Vector3d p : positions) {
            this.gContext.fillRect(p.x(), p.y(), 5,5);
        }
    }
}

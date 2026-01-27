package main.java.com.tcas_sim.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import main.java.com.tcas_sim.util.math.GraphicsContants;
import main.java.com.tcas_sim.util.math.Vector3d;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RunController extends SceneController implements Initializable {
    @FXML
    private Canvas simCanvas;

    private Vector3d mouseAnchor;

    private Vector3d mouseFinal;
    private boolean sq = false;
    private boolean drag = false;
    private Affine origin;

    private Vector3d cameraPos = new Vector3d(0);
    private double cameraZoom = 1.0;

    private GraphicsContext gContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gContext = simCanvas.getGraphicsContext2D();


        gContext.setFill(Color.BLACK);
        gContext.fillRect(0,0,1920,1080);

        origin = gContext.getTransform();

        setUpEventListeners();
    }

    private void setUpEventListeners() {

        simCanvas.setOnMousePressed(e -> {
            mouseAnchor = new Vector3d(e.getSceneX(), e.getSceneY(), 0);
            mouseFinal = new Vector3d(mouseAnchor);
            //Left Click
            if(e.isPrimaryButtonDown()) {
                sq = true;
            }

            //Right Click
            if(e.isSecondaryButtonDown()) {
                simCanvas.setCursor(Cursor.CLOSED_HAND);
                drag = true;
                this.getGraphicContext().save();
            }
        });
        simCanvas.setOnMouseDragged(e->{
            mouseFinal = new Vector3d(e.getSceneX(),e.getSceneY(),0);

            if(drag) {
                //TODO: Apply Logic for Dragging Canvas
                //Calculate the vector from the final position to the starting position
                //Positive X value means drag was to the RIGHT, so canvas is moved LEFT and vice versa
                //Positive Y value means drag was DOWN, so canvas is moved UP and vice versa
                //Thus the negative of this vector must be applied to the camera position
                applyCameraDrag(mouseFinal.add(mouseAnchor.multiply(-1.0)));
                mouseAnchor = new Vector3d(mouseFinal);
            }

        });
        simCanvas.setOnMouseReleased(e->{
            if(sq) {
                sq = false;
            }
            if (drag) {
                //reset cursor
                simCanvas.setCursor(Cursor.DEFAULT);
                drag = false;
                //gContext.restore();
            }
        });
        simCanvas.setOnScroll(e->{
            if(e.getDeltaY() < 0) {
                this.cameraZoom = Math.max(GraphicsContants.MIN_ZOOM_LEVEL, cameraZoom - 0.1);
            } else if(e.getDeltaY() > 0) {
             this.cameraZoom = Math.min(GraphicsContants.MAX_ZOOM_LEVEL, cameraZoom + 0.1);
            }
        });


    }

    /**
     * Apply the drag input to the camera position by panning it.
      * @param dragV the vector FROM the mouse's original position TO the mouse's final position.
     */
    private void applyCameraDrag(final Vector3d dragV) {
        //apply the negative of the drag vector
        this.cameraPos = this.cameraPos.add(dragV.multiply(GraphicsContants.CAMERA_DRAG_COEF));
        this.translateCamera(dragV.multiply(GraphicsContants.CAMERA_DRAG_COEF));
    }

    private void translateCamera(final Vector3d moveVec) {
        this.getGraphicContext().translate(moveVec.x(), moveVec.y());
    }

    public GraphicsContext getGraphicContext() {
        return gContext;
    }

    private void resetCameraToOrigin() {
        gContext.setTransform(origin);
    }

    public void draw(List<Vector3d> positions) {
        //color in the background
        gContext.setFill(Color.BLACK);
        gContext.fillRect(0,0,1920,1080);
        //Set stroke for drawing plane contact
        gContext.setFill(Color.web("#00FF00", 0.5));
        gContext.setLineWidth(4);

        //Draw each plane
        for(Vector3d p : positions) {
            this.gContext.fillRect(p.x(), p.y(), 5,5);
        }

        //Draw the selection box
        if(sq){
            gContext.setFill(Color.web("#FF8000", 0.15));
            gContext.fillRect(mouseFinal.x() - mouseAnchor.x() > 0 ? mouseAnchor.x() : mouseFinal.x(),
                    mouseFinal.y() - mouseAnchor.y() > 0 ? mouseAnchor.y() : mouseFinal.y(),
                    Math.abs(mouseFinal.x() - mouseAnchor.x()), Math.abs(mouseFinal.y() - mouseAnchor.y()));
        }

    }
}

package main.java.com.tcas_sim.view.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import main.java.com.tcas_sim.util.math.GraphicsContants;
import main.java.com.tcas_sim.util.math.Vector3d;
import main.java.com.tcas_sim.util.transfer.AircraftDataTransfer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class RunController extends SceneController implements Initializable {
    /*
    Java FX Fields
     */
    @FXML
    private Canvas simCanvas;
    @FXML
    private AnchorPane infoPane;
    @FXML
    private AnchorPane TargetIDAnchorPane;
    @FXML
    private Text targetIDText;
    @FXML
    private VBox targetInfoBox;

    private Vector3d mouseAnchor;

    private Vector3d mouseFinal;
    private boolean selection = false;
    private boolean drag = false;
    private Affine guiAffine;
    private Affine viewAffine;

    private Vector3d cameraPos = new Vector3d(0);
    private double cameraZoom = 1.0;

    private GraphicsContext gContext;

    private InputHandler inputHandler;

    private HashMap<String, Entity> entities;
    private HashMap<String, Property<?>> properties;
    private List<Entity> selectedPlanes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gContext = simCanvas.getGraphicsContext2D();


        gContext.setFill(Color.BLACK);
        gContext.fillRect(0,0,GraphicsContants.DEFAULT_CANVAS_WIDTH,GraphicsContants.DEFAULT_CANVAS_HEIGHT);

        guiAffine = gContext.getTransform();
        viewAffine = gContext.getTransform();

        inputHandler =  new InputHandler();
        entities = new HashMap<>();
        properties = new HashMap<>();
        selectedPlanes = new ArrayList<>();
        setUpEventListeners();
    }

    private void setUpEventListeners() {
        inputHandler.setUpMouseControls();
    }


    /**
     * Apply the drag input to the camera position by panning it.
      * @param dragV the vector FROM the mouse's original position TO the mouse's final position.
     */
    private void applyCameraDrag(final Vector3d dragV) {
        //apply the negative of the drag vector
        this.cameraPos = this.cameraPos.add(dragV.multiply(GraphicsContants.CAMERA_DRAG_COEFF));
        this.translateCamera(dragV.multiply(GraphicsContants.CAMERA_DRAG_COEFF));
    }

    /**
     * Move the camera by the specified vector
     * @param moveVec the vector to move along
     */
    private void translateCamera(final Vector3d moveVec) {
        this.getGraphicContext().translate(moveVec.x(), moveVec.y());
    }

    /**
     * Get the scene's graphics context.
     * @return the graphics context of the Scene containing the simulation canvas.
     */
    public GraphicsContext getGraphicContext() {
        return gContext;
    }

    /**
     * Return the camera to its original affine.
     * @param gc the graphics context to translate.
     */
    private void resetCameraToOrigin(final GraphicsContext gc) {
        gc.setTransform(guiAffine);
    }

    /**
     * Return the camera to its translated affine.
     * @param gc the graphics context to translate.
     */
    private void resetCameraToView(final GraphicsContext gc) {
        gc.setTransform(viewAffine);
    }

    private void updateEntities(List<AircraftDataTransfer> incomingData) {
        for(AircraftDataTransfer d : incomingData) {
            //Check if map contains the entity already or if it is a new one
            if(entities.containsKey(d.callsign())) {
                entities.get(d.callsign()).updateData(d);
            } else {
                entities.put(d.callsign(), new Entity(d));
            }
        }
    }

    /**
     * Draw the canvas using the specified entity positions.
     * @param positions the positions of the entities.
     */
    public void draw(List<AircraftDataTransfer> positions) {
        viewAffine = gContext.getTransform();
        updateEntities(positions);
        /*
        Layer 1: Background
         */
        //Draw Background
        resetCameraToOrigin(gContext);
        renderBackground(gContext);
        resetCameraToView(gContext);

        /*
        Layer 2: Dynamic UI Layer
         */
        //Draw Radar Contacts
        drawEntitiesAndAttributes(entities.values().stream().toList(), gContext);


        /*
        Layer 3: Static UI Layer
         */
        //Draw GUI Elements
        resetCameraToOrigin(gContext);
        renderStaticGUI(gContext);
        resetCameraToView(gContext);

    }

    /**
     * Draw the specified entities and their respective attributes on the canvas.
     * @param entities the list of the entities to draw.
     * @param gc the graphics context to render on.
     */
    private void drawEntitiesAndAttributes(List<Entity> entities, final GraphicsContext gc) {
        //Set stroke for drawing plane contact
        gc.setFill(Color.web("#00FF00", 0.5));
        gc.setLineWidth(4);


        //Draw each plane
        for(Entity e : entities) {
            drawEntityPosition(e, gc);
            drawEntityAttributes(e, gc);
        }
    }

    /**
     * Draw a specific entity on the canvas.
     * @param theEntity the entity object to draw.
     * @param gc the graphics context to render on.
     */

    private void drawEntityPosition(Entity theEntity, GraphicsContext gc) {
        gc.fillRect(theEntity.getPos().x(), theEntity.getPos().y(), GraphicsContants.ENTITY_DRAW_SIZE,GraphicsContants.ENTITY_DRAW_SIZE);
    }

    /**
     * Draw the specified entity's attributes to the canvas.
     * @param theEntity the entity object whose attributes need to be drawn.
     * @param gc the graphics context to render on.
     */

    private void drawEntityAttributes(Entity theEntity, GraphicsContext gc) {
        if(theEntity.isSelected()) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(1);
            gc.strokeRect(theEntity.getPos().x() - GraphicsContants.ENTITY_ATTRIBUTE_OFFSET,
                    theEntity.getPos().y() - GraphicsContants.ENTITY_ATTRIBUTE_OFFSET,
                    GraphicsContants.ENTITY_SELECTED_OUTLINE_SIZE,
                    GraphicsContants.ENTITY_SELECTED_OUTLINE_SIZE);
        }
        if(theEntity.aircraftData.isTA) {

        }
        if(theEntity.aircraftData.isRA) {

        }
    }

    /**
     * Render the UI to the graphics context.
     * @param gc the graphics context to render on.
     */
    private void renderStaticGUI(final GraphicsContext gc) {
        //Draw the selection box
        if(selection){
            gc.setFill(Color.web("#FF8000", 0.15));
            gc.fillRect(Math.min(mouseFinal.x(), mouseAnchor.x()),
                    Math.min(mouseFinal.y(), mouseAnchor.y()),
                    Math.abs(mouseFinal.x() - mouseAnchor.x()), Math.abs(mouseFinal.y() - mouseAnchor.y()));
        }
    }

    /**
     * Render the background to the canvas.
     * @param gc the graphics context to render on.
     */
    private void renderBackground(final GraphicsContext gc) {
        //color in the background
        gc.setFill(Color.rgb(0,0,0,
                drag ? 1.0 : 0.35));
        gc.fillRect(0,0,GraphicsContants.DEFAULT_CANVAS_WIDTH,GraphicsContants.DEFAULT_CANVAS_HEIGHT);
    }

    private boolean checkWithinInRectangle(Vector3d lb, Vector3d ub, Vector3d pos) {
        return pos.x() >= lb.x() && pos.y() >= lb.y() && pos.x() <= ub.x() && pos.y() <= ub.y();
    }

    private void selectAircraft(List<Entity> positions) {
        //Lower-left corner of the rectangle
        Vector3d lbound = new Vector3d(Math.min(
                mouseAnchor.x(),mouseFinal.x()),
                Math.min(mouseAnchor.y(),mouseFinal.y()),
                0
        );

        lbound = lbound.add(//add the camera's drag translation
                        viewAffine.getTx() * -1.0,
                        viewAffine.getTy(),
                        0
        );


        //Upper-right corner of the rectangle
        Vector3d ubound = new Vector3d(
                Math.max(mouseAnchor.x(),mouseFinal.x()),
                Math.max(mouseAnchor.y(),mouseFinal.y()),
                0
        );


        ubound = ubound.add(//add the camera's drag translation
                        viewAffine.getTx() * -1.0,
                        viewAffine.getTy(),
                        0
        );

        selectedPlanes.clear();
        targetInfoBox.getChildren().clear();
        for(Entity p : positions) {
            p.setSelected(checkWithinInRectangle(lbound, ubound, p.getPos()));
            if(p.isSelected()) {
                selectedPlanes.add(p);
            }
        }
    }

    private void addSelectedCraftToInfoVBox() {
        //Add the title Pane
        targetIDText.setText(
                selectedPlanes.isEmpty() ? "No target selected" : "Target Selected"
        );
        targetInfoBox.getChildren().add(TargetIDAnchorPane);

        try {
            //Add all selected planes
            for (Entity e : selectedPlanes) {
                AnchorPane a = AnchorPaneLoader.createNewPaneWithEntity(e);
                setUpBindingsForPane(a, e);
                targetInfoBox.getChildren().add(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpBindingsForPane(AnchorPane a, Entity e) {
        //Grab the title text node
        Text textNode = (Text) a.lookup("#titleText");
        //Display the aircraft's callsign
        textNode.setText(e.getData().callsign());

        //Get the info text node
        textNode = (Text) a.lookup("#aircraftInfoText");
        //Bin
        textNode.textProperty().bind(formatString(e));
    }

    private static javafx.beans.binding.StringExpression formatString(Entity e) {
        return Bindings.concat("Position: ", e.posXProperty().asString("%.2f"),", ", e.posYProperty().asString("%.2f") ,"\nAltitude: ",e.altitudeProperty().asString("%.2f"), " ft. Bearing: ", e.bearingProperty().asString("%.2f"), " (TRUE)");
    }
    private class InputHandler {
        InputHandler() {}

        protected void setUpMouseControls() {
            simCanvas.setOnMousePressed(e -> {
                mouseAnchor = new Vector3d(e.getSceneX(), e.getSceneY(), 0);
                mouseFinal = new Vector3d(mouseAnchor);
                //Left Click
                if(e.isPrimaryButtonDown()) {
                    selection = true;
                }

                //Right Click
                if(e.isSecondaryButtonDown()) {
                    simCanvas.setCursor(Cursor.CLOSED_HAND);
                    drag = true;
                    getGraphicContext().save();
                }
            });
            simCanvas.setOnMouseDragged(e->{
                mouseFinal = new Vector3d(e.getSceneX(),e.getSceneY(),0);

                if(drag) {
                    //Calculate the vector from the final position to the starting position
                    //Positive X value means drag was to the RIGHT, so canvas is moved LEFT and vice versa
                    //Positive Y value means drag was DOWN, so canvas is moved UP and vice versa
                    //Thus the negative of this vector must be applied to the camera position
                    applyCameraDrag(mouseFinal.add(mouseAnchor.multiply(-1.0)));
                    mouseAnchor = new Vector3d(mouseFinal);
                }

            });
            simCanvas.setOnMouseReleased(e->{
                if(!e.isPrimaryButtonDown() && selection) {
                    selection = false;
                    selectAircraft(entities.values().stream().toList());
                    addSelectedCraftToInfoVBox();
                }
                if (!e.isSecondaryButtonDown() && drag) {
                    //reset cursor
                    simCanvas.setCursor(Cursor.DEFAULT);
                    drag = false;
                    //gContext.restore();
                }
            });
            simCanvas.setOnScroll(e->{
                if(e.getDeltaY() < 0) {
                    cameraZoom = Math.max(GraphicsContants.MIN_ZOOM_LEVEL, cameraZoom - 0.1);
                } else if(e.getDeltaY() > 0) {
                    cameraZoom = Math.min(GraphicsContants.MAX_ZOOM_LEVEL, cameraZoom + 0.1);
                }
            });

        }
    }
    private class Entity {
        private boolean selected;
        private AircraftData aircraftData;
        private final SimpleDoubleProperty bearingProperty;
        private final SimpleDoubleProperty altitudeProperty;
        private final SimpleDoubleProperty posXProperty;
        private final SimpleDoubleProperty posYProperty;

        public SimpleDoubleProperty altitudeProperty() {
            return altitudeProperty;
        }
        public SimpleDoubleProperty posXProperty() {
            return posXProperty;
        }
        public SimpleDoubleProperty posYProperty() {
            return posYProperty;
        }
        public SimpleDoubleProperty bearingProperty() {
            return bearingProperty;
        }

        Entity(AircraftDataTransfer data) {
            bearingProperty = new SimpleDoubleProperty(data.bearing());
            posXProperty= new SimpleDoubleProperty(data.pos().x());
            posYProperty = new SimpleDoubleProperty(data.pos().y());
            altitudeProperty = new SimpleDoubleProperty(data.pos().z());
            selected = false;
            updateData(data);
        }
        public boolean isSelected() {
            return selected;
        }
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
        public Vector3d getPos() {
            return this.aircraftData.pos();
        }
        public AircraftData getData() {return this.aircraftData;}

        public void updateData(AircraftDataTransfer data) {
            this.aircraftData = new AircraftData(data.callsign(), data.pos(), data.bearing(), data.isTA(), data.isRA());
            updateProperties();
        }
        private void updateProperties() {
            bearingProperty.set(getData().bearing());
            posXProperty.set(getPos().x());
            posYProperty.set(getPos().y());
            altitudeProperty.set(getPos().z());
        }
        private record AircraftData(String callsign,Vector3d pos,double bearing, boolean isTA, boolean isRA) {
        }
    }
    private static class AnchorPaneLoader {

        private static AnchorPane createNewPane() throws IOException {
            FXMLLoader infoPaneLoader = new FXMLLoader(AnchorPaneLoader.class.getResource("/main/resources/TargetInfoTextPane.fxml"));
            return infoPaneLoader.load();
        }
        private static AnchorPane createNewPaneWithText(String s) throws IOException {
            FXMLLoader infoPaneLoader = new FXMLLoader(AnchorPaneLoader.class.getResource("/main/resources/TargetInfoTextPane.fxml"));
            AnchorPane p = infoPaneLoader.load();
            ((Text)p.getChildren().getFirst()).setText(s);
            return p;
        }

        private static AnchorPane createNewPaneWithEntity(Entity e) throws IOException {
            //Setup loader with file
            FXMLLoader infoPaneLoader = new FXMLLoader(AnchorPaneLoader.class.getResource("/main/resources/TargetInfoTextPane.fxml"));
            //Load the pane from fxml loader
            AnchorPane p = infoPaneLoader.load();
            //Set the anchor's ID to the callsign of the aircraft
            //p.setId(e.getData().callsign());
            //Grab the title field
            Text textNode = (Text) p.lookup("#titleText");

            //Check if there was a proper return from the search
            if(textNode != null) {
                //Set the A/C's callsign to the title text
                textNode.setText(e.getData().callsign());
            }
            return p;
        }
    }
}

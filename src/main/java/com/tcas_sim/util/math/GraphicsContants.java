package main.java.com.tcas_sim.util.math;

public final class GraphicsContants {
    public static final int DEFAULT_WINDOW_WIDTH = 1920;
    public static final int DEFAULT_WINDOW_HEIGHT = 1080;


    public static final int DEFAULT_CANVAS_WIDTH = 1600;
    public static final int DEFAULT_CANVAS_HEIGHT = 1080;

    public static final double MIN_ZOOM_LEVEL = 0.2;
    public static final double MAX_ZOOM_LEVEL = 3.0;

    public static final double CAMERA_DRAG_COEFF = 0.945;

    public static final double ENTITY_DRAW_SIZE = 5.0;

    public static final double ENTITY_ATTRIBUTE_OFFSET =3;
    public static final double ENTITY_SELECTED_OUTLINE_SIZE = 11.0;

    private GraphicsContants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }
}

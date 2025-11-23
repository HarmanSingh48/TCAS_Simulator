    public class Aircraft {
    private Vector3d position;
    private double velocity;
    private final Transponder transponder;

    //Constructor
    public Aircraft(final Vector3d initialPos, final double initialV, final Transponder transponder) {
        this.position = initialPos;
        this.velocity = initialV;
        this.transponder = transponder;
    }
    //Getters and Setters
    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Transponder getTransponder() {
        return transponder;
    }
}

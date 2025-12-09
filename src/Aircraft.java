    public class Aircraft {
    /**
     * The position of the aircraft in cartesian coordinates, with (x,y) being the lateral positions and the z-value
     * being the altitude. Values are represented in feet. Therefore, check the units before any calculations.
     */
    private Vector3d position;
    /**
     * Stores the value for the aircraft's speed as a 3d vector of the velocity's x-, y-, and z- components
     * in feet per second.
     */
    private Vector3d velocity;
    /**
     * The aircraft's transponder object
     */
    private final Transponder transponder;
    /**
     * The aircraft's registration
     */
    private final String registration;

    //Constructor
    public Aircraft(final String registration, final Vector3d initialPos, final Vector3d initialV, final Transponder transponder) {
        this.position = initialPos;
        this.velocity = initialV;
        this.transponder = transponder;
        this.registration = registration;
    }
    public Aircraft(final String registration, final Vector3d initialPos, final Vector3d initialV, final boolean isTCASEquipped, final Transponder_Type type) {
        this.position = initialPos;
        this.velocity = initialV;
        this.transponder = type == Transponder_Type.MODE_S ?
                new Mode_S_Transponder(1, registration, "1200", this, isTCASEquipped) :
                new Mode_C_Transponder(registration, "1200", this, isTCASEquipped);
        this.registration = registration;
    }
    //Getters and Setters
    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity;
    }

    public String getRegistration() { return registration; }

    public Transponder getTransponder() { return transponder; }

    public void update(final double deltaT) {
        updatePosition(deltaT);
        this.transponder.update(deltaT);
    }

    private  void updatePosition(final double deltaT) {
        this.setPosition(this.getPosition().add(
                this.getVelocity().multiply(deltaT * 3.6 * 1000000000000L)
        ));
    }

    @Override
    public String toString() {
        return registration + " reporting position at " + this.position.toString() + ". Speed: " + this.velocity.toString() +"\n";
    }
    }

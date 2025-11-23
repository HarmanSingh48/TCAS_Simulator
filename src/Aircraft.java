    public class Aircraft {
    private Vector3d position;
    private Vector3d velocity;
    private final Transponder transponder;
    private final String registration;

    //Constructor
    public Aircraft(final String registration, final Vector3d initialPos, final Vector3d initialV, final Transponder transponder) {
        this.position = initialPos;
        this.velocity = initialV;
        this.transponder = transponder;
        this.registration = registration;
    }
    public Aircraft(final String registration, final Vector3d initialPos, final Vector3d initialV, final boolean isTCASEquipped, final boolean isModeS) {
        this.position = initialPos;
        this.velocity = initialV;
        this.transponder = isModeS ?
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

    @Override
    public String toString() {
        return registration + " reporting position at " + this.position.toString() + ". Speed: " + this.velocity.toString() +"\n";
    }
    }

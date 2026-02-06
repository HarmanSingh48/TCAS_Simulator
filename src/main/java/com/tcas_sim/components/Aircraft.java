package main.java.com.tcas_sim.components;

import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.*;
import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.systems.tcas.NullTCA_System;
import main.java.com.tcas_sim.components.systems.tcas.TCAS;
import main.java.com.tcas_sim.components.systems.tcas.TCA_System;
import main.java.com.tcas_sim.components.systems.transponder.*;
import main.java.com.tcas_sim.components.systems.transponder.Transponder;
import main.java.com.tcas_sim.util.math.Vector3d;

public class Aircraft implements MessageVisitor {
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
     * The Aircraft's bearing relative to the positive y-axis (aka North)
     */
    private double bearing;
    /**
     * The aircraft's transponder object
     */
    private final Transponder transponder;
    /**
     * The aircraft's registration
     */
    private final String registration;
    /**
     * The reference for the TCAS computer
     */
    private final TCAS TCAS_Computer;

    //Constructor
    public Aircraft(final String registration, final Vector3d initialPos, final Vector3d initialV, final Transponder transponder) {
        this.position = initialPos;
        this.velocity = initialV;
        this.transponder = transponder;
        this.registration = registration;
        this.TCAS_Computer = new TCA_System();
        this.bearing = updateBearing(initialPos, initialV);
    }
    public Aircraft(final String registration, final Vector3d initialPos, final Vector3d initialV, final boolean isTCASEquipped, final Transponder_Type type) {
        this.position = initialPos;
        this.velocity = initialV;
        this.TCAS_Computer = isTCASEquipped ? new TCA_System() : new NullTCA_System();
        this.transponder = type == Transponder_Type.MODE_S ?
                new Mode_S_Transponder(1, registration, "1200", this, this.TCAS_Computer) :
                new Mode_C_Transponder(registration, "1200", this);
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
    public void setBearing(double newBearing) {this.bearing = newBearing;}
    public double getBearing() {return this.bearing;}

    public void update(final double deltaT) {
        updatePositionAndBearing(deltaT);
        this.transponder.update(deltaT);
    }
    private static double updateBearing(Vector3d initialPos, Vector3d velocity) {
        //Extrapolate the position using the velocity to some point in the future. The step in time can be any number, big or small (within reason),
        //since we need 2 points of position separated in time to get a bearing, regardless of how far they are from each other.
        Vector3d extrapolated = initialPos.add(velocity);
        return calculateBearing(initialPos.x(), initialPos.y(), extrapolated.x(), extrapolated.y());
    }
    private static double calculateBearing(double x1, double y1, double x2, double y2) {
        return Math.toDegrees(Math.atan2(x2 - x1, y2 - y1)) + 360;
    }

    public void receive(Transmission t) {
        t.accept(this);
    }

    private  void updatePositionAndBearing(final double deltaT) {
        this.setPosition(this.getPosition().add(
                this.getVelocity().multiply(deltaT * 1E-9)
        ));
        this.bearing = updateBearing(this.getPosition(), this.getVelocity());
    }

    @Override
    public String toString() {
        return registration + " reporting position at " + this.position.toString() + ". Speed: " + this.velocity.toString() +"\n";
    }

    @Override
    public void visit(Mode_S_Ping sPing) {
        transponder.processPing(sPing);
    }

    @Override
    public void visit(Mode_C_Ping cPing) {
        transponder.processPing(cPing);
    }

    @Override
    public void visit(Mode_S_Result sRes) {
        transponder.processReply(sRes);
    }

    @Override
    public void visit(Mode_C_Result cRes) {
        transponder.processReply(cRes);
    }
}

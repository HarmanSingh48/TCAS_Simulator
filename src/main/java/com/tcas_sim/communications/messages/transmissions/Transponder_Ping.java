package main.java.com.tcas_sim.communications.messages.transmissions;


import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.math.*;

public abstract class Transponder_Ping extends Transmission{
    public Transponder_Ping(final Vector3d aircraftPos, final double range, final Aircraft sender) {
        super(new Sphere(aircraftPos, range), sender);
    }
}


package main.java.com.tcas_sim.communications.messages.transmissions;


import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.util.math.Sphere;
import main.java.com.tcas_sim.util.math.Vector3d;

/**
 * Extension of the Transmission class that represents outgoing pings to query other aircraft.
 */
public abstract class Transponder_Ping extends Transmission{
    /**
     * Constructor.
     * @param aircraftPos Sender's position.
     * @param range Range of the transmission in Nautical Miles.
     * @param sender Reference to sender.
     */
    public Transponder_Ping(final Vector3d aircraftPos, final double range, final Aircraft sender) {
        super(new Sphere(aircraftPos, range), sender);
    }
}


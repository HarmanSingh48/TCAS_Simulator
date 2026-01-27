package main.java.com.tcas_sim.communications.messages.results;

import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.util.math.Sphere;
import main.java.com.tcas_sim.util.math.Vector3d;

/**
 * Extension of Transmission class that represents a reply to a ping
 */
public abstract class Reply extends Transmission {
    /**
     * Position of the replying Aircraft
     */
    final Vector3d position;
    /**
     * The addressee of this reply
     */
    final Aircraft target;

    /**
     * Constructor
     * @param position Sender's position
     * @param sender the Sender
     * @param receiver the target aircraft
     * @param range
     */
    public Reply(final Vector3d position, final Aircraft sender, final Aircraft receiver, final double range) {
        super(new Sphere(sender.getPosition(), range), sender);
        this.position = position;
        this.target = receiver;
    }

    /**
     * Get the target of the reply
     * @return reference to target
     */
    public Aircraft getTarget() {
        return target;
    }
}


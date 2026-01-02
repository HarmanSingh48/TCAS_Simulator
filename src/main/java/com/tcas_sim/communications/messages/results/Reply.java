package main.java.com.tcas_sim.communications.messages.results;

import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.math.*;

public abstract class Reply extends Transmission {
    final Vector3d position;
    final Aircraft target;

    public Reply(final Vector3d position, final Aircraft sender, final Aircraft receiver, final double range) {
        super(new Sphere(sender.getPosition(), range), sender);
        this.position = position;
        this.target = receiver;
    }

    public Aircraft getTarget() {
        return target;
    }
}


package main.java.com.tcas_sim.communications.messages.transmissions;

import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.math.*;

public abstract class Transmission {
    private final Sphere origin;
    private final Aircraft sender;
    public Transmission(final Sphere origin, final Aircraft sender) {
        this.origin = origin;
        this.sender = sender;
    }

    public double getPingRange() {return this.origin.radius();}
    public Vector3d getPingCenter() {return this.origin.center();}

    public boolean checkWithinBounds(Vector3d otherPos) {return this.origin.checkWithinBoundsInclusive(otherPos);}

    public Aircraft getSender() {return this.sender;}
    public abstract void accept(MessageVisitor visitor);
}

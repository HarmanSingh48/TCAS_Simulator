package main.java.com.tcas_sim.communications.messages.transmissions;

import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.util.math.Sphere;
import main.java.com.tcas_sim.util.math.Vector3d;

/**
 * Class representing a sendable message in the simulation-space.
 */
public abstract class Transmission {
    /**
     * The origin point (or center) of the transmission, i.e. the position of the transmitter at the time of the emission.
     * Used for calculating range/sphere of effect for the transmission.
     */
    private final Sphere origin;
    /**
     * Reference to the sender. Might remove? Redundant if origin is already a field
     */
    private final Aircraft sender;

    /**
     * Constructor.
     * @param origin The center of the transmission.
     * @param sender The sending Aircraft.
     */
    public Transmission(final Sphere origin, final Aircraft sender) {
        this.origin = origin;
        this.sender = sender;
    }

    /**
     * Get the effective range of the transmission.
     * @return The range of the transmission.
     */
    public double getRange() {return this.origin.radius();}

    /**
     * Get the center of the Transmission.
     * @return A 3D Vector containing the center's position.
     */
    public Vector3d getCenter() {return this.origin.center();}

    /**
     * Check if the other position is within the sphere of effect of this Transmission
     * @param otherPos the other position to check
     * @return True if within range.
     */
    public boolean checkWithinBounds(Vector3d otherPos) {return this.origin.checkWithinBoundsInclusive(otherPos);}

    /**
     * Returns a reference to the sending Aircraft
     * @return The sender Aircraft
     */
    public Aircraft getSender() {return this.sender;}

    /**
     * Accept the specified Message Visitor.
     * @param visitor the MessageVisitor asking to visit.
     */
    public abstract void accept(MessageVisitor visitor);
}

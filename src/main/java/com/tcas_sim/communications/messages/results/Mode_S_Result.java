package main.java.com.tcas_sim.communications.messages.results;

import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.util.math.Vector3d;

/**
 * A Mode-S Reply.
 */
public class Mode_S_Result extends Reply {
    /**
     * The ID of the sender
     */
    private final long ICAO_IDENTITY;
    /**
     * Callsign of the sender
     */
    private final String callsign;

    /**
     * Constructs a Mode-S reply
     * @param sender the origin of the message (Issuer)
     * @param receiver the target of the message
     * @param IDENTIFIER the unique identifier of the issuing A/C
     * @param callsign the A/C's callsign
     */
    public Mode_S_Result(final Aircraft sender, final Aircraft receiver, final long IDENTIFIER, final String callsign) {
        super(sender.getPosition(), sender, receiver, 15);
        this.ICAO_IDENTITY = IDENTIFIER;
        this.callsign = callsign;
    }

    /**
     * Get the unique Identifier in the message
     * @return long containing ID
     */
    public long get_Identity() {
        return ICAO_IDENTITY;
    }

    /**
     * Get the sender's callsign
     * @return String containing callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * Get the sender's position.
     * @return 3D vector of the position.
     */
    public Vector3d getPosition() {
        return position;
    }

    /**
     * Accept the MessageVisitor.
     * @param visitor the MessageVisitor asking to visit.
     */
    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

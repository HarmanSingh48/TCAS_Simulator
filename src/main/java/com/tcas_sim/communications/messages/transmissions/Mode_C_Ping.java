package main.java.com.tcas_sim.communications.messages.transmissions;



import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;

/**
 * Extension of Transponder Pings that represents Mode-C pings from Mode-C Transponders.
 */

public class Mode_C_Ping extends Transponder_Ping {
    /**
     *
     * @param sender The sending Aircraft.
     * @param range Range of the transmission in Nautical Miles.
     */
    public Mode_C_Ping(final Aircraft sender, final double range) {
        super(sender.getPosition(), range, sender);
    }
    /**
     * Accept a message visitor.
     * @param visitor the MessageVisitor asking to visit.
     */
    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

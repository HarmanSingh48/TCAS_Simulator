package main.java.com.tcas_sim.communications.messages.transmissions;


import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.systems.transponder.Mode_S_Transponder;
import main.java.com.tcas_sim.components.systems.transponder.Transponder;

/**
 * Extension of Transponder Pings that represents Mode-S pings from Mode-S Transponders.
 */
public class Mode_S_Ping extends Transponder_Ping {
    /**
     * The ping's target
     */
    private final Mode_S_Transponder target;

    /**
     * Constructor.
     * @param target The target Aircraft's ID.
     * @param sender The Sender Aircraft's ID.
     */
    public Mode_S_Ping(final Mode_S_Transponder target, final Mode_S_Transponder sender) {
        super(sender.getOWNER().getPosition(), 15, sender.getOWNER());
        this.target = target;
    }

    /**
     * Get the message's target.
     * @return The target's ID
     */
    public long getTargetID(){return this.target.getIdentity();}

    /**
     * Accept a message visitor.
     * @param visitor the MessageVisitor asking to visit.
     */
    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

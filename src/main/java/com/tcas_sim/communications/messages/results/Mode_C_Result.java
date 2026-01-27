package main.java.com.tcas_sim.communications.messages.results;


import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.util.math.Vector3d;

/**
 * A Mode C reply.
 */
public class Mode_C_Result extends Reply {
    /**
     * Constructor
     * @param position
     * @param sender
     * @param receiver
     */
    public Mode_C_Result(final Vector3d position, final Aircraft sender, final Aircraft receiver) {
        super(position, sender, receiver, 40);
    }

    /**
     * Accept a message visitor
     * @param visitor the MessageVisitor asking to visit.
     */
    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

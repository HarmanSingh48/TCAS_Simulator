package main.java.com.tcas_sim.communications.messages.results;


import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.math.Vector3d;

public class Mode_C_Result extends Interrogation_Result {
    public Mode_C_Result(final Vector3d position, final Aircraft sender, final Aircraft receiver) {
        super(position, sender, receiver, 40);
    }

    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

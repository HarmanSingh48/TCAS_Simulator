package main.java.com.tcas_sim.communications.messages.transmissions;

import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.util.math.Sphere;

public class Squitter extends Transmission{
    public Squitter(final Aircraft sender, final double range) {
        super(new Sphere(sender.getPosition(), range),sender);
    }
    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

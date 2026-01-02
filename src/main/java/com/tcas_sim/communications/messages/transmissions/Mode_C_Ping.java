package main.java.com.tcas_sim.communications.messages.transmissions;



import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Mode_C_Ping extends Transponder_Ping {
    public Mode_C_Ping(final Aircraft sender, final double range) {
        super(sender.getPosition(), range, sender);
    }

    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

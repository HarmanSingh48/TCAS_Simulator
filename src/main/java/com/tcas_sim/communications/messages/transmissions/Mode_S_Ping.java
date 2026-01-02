package main.java.com.tcas_sim.communications.messages.transmissions;


import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.systems.transponder.Mode_S_Transponder;
import main.java.com.tcas_sim.components.systems.transponder.Transponder;

public class Mode_S_Ping extends Transponder_Ping {
    private final Mode_S_Transponder target;

    public Mode_S_Ping(final Mode_S_Transponder target, final Mode_S_Transponder sender) {
        super(sender.getOWNER().getPosition(), 15, sender.getOWNER());
        this.target = target;
    }
    public long getTargetID(){return this.target.getIdentity();}


    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

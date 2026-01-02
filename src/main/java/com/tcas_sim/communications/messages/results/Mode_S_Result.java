package main.java.com.tcas_sim.communications.messages.results;

import main.java.com.tcas_sim.communications.messagevisitor.MessageVisitor;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.math.Vector3d;

public class Mode_S_Result extends Interrogation_Result {
    private final long ICAO_IDENTITY;
    private final String callsign;
    private final String Squawk_Code;
    private final boolean is_Issuing_RA;

    public Mode_S_Result(final Aircraft sender, final Aircraft reciever, final long IDENTIFIER, final String callsign, final String squawk_Code, final boolean is_Issuing_RA) {
        super(sender.getPosition(), sender, reciever, 15);
        this.ICAO_IDENTITY = IDENTIFIER;
        this.callsign = callsign;
        Squawk_Code = squawk_Code;
        this.is_Issuing_RA = is_Issuing_RA;
    }

    public long get_Identity() {
        return ICAO_IDENTITY;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getSquawk_Code() {
        return Squawk_Code;
    }

    public boolean is_Issuing_RA() {
        return is_Issuing_RA;
    }

    public Vector3d getPosition() {
        return position;
    }

    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}

package main.java.com.tcas_sim.components.systems.transponder;

import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_C_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.components.systems.tcas.TCAS;

public class Mode_S_Transponder extends Transponder {
    private final long IDENTITY;
    private final TCAS TCAS_Computer;

    private boolean TA_Active = false;
    private boolean RA_Active = false;
    public Mode_S_Transponder(final int ICAO_IDENTIFIER, final String callsign, final String squawkCode, final Aircraft Owner, final TCAS tcas) {
        super(callsign, squawkCode, Owner);
        this.IDENTITY = ICAO_IDENTIFIER;
        this.TCAS_Computer = tcas;
    }
    private Mode_S_Result generateReply(final Aircraft sender) {
        return new Mode_S_Result(this.getOWNER(), sender, this.IDENTITY, this.getCallsign(), this.getSquawkCode(), this.is_Issuing_RA());
    }

    public long getIdentity(){return this.IDENTITY;}

    public boolean is_Issuing_TA() {
        return this.TA_Active;
    }
    public boolean is_Issuing_RA() {
        return this.RA_Active;
    }


    @Override
    public void processReply(Mode_C_Result reply) {
        //1.Update TCAS Computer
        this.TCAS_Computer.process(reply);
        //2.Send a signal back to be quiet
    }

    @Override
    public void processReply(Mode_S_Result reply) {
        //1.Update TCAS Computer
        this.TCAS_Computer.process(reply);
        //2.Send a signal back to be quiet
    }

    @Override
    public void processPing(Mode_C_Ping ping) {
        //Ignore Ping
    }

    @Override
    public void processPing(Mode_S_Ping ping) {
        if(ping.getTargetID() == this.getIdentity()) {
            System.out.println("TCAS Computer needs to be here!");
        }
    }

    @Override
    public void update(double deltaT) {
        this.timeSinceLastPing += (long) deltaT;
        if(timeSinceLastPing >= 1.0E9) {
            //TODO:ISSUE MESSAGE
            timeSinceLastPing -= (long) 1.0E9;
        } else {
            timeSinceLastPing += (long) deltaT;
        }
    }


}

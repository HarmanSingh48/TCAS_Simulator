package main.java.com.tcas_sim.components.systems.transponder;

import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_C_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.components.Aircraft;

public class Mode_C_Transponder extends Transponder {

    public Mode_C_Transponder(final String callsign, final String squawkCode, final Aircraft Owner) {
        super(callsign, squawkCode, Owner);
    }


    @Override
    public void processReply(Mode_C_Result reply) {

    }

    @Override
    public void processReply(Mode_S_Result reply) {

    }

    @Override
    public void processPing(Mode_C_Ping ping) {

    }

    @Override
    public void processPing(Mode_S_Ping ping) {
        this.getOutBox().add(new Mode_C_Result(this.getOWNER().getPosition(), this.getOWNER(), ping.getSender()));
    }

    @Override
    public void update(double deltaT) {

    }


}


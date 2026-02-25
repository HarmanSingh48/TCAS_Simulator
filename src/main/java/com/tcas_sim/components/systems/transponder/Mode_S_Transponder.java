package main.java.com.tcas_sim.components.systems.transponder;

import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_C_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Squitter;
import main.java.com.tcas_sim.components.Aircraft;
import main.java.com.tcas_sim.components.systems.tcas.TCAS;
import main.java.com.tcas_sim.components.systems.tcas.TCA_System;
import main.java.com.tcas_sim.util.math.MathConstants;

import java.util.LinkedList;

/**
 * Extension of the Transponder class that represents a Mode-S Transponder
 */
public class Mode_S_Transponder extends Transponder {
    /**
     * The Aircraft's unique Identity
     */
    private final long IDENTITY;
    /**
     * The TCAS Computer
     */
    private final TCAS TCAS_Computer;

    /**
     *
     */
    private LinkedList<Integer> queueOfPingRanges = new LinkedList<>();



    /**
     * Constructor.
     * @param ICAO_IDENTIFIER The Aircraft's unique Identity
     * @param callsign The Aircraft's call-sign/Flight ID
     * @param squawkCode The Default Squawk Code dialed at instantiation
     * @param Owner Owner aircraft of the transponder
     * @param tcas the TCAS computer. Use NullTCA_System for non-TCAS equipped aircraft.
     */
    public Mode_S_Transponder(final int ICAO_IDENTIFIER, final String callsign, final String squawkCode, final Aircraft Owner, final TCAS tcas) {
        super(callsign, squawkCode, Owner);
        this.IDENTITY = ICAO_IDENTIFIER;
        this.TCAS_Computer = tcas;
    }
    public int getNextPingRange(){return this.queueOfPingRanges.isEmpty() ? 0 : this.queueOfPingRanges.pop();}

    /**
     * Generate a Mode S Reply
     * @param sender
     * @return
     */
    private Mode_S_Result generateReply(final Aircraft sender) {
        return new Mode_S_Result(this.getOWNER(), sender, this.IDENTITY, this.getCallsign());
    }

    /**
     * Get the Unique ID associated with the transponder's owner aircraft
     * @return long containing unique ID.
     */
    public long getIdentity(){return this.IDENTITY;}

    private Squitter createSquitter() {
        return new Squitter(this.getOWNER(), MathConstants.Distance.ConversionFactors.NAUTICAL_MILES_TO_FEET * 45);
    }
    private void broadcastToOtherMode_S(final double deltaT) {
        this.timeSinceLastPing += deltaT;
        if(timeSinceLastPing > MathConstants.Time.SECOND_AS_NANOSECONDS) {
            this.getOutBox().add(createSquitter());
            this.timeSinceLastPing = 0;
            System.out.println(this.getOWNER().getRegistration() + " just pinged!");
        }
    }
    private void broadcastToMode_C(final double deltaT) {

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
        //Broadcast a ping every second
        broadcastToOtherMode_S(deltaT);
        this.TCAS_Computer.update(deltaT);
        if(!((TCA_System)this.TCAS_Computer).getOutBox().isEmpty()) {
            this.getOutBox().addAll(((TCA_System)this.TCAS_Computer).getOutBox());
        }

    }

    @Override
    public void processPing(Squitter squitter) {
        this.TCAS_Computer.process(squitter);
    }


}

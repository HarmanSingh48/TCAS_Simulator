package main.java.com.tcas_sim.components.systems.transponder;

import main.java.com.tcas_sim.communications.Transmitter;
import main.java.com.tcas_sim.communications.messages.results.Interrogation_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_C_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.communications.messages.transmissions.Transponder_Ping;
import main.java.com.tcas_sim.components.Aircraft;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Transponder implements Transmitter {
    private String SquawkCode="1200";
    private final Aircraft OWNER;
    private String callsign;
    private boolean issueMessage = false;
    protected long timeSinceLastPing = 0;
    private ConcurrentHashMap<String, Aircraft> detected_Aircrafts = new ConcurrentHashMap<>();
    private LinkedList<Integer> queueOfPingRanges = new LinkedList<>();
    private ArrayList<Transmission> outBox = new ArrayList<>();

    public Transponder(final String callsign, final String squawkCode, final Aircraft owner) {
        this.OWNER = owner;
        this.callsign = callsign;
        this.SquawkCode = squawkCode;
    }

    public String getSquawkCode() {
        return SquawkCode;
    }

    public void setSquawkCode(String squawkCode) {
        SquawkCode = squawkCode;
    }

    public Aircraft getOWNER() {
        return OWNER;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCallsign() {
        return callsign;
    }

    public boolean isIssuingMessage() {return this.issueMessage;}

    public int getNextPingRange(){return this.queueOfPingRanges.isEmpty() ? 15 : this.queueOfPingRanges.pop();}

    public abstract void processReply(Mode_C_Result reply);
    public abstract void processReply(Mode_S_Result reply);

    public abstract void processPing(Mode_C_Ping ping);
    public abstract void processPing(Mode_S_Ping ping);
    public abstract void update(double deltaT);
    @Override
    public List<Transmission> getOutBox() {return this.outBox;}
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(this.getClass() != obj.getClass()) return false;
        return this.SquawkCode.equals((
                (Transponder)obj).SquawkCode);
    }
}


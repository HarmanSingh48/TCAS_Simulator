package main.java.com.tcas_sim.components.systems.transponder;

import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_C_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.components.Aircraft;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class representing an Aircraft Transponder in the Sim.
 */
public abstract class Transponder {
    /**
     * Currently set squawk code
     */
    private String SquawkCode="1200";
    /**
     * Reference to the aircraft that this transponder is equipped on.
     */
    private final Aircraft OWNER;
    /**
     * The Flight ID
     */
    private String callsign;
    /**
     * Time (in milliseconds) since the last emitted ping of this transponder.
     */
    protected long timeSinceLastPing = 0;

    /**
     *
     */
    private LinkedList<Integer> queueOfPingRanges = new LinkedList<>();
    /**
     * The list of outgoing transmissions of this transponder.
     */
    private ArrayList<Transmission> outBox = new ArrayList<>();

    /**
     * Constructor.
     * @param callsign
     * @param squawkCode
     * @param owner
     */
    public Transponder(final String callsign, final String squawkCode, final Aircraft owner) {
        this.OWNER = owner;
        this.callsign = callsign;
        this.SquawkCode = squawkCode;
    }

    /**
     *
     * @return
     */
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


    public int getNextPingRange(){return this.queueOfPingRanges.isEmpty() ? 15 : this.queueOfPingRanges.pop();}

    /**
     * Process a Mode C reply.
     * @param reply The reply to process
     */
    public abstract void processReply(Mode_C_Result reply);

    /**
     * Process a Mode S reply.
     * @param reply
     */
    public abstract void processReply(Mode_S_Result reply);
    /**
     * Process a Mode C ping.
     * @param ping The ping to process
     */
    public abstract void processPing(Mode_C_Ping ping);
    /**
     * Process a Mode S ping.
     * @param ping The ping to process
     */
    public abstract void processPing(Mode_S_Ping ping);
    /**
     * Update the transponder's sim logic by the specified elapsed time.
     * @param deltaT time in nanoseconds.
     */
    public abstract void update(double deltaT);

    /**
     * Get the outgoing transmissions (pings, replies, etc.) of this transponder.
     * @return List containing outgoing transmissions.
     */
    public List<Transmission> getOutBox() {return this.outBox;}
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(this.getClass() != obj.getClass()) return false;
        return this.SquawkCode.equals((
                (Transponder)obj).SquawkCode);
    }
}


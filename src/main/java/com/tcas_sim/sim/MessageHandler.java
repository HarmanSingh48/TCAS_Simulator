package main.java.com.tcas_sim.sim;

import main.java.com.tcas_sim.communications.messages.results.Interrogation_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.communications.messages.transmissions.Transponder_Ping;
import main.java.com.tcas_sim.components.Aircraft;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageHandler {
    private final ArrayList<Transponder_Ping> pingQueue = new ArrayList<>();
    private final ArrayList<Transponder_Ping> futurePingQueue = new ArrayList<>();

    private final ArrayList<Interrogation_Result> replyQueue = new ArrayList<>();
    private final ArrayList<Interrogation_Result> futureReplyQueue = new ArrayList<>();

    public MessageHandler() {
    }
    public boolean add(Transmission t) {
        if(t instanceof Transponder_Ping) {
           return this.add((Transponder_Ping) t);
        } else if(t instanceof Interrogation_Result) {
           return this.add((Interrogation_Result) t);
        }
        return false;
    }
    public void addAll(Collection<Transmission> list) {
        for(Transmission t : list) {
            this.add(t);
        }
    }
    private boolean add(Transponder_Ping p) {
       return pingQueue.add(p);
    }

    private boolean add(Interrogation_Result i) {
       return replyQueue.add(i);
    }

    public boolean schedule(Transmission t) {
        if(t instanceof Transponder_Ping) {
            return this.schedule((Transponder_Ping) t);
        } else if(t instanceof Interrogation_Result) {
            return this.schedule((Interrogation_Result) t);
        }
        return false;
    }
    public void scheduleAll(Collection<Transmission> list) {
        for(Transmission t : list) {
            this.schedule(t);
        }
    }

    private boolean schedule(Transponder_Ping p) {
        return futurePingQueue.add(p);
    }
    private boolean schedule(Interrogation_Result p) {
        return futureReplyQueue.add(p);
    }

    public void advanceLists() {
        pingQueue.clear();
        pingQueue.addAll(futurePingQueue);
        futurePingQueue.clear();

        replyQueue.clear();
        replyQueue.addAll(futureReplyQueue);
        replyQueue.clear();
    }
    public List<Transponder_Ping> getAllPings() {return this.pingQueue;}
    public List<Interrogation_Result> getAllReplies() {return this.replyQueue;}
    public List<Transmission> getAllMessages() {
        ArrayList<Transmission> list = new ArrayList<>(this.pingQueue);
        list.addAll(this.replyQueue);
        list.trimToSize();
        return list;
    }
}

package main.java.com.tcas_sim.sim;

import main.java.com.tcas_sim.communications.messages.results.Reply;
import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.communications.messages.transmissions.Transponder_Ping;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    private final ArrayList<Transponder_Ping> pingQueue = new ArrayList<>();
    private final ArrayList<Transponder_Ping> futurePingQueue = new ArrayList<>();

    private final ArrayList<Reply> replyQueue = new ArrayList<>();
    private final ArrayList<Reply> futureReplyQueue = new ArrayList<>();

    public MessageHandler() {
    }
    public boolean add(Transmission t) {
        if(t instanceof Transponder_Ping) {
           return this.add((Transponder_Ping) t);
        } else if(t instanceof Reply) {
           return this.add((Reply) t);
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

    private boolean add(Reply i) {
       return replyQueue.add(i);
    }

    public boolean schedule(Transmission t) {
        if(t instanceof Transponder_Ping) {
            return this.schedule((Transponder_Ping) t);
        } else if(t instanceof Reply) {
            return this.schedule((Reply) t);
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
    private boolean schedule(Reply p) {
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
    public List<Reply> getAllReplies() {return this.replyQueue;}
    public List<Transmission> getAllMessages() {
        ArrayList<Transmission> list = new ArrayList<>(this.pingQueue);
        list.addAll(this.replyQueue);
        list.trimToSize();
        return list;
    }
}

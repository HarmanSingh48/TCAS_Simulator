package main.java.com.tcas_sim.components.systems.tcas;

import main.java.com.tcas_sim.communications.Transmitter;
import main.java.com.tcas_sim.communications.messages.results.Interrogation_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.math.Vector3d;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TCA_System implements TCAS, Transmitter {
    private final HashMap<Long, Track> detectedModeS = new HashMap<>();
    private final ArrayList<Track> detectedModeC = new ArrayList<>();
    private final ArrayList<Transmission> outBox = new ArrayList<>();

    private final long MODE_C_ID = 0xff;

    public TCA_System() {
    }

    private void updateModeSTrack(final long tID, final Vector3d pos) {
        if(detectedModeS.containsKey(tID)) {
            detectedModeS.get(tID).update(pos);
        } else {
            detectedModeS.put(tID, new Track(tID, pos));
        }
    }
    private void updateModeCTrack(final Vector3d pos) {

    }
    @Override
    public List<Transmission> getOutBox() {return this.outBox;}
    @Override
    public void process(Mode_S_Result res) {
        updateModeSTrack(res.get_Identity(), res.getPosition());
    }

    @Override
    public void process(Mode_C_Result res) {
        updateModeCTrack(res.getPingCenter());
    }

    @Override
    public void process(Mode_S_Ping ping) {
        updateModeSTrack(ping.getTargetID(), ping.getPingCenter());

    }

    @Override
    public List<Interrogation_Result> getActiveAdvisories() {
        return List.of();
    }

    private class Track {
        private final long trackID;
        private Vector3d position;
        public Track(final long targetID, final Vector3d Pos) {
            this.trackID = targetID;
            this.position = Pos;
        }

        public long getTrackID() {
            return trackID;
        }

        public Vector3d getPosition() {
            return position;
        }

        public void update(Vector3d newPos) {
            this.position = new Vector3d(newPos);
        }
    }
}

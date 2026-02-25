package main.java.com.tcas_sim.components.systems.tcas;

import main.java.com.tcas_sim.communications.messages.results.Reply;
import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Squitter;
import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;
import main.java.com.tcas_sim.util.math.Vector3d;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TCA_System implements TCAS {
    private final HashMap<Long, Track> detectedModeS = new HashMap<>();
    private final ArrayList<Track> detectedModeC = new ArrayList<>();
    private final ArrayList<Transmission> outBox = new ArrayList<>();

    private final long MODE_C_ID = 0xff;
    private final double MODE_C_CONTACT_RESOLUTION = 1.0;

    public TCA_System() {
    }

    private void updateModeSTrack(final long tID, final Vector3d pos) {
        if(detectedModeS.containsKey(tID)) {
            Track t =  detectedModeS.get(tID);
            t.setPos(pos);
            t.setAge(0);
        } else {
            detectedModeS.put(tID, new Track(tID, pos));
        }
    }
    private void updateModeCTrack(final Vector3d pos) {
        for(Track mct : detectedModeC) {
            if(checkIfSameModeC_Contact(mct, pos)) {
                mct.setPos(pos);
                mct.setAge(0);
                break;
            }
        }
    }
    private boolean checkIfSameModeC_Contact(final Track track, final Vector3d newPos) {
        double dif = newPos.distance(track.currPos);
        return dif <= track.getAge();
    }
    public List<Transmission> getOutBox() {return this.outBox;}
    @Override
    public void process(Mode_S_Result res) {
        updateModeSTrack(res.get_Identity(), res.getPosition());
    }

    @Override
    public void process(Mode_C_Result res) {
        updateModeCTrack(res.getCenter());
    }

    @Override
    public void update(double deltaT) {
        for(Track t : detectedModeS.values()) {
            t.setAge(t.getAge() + deltaT);
        }
        for(Track t : detectedModeC) {
            t.setAge(t.getAge() + deltaT);
        }
    }

    @Override
    public void process(Squitter ping) {
        updateModeSTrack(ping.getID(), ping.getCenter());

    }

    @Override
    public List<Reply> getActiveAdvisories() {
        return List.of();
    }


    private class Track {
        private final long trackID;
        private Vector3d currPos;
        private double range;
        private double age;
        public Track(final long targetID, final Vector3d Pos) {
            this.trackID = targetID;
            this.currPos = Pos;
            this.age = 0;
        }

        public long getTrackID() {
            return trackID;
        }

        public Vector3d getPosition() {
            return currPos;
        }

        public void setPos(Vector3d newPos) {
            this.currPos = new Vector3d(newPos);
        }
        public double getAge() {
            return this.age;
        }
        public void setAge(final double newAge) {
            this.age = newAge;
        }
    }
}

package main.java.com.tcas_sim.components.systems.tcas;

import main.java.com.tcas_sim.communications.messages.results.Reply;
import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Squitter;

import java.util.Collections;
import java.util.List;

public class NullTCA_System implements TCAS{
    @Override
    public void process(Mode_S_Result res) {
        //Do Nothing
        //Intentionally left blank
    }

    @Override
    public void process(Mode_C_Result res) {
        //Do Nothing
        //Intentionally left blank
    }

    @Override
    public void update(double deltaT) {
        //Do Nothing
    }

    @Override
    public void process(Squitter ping) {
        //Do Nothing
        //Intentionally left blank
    }

    @Override
    public List<Reply> getActiveAdvisories() {
        return Collections.emptyList();
    }
}

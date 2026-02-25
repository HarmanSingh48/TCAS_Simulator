package main.java.com.tcas_sim.components.systems.tcas;

import main.java.com.tcas_sim.communications.messages.results.Reply;
import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Squitter;

import java.util.List;

public interface TCAS {
    void process(Mode_S_Result res);
    void process(Mode_C_Result res);
    void update(double deltaT);
    List<Reply> getActiveAdvisories();

    void process(Squitter squitter);

}

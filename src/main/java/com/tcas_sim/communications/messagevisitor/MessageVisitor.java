package main.java.com.tcas_sim.communications.messagevisitor;

import main.java.com.tcas_sim.communications.messages.results.Interrogation_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_C_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;

public interface MessageVisitor {
    void visit(Mode_S_Ping sPing);
    void visit(Mode_C_Ping cPing);

    void visit(Mode_S_Result sRes);
    void visit(Mode_C_Result cRes);

}

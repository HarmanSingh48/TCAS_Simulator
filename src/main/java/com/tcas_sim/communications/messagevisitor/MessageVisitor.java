package main.java.com.tcas_sim.communications.messagevisitor;

import main.java.com.tcas_sim.communications.messages.results.Mode_C_Result;
import main.java.com.tcas_sim.communications.messages.results.Mode_S_Result;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_C_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Mode_S_Ping;
import main.java.com.tcas_sim.communications.messages.transmissions.Squitter;

/**
 * A Visitor Interface implemented by classes that need the ability to receive a Transponder transmission.
 */
public interface MessageVisitor {
    /**
     * Visit the specified Mode-S Ping.
     * @param sPing the Mode-S Ping.
     */
    void visit(Mode_S_Ping sPing);

    /**
     *Visit the specified Mode-C Ping.
     * @param cPing The Mode-C Ping.
     */
    void visit(Mode_C_Ping cPing);

    /**
     * Visit the specified Mode-S Result.
     * @param sRes The Mode-S Result.
     */
    void visit(Mode_S_Result sRes);

    /**
     * Visit the specified Mode-C Result.
     * @param cRes the Mode-C result.
     */
    void visit(Mode_C_Result cRes);

    void visit(Squitter squitter);

}

package main.java.com.tcas_sim.communications;

import main.java.com.tcas_sim.communications.messages.transmissions.Transmission;

import java.util.List;

public interface Transmitter {
    List<Transmission> getOutBox();
}

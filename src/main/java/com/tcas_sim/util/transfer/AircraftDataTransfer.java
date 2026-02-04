package main.java.com.tcas_sim.util.transfer;

import main.java.com.tcas_sim.util.math.Vector3d;

public record AircraftDataTransfer(String callsign, Vector3d pos, boolean isTA, boolean isRA) {
}

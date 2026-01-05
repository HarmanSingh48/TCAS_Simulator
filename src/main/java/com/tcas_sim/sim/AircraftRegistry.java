package main.java.com.tcas_sim.sim;

import java.util.HashMap;

public record AircraftRegistry() {
    private static final HashMap<Long, String> registry = new HashMap<>();
    /**
     * Add an aircraft to the global registration.
     * @param ID The unique ICAO identity for the aircraft.
     * @param registration tne registration to associate with the ID.
     */
    public void addAircraft(long ID, String registration) {
        registry.put(ID,registration);
    }

    /**
     * Remove the specified ID's association from the registry.
     * @param ID the ID to search for.
     * @return String containing the removed registration, is empty if no association with the ID found.
     */
    public String remove(long ID) {
        String rem = registry.remove(ID);
        return rem == null ? "" : rem;
    }
    /**
     * Search the global registry for the specified ID.
     * @param ID the ICAO ID to search for.
     * @return String containing the registration associated with the ID, empty if no association found.
     */
    public String search(long ID) {
        return registry.get(ID) == null ? "" : registry.get(ID);
    }
}

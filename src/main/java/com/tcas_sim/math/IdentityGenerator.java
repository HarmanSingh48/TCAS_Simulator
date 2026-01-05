package main.java.com.tcas_sim.math;

import java.util.HashMap;

public class IdentityGenerator {
    private static long next_available_ID_number = 0x1;
    public static long getNextAvailableID(){
        //Return and update ID number for next aircraft
        return next_available_ID_number++;
    }
}

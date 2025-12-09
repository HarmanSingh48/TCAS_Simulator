import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Application implements Runnable {
    /**
     * Controls the maximum FPS limit of sim
     */
    private final static double MAX_FPS = 120.0;
    /**
     * Controls whether the sim is running
     */
    private boolean isRunning = false;
    /**
     * Controls whether the debug info is tracked
     */
    private boolean debugEnabled = false;
    /**
     *  The current frame rate of the sim
     */
    private final double FRAME_RATE;
    /**
     * The maximum number of aircraft that can be spawned in at once
     */
    private final int aircraftLimit = 4;
    /**
     * The thread that the sim runs in
     */
    private Thread simThread;
    /**
     * Collection of the aircraft that are currently spawned in the simulation
     */
    private final ConcurrentHashMap<String, Aircraft> spawnedAircraft;
    /**
     * List of outgoing pings between aircraft. Pings are added to this list to be registered in the sim
     */
    private LinkedList<Transponder_Ping> pingQueue = new LinkedList<>();

    /**
     * List of outgoing pings between aircraft. Pings are added to this list to be registered in the sim
     */
    private LinkedList<Sendable> replyQueue = new LinkedList<>();
    /**
     * Sequential list of the aircraft registrations. A randomly spawned plane will take the next available registration on this list.
     * List is generated at runtime from a file called 'regs.txt.'
     */
    private LinkedList<String> availableRegistrations;
    /**
     * RNGenerator used for spawning the random aircraft
     */
    private Random rng;
    /**
     * The seed for the random number generator
     */
    private final static long SEED = 12345;

    /**
     * Constructor for an application.
     * @param FRAME_RATE the frame rate that the sim runs at. Must be less than MAX_FPS.
     * @param debugMode whether the sim starts with debug mode on.
     * @throws IllegalArgumentException if frame rate specifies is greater than MAX_FPS
     */
    public Application(final double FRAME_RATE, final boolean debugMode) throws IllegalArgumentException{
        if(FRAME_RATE < MAX_FPS) {
            this.FRAME_RATE = FRAME_RATE;
        } else {
            this.FRAME_RATE = 0;
            throw new IllegalArgumentException("Frame Rate too high. Max fps is 120.");
        }
        this.spawnedAircraft = new ConcurrentHashMap<>();
        generateRegistrations(aircraftLimit);
        this.debugEnabled = debugMode;
        rng = new Random(SEED);
    }

    /**
     * Starts a new simulation.
     */
    public void start() {
        isRunning = true;
        simThread = new Thread(this);
        simThread.start();
    }

    /**
     * Flags the simulation to stop.
     */
    public void stop(){
        isRunning = false;
    }

    /**
     * Generates the list of registrations.
     * @param length the number of registrations to load.
     */
    private void generateRegistrations(int length) {
        availableRegistrations = new LinkedList<>();
        File regs = new File("src/regs.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(regs))) {
            String line;
            int n = 0;
            while((line = reader.readLine()) != null && n < length) {
                availableRegistrations.add(line);
                n++;
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
    }

    /**
     * Populates the sim up to the limit with random aircraft.
     */
    private void populateUniverse() {
        while(spawnedAircraft.size() < aircraftLimit) {
            spawnRandomAircraft();
        }
    }

    /**
     * Update each aircraft for the passed amount time.
     * @param dTime the amount of time the update should represent.
     */
    private void updateAircraft(double dTime) {
        populateUniverse();
        for(Aircraft a : spawnedAircraft.values()) {
            a.update(dTime);
            if(a.getTransponder().isPinging()) {
                this.pingQueue.add(a.getTransponder().issuePing());
            }
            System.out.println(a);
        }
    }

    /**
     * Helper functions that generates a random aircraft.
     */
    private void spawnRandomAircraft() {
        if(spawnedAircraft.size() < aircraftLimit) {
            int min = 0, max = 10000, maxZ = 32000, maxV = 250;
            String reg = availableRegistrations.pop();
            Aircraft a = new Aircraft(reg, new Vector3d(0), new Vector3d(0), true, Transponder_Type.MODE_C);
            a.setPosition(new Vector3d(rng.nextDouble(min, max + 1), rng.nextDouble(min, max + 1), rng.nextDouble(min, maxZ + 1)));
            a.setVelocity(new Vector3d(rng.nextDouble(50, maxV), rng.nextDouble(50, maxV), 0));
            spawnedAircraft.put(reg, a);
        }
    }

    /**
     * Broadcast the given Mode-C ping and return a list of aircraft hit by the ping.
     * @param pingMC The Mode C ping to broadcast to the universe.
     * @return List of aircraft that were hit by the ping.
     */
    private ArrayList<Aircraft> checkWithinRange(Mode_C_Ping pingMC) {
        ArrayList<Aircraft> r = new ArrayList<>();
        for(Aircraft a : spawnedAircraft.values()) {
            if (pingMC.checkWithinBounds(a.getPosition())) {
                r.add(a);
            }
        }


        return r;
    }

    /**
     * Simulate a Mode-C ping on the universe.
     * @param ping THe Mode C Ping to simulate.
     */
    private void handlePing(Mode_C_Ping ping) {
        ArrayList<Aircraft> withinRange = checkWithinRange((Mode_C_Ping) ping);
    }

    /**
     * Simulate a Mode-S ping on the universe.
     * @param ping The Mode S ping to simulate.
     */
    private void handlePing(Mode_S_Ping ping) {

    }

    /**
     * Go through the message queue and handle
     */
    private void manageQueue() {

    }
    private void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    @Override
    public void run() {
        long prevTime = System.nanoTime(), perfTimer = System.currentTimeMillis();
        double nanoPerFrame = 1000000000 / this.FRAME_RATE;
        double rateAccum = 0;

        int numFrames = 0, numUpdates = 0;

        while (isRunning) {
            long currTime = System.nanoTime();

            rateAccum += (currTime - prevTime) / nanoPerFrame;

            prevTime = currTime;

            while(rateAccum >= 1) {
                //do stuff
                updateAircraft(currTime - prevTime);
                numUpdates++;
                rateAccum--;
            }

            numFrames++;
            if(System.currentTimeMillis() - perfTimer > 1000 && debugEnabled){
                System.out.println("FPS: " + numFrames + " UPS: " + numUpdates);
            }
            try{
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

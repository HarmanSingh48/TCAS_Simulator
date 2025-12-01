import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Application implements Runnable {
    private final static double MAX_FPS = 120.0;
    private boolean isRunning = false;
    private boolean debugEnabled = false;
    private final double FRAME_RATE;
    private final int aircraftLimit = 4;
    private Thread simThread;

    private ConcurrentHashMap<String, Aircraft> spawnedAircraft;
    private ConcurrentHashMap<String, Sendable> messageQueue = new ConcurrentHashMap<>();
    private LinkedList<String> availableRegistrations;
    private Random rng;
    private final static long SEED = 12345;

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
    public void start() {
        isRunning = true;
        simThread = new Thread(this);
        simThread.start();
    }

    public void stop(){
        isRunning = false;
    }
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
    private void populateUniverse() {
        while(spawnedAircraft.size() < aircraftLimit) {
            spawnRandomAircraft();
        }
    }
    private void updateAircraft(double dTime) {
        populateUniverse();
        for(Aircraft a : spawnedAircraft.values()) {
            a.update(dTime);
            System.out.println(a);
        }
    }
    private void spawnRandomAircraft() {
        int min = 0, max = 10000, maxZ = 32000, maxV = 250;
        String reg = availableRegistrations.pop();
        Aircraft a = new Aircraft(reg, new Vector3d(0), new Vector3d(0), true, Transponder_Type.MODE_C);
        a.setPosition(new Vector3d(rng.nextDouble(min, max + 1), rng.nextDouble(min, max + 1), rng.nextDouble(min, maxZ + 1)));
        a.setVelocity(new Vector3d(rng.nextDouble(50, maxV), rng.nextDouble(50, maxV), 0));
        spawnedAircraft.put(reg, a);
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

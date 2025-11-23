public class Application implements Runnable {
    private final double MAX_FPS = 120.0;
    private boolean isRunning = false;
    private final double FRAME_RATE;

    private Thread simThread;

    public Application(final double FRAME_RATE) throws IllegalCallerException{
        if(FRAME_RATE < MAX_FPS) {
            this.FRAME_RATE = FRAME_RATE;
        } else {
            this.FRAME_RATE = 0;
            throw new IllegalArgumentException("Frame Rate too high. Max fps is 120.");
        }
    }
    public void start() {
        isRunning = true;
        simThread = new Thread(this);
        simThread.start();
    }

    public void stop(){
        isRunning = false;
    }
    @Override
    public void run() {
        long prevTime = System.nanoTime(), perfTimer = System.currentTimeMillis();
        double nanoPerFrame = 1000000000 / this.FRAME_RATE;
        double deltaTime = 0;

        int numFrames = 0, numUpdates = 0;

        while (isRunning) {
            long currTime = System.nanoTime();

            deltaTime += (currTime - prevTime) / nanoPerFrame;

            prevTime = currTime;

            while(deltaTime >= 1) {
                //do stuff

                numUpdates++;
                deltaTime--;
            }

            numFrames++;
            if(System.currentTimeMillis() - perfTimer > 1000){
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

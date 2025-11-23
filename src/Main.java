public class Main {
    public static void main(String[] args) {

        Application sim = new Application(60);
        sim.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sim.stop();
    }
}
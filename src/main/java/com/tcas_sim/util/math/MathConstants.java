package main.java.com.tcas_sim.util.math;

public final class MathConstants {
    public static final class Time {
        private Time() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated.");
        }
        public final static double SECOND_AS_NANOSECONDS = 1E9;
    }
    private MathConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }
}

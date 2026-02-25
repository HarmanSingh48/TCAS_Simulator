package main.java.com.tcas_sim.util.math;

public final class MathConstants {
    public static final class Time {
        private Time() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated.");
        }
        public final static double SECOND_AS_NANOSECONDS = 1E9;
    }
    public static final class Distance{
        private Distance() {throw new UnsupportedOperationException("Utility class cannot be instantiated.");}
        public static final class ConversionFactors {
            public final static double NAUTICAL_MILES_TO_FEET = 6076.12;
            private ConversionFactors() {throw new UnsupportedOperationException("Utility class cannot be instantiated.");}
        }
    }
    private MathConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }
}

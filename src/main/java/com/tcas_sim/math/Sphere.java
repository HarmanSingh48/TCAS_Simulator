package main.java.com.tcas_sim.math;

public record Sphere(Vector3d center, double radius) {
    public boolean checkWithinBoundsInclusive(Vector3d point) {
        return this.center.distance_squared(point) <= this.radius;
    }
    public boolean checkWithinBoundsExclusive(Vector3d point) {
        return this.center.distance_squared(point) < this.radius;
    }

}

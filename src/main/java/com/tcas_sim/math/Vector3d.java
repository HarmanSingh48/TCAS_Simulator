package main.java.com.tcas_sim.math;

public record Vector3d(double x, double y, double z) {
    public Vector3d(double val){
        this(val,val,val);
    }
    public Vector3d(Vector3d other){
        this(other.x(), other.y(), other.z());
    }
    public Vector3d add(double addend) {
        return new Vector3d(this.x + addend, this.y + addend, this.z + addend);
    }
    public Vector3d add(Vector3d other) {
        return new Vector3d(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3d multiply(double factor) {
        return new Vector3d(this.x * factor, this.y * factor, this.z * factor);
    }

    public Vector3d multiply(Vector3d other) {
        return new Vector3d(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public double distance(Vector3d other) {
        Vector3d v = this.add(other.multiply(-1));
        v = v.multiply(v);
        double distance_squared = v.x + v.y + v.z;

        return Math.sqrt(distance_squared);
    }
    public double distance_squared(Vector3d other) {
        Vector3d v = this.add(other.multiply(-1));
        v = v.multiply(v);
        return v.x + v.y + v.z;
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

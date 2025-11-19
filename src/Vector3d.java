public class Vector3d {
    private final double x;
    private final double y;
    private final double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    public Vector3d add(Vector3d vector) {
        return new Vector3d(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector3d multiply(Vector3d vector) {
        return new Vector3d(this.x * vector.x, this.y * vector.y, this.z * vector.z);
    }
}

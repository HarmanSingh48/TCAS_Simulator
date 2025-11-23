public record Vector3d(double x, double y, double z) {
    public Vector3d add(Vector3d vector) {
        return new Vector3d(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector3d multiply(Vector3d vector) {
        return new Vector3d(this.x * vector.x, this.y * vector.y, this.z * vector.z);
    }
}

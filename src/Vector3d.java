public record Vector3d(double x, double y, double z) {
    public Vector3d(double val){
        this(val,val,val);
    }
    public Vector3d add(double addend) {
        return new Vector3d(this.x + addend, this.y + addend, this.z + addend);
    }
    public Vector3d add(Vector3d vector) {
        return new Vector3d(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector3d multiply(double factor) {
        return new Vector3d(this.x * factor, this.y * factor, this.z * factor);
    }

    public Vector3d multiply(Vector3d vector) {
        return new Vector3d(this.x * vector.x, this.y * vector.y, this.z * vector.z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

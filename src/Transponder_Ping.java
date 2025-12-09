public abstract class Transponder_Ping implements Sendable{
    private final Sphere pingVolume;
    public Transponder_Ping(final Vector3d aircraftPos, final double range) {
        pingVolume = new Sphere(aircraftPos, range);
    }
    public double getPingRange() {return this.pingVolume.radius();}
    public Vector3d getPingCenter() {return this.pingVolume.center();}
     public boolean checkWithinBounds(Vector3d otherPos) {return this.pingVolume.checkWithinBoundsInclusive(otherPos);}
}


public abstract class Transponder_Ping implements Sendable{
    private Sphere pingVolume;
    public Transponder_Ping(final Vector3d aircraftPos) {
        pingVolume = new Sphere(aircraftPos, 15);
    }
}


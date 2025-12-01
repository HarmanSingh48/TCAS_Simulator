public class Mode_C_Ping extends Transponder_Ping {
    public Mode_C_Ping(final Vector3d aircraftPos) {
        super(aircraftPos);
    }

    @Override
    public Mode_C_Result resolve(Transponder subject) {
        return new Mode_C_Result(subject.getOWNER().getPosition().z());
    }
}

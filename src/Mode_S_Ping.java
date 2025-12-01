public class Mode_S_Ping extends Transponder_Ping {
    private Transponder target;

    public Mode_S_Ping(final Transponder target, final Transponder sender) {
        super(sender.getOWNER().getPosition());
        this.target = target;
    }

    @Override
    public Interrogation_Result resolve(Transponder subject) {

        return null;
    }
}

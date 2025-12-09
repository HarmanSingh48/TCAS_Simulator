public class Mode_S_Ping extends Transponder_Ping {
    private Transponder target;
    private Transponder sender;

    public Mode_S_Ping(final Transponder target, final Transponder sender) {
        super(sender.getOWNER().getPosition(), 15);
        this.target = target;
        this.sender = sender;
    }
}

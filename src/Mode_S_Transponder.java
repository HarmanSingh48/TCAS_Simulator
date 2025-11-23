public class Mode_S_Transponder extends Transponder {
    private final int IDENTITY;
    public Mode_S_Transponder(final int ICAO_IDENTIFIER, final String callsign, final String squawkCode, final Aircraft Owner, final boolean is_TCAS_Equipped) {
        super(callsign, squawkCode, Owner, is_TCAS_Equipped);
        this.IDENTITY = ICAO_IDENTIFIER;
    }

    @Override
    public TCAS_Reply interogate(Transponder sender) {
        return new TCAS_Reply(new Mode_S_Result(
                this.getOWNER().getPosition().z(),
                this.IDENTITY,
                this.getCallsign(),
                this.getSquawkCode(),
                this.is_Issuing_RA(),
                this.getOWNER().getPosition()
        ));
    }
}

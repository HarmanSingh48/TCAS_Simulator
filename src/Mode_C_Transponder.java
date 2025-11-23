public class Mode_C_Transponder extends Transponder {

    public Mode_C_Transponder(final String callsign, final String squawkCode, final Aircraft Owner, final boolean is_TCAS_Equipped) {
        super(callsign, squawkCode, Owner, is_TCAS_Equipped);
    }

    @Override
    public TCAS_Reply interogate(Transponder sender) {
        return new TCAS_Reply(new Mode_C_Result(this.getOWNER().getPosition().z()));
    }
}


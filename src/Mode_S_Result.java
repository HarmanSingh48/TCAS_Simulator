public class Mode_S_Result extends Interrogation_Result {
    private final long ICAO_IDENTITY;
    private final String callsign;
    private final String Squawk_Code;
    private final boolean is_Issuing_RA;
    private final Vector3d position;

    public Mode_S_Result(final double altitude, final long IDENTIFIER, final String callsign, final String squawk_Code, final boolean is_Issuing_RA, final Vector3d position) {
        super(altitude);
        this.ICAO_IDENTITY = IDENTIFIER;
        this.callsign = callsign;
        Squawk_Code = squawk_Code;
        this.is_Issuing_RA = is_Issuing_RA;
        this.position = position;
    }

    public long get_Identity() {
        return ICAO_IDENTITY;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getSquawk_Code() {
        return Squawk_Code;
    }

    public boolean isIs_Issuing_RA() {
        return is_Issuing_RA;
    }

    public Vector3d getPosition() {
        return position;
    }
}

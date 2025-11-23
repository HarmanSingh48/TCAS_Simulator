public abstract class Transponder {
    private String SquawkCode="1200";
    private final Aircraft OWNER;
    private final String callsign;
    private final boolean TcasAvailable;
    private boolean TA_Active;
    private boolean RA_Active;

    public Transponder(final String callsign, final String squawkCode, final Aircraft owner, final boolean isTcasAvailable) {
        this.OWNER = owner;
        this.callsign = callsign;
        this.SquawkCode = squawkCode;
        this.TcasAvailable = isTcasAvailable;
        this.TA_Active = false;
        this.RA_Active = false;
    }

    public String getSquawkCode() {
        return SquawkCode;
    }

    public void setSquawkCode(String squawkCode) {
        SquawkCode = squawkCode;
    }

    public Aircraft getOWNER() {
        return OWNER;
    }

    public String getCallsign() {
        return callsign;
    }

    public boolean is_Issuing_TA() {
        return this.TA_Active;
    }
    public boolean is_Issuing_RA() {
        return this.RA_Active;
    }
    public boolean isTcasEquipped() {return this.TcasAvailable;}

    public abstract TCAS_Reply interogate(Transponder sender);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(this.getClass() != obj.getClass()) return false;
        return this.SquawkCode.equals((
                (Transponder)obj).SquawkCode);
    }
}


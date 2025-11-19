public abstract class Transponder {
    private String SquawkCode="1200";

    public Transponder(String squawkCode) {
        this.SquawkCode = squawkCode;
    }

    public String getSquawkCode() {
        return SquawkCode;
    }

    public void setSquawkCode(String squawkCode) {
        SquawkCode = squawkCode;
    }

    public abstract void ping(Transponder sender);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(this.getClass() != obj.getClass()) return false;
        return this.SquawkCode.equals((
                (Transponder)obj).SquawkCode);
    }
}


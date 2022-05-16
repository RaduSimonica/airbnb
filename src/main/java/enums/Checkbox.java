package enums;

public enum Checkbox {

    POOL("Pool"),
    WIFI("Wifi"),
    AIR_CONDITIONING("Air conditioning");

    private final String value;

    Checkbox(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}

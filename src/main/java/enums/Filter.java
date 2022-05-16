package enums;

public enum Filter {

    LOCATION("Location"),
    DURATION("Check in / Check out"),
    GUESTS("Guests");

    private final String value;

    Filter(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}

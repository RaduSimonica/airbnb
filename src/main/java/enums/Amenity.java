package enums;

public enum Amenity {

    SHAMPOO("bathroom_41"),
    POOL("parking_facilities_7");

    private final String value;

    Amenity(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}

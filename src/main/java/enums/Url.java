package enums;

public enum Url {

    HOMEPAGE("https://www.airbnb.com/");

    private final String url;

    Url(String url) {
        this.url = url;
    }

    public String get() {
        return this.url;
    }
}

package pages;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Location {

    private final String country;
    private final String city;

    @Override
    public String toString() {
        return String.format("%s, %s", this.city, this.country);
    }
}

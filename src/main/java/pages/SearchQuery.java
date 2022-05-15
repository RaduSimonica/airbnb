package pages;

import enums.GuestType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;

@Builder
@Getter
public class SearchQuery {
    private final String location;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final HashMap<GuestType, Integer> guests;
}

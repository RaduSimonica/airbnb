package pages;

import enums.GuestType;
import lombok.Builder;
import lombok.Getter;
import utils.TimeUtils;

import java.time.LocalDate;
import java.util.HashMap;

@Builder
@Getter
public class SearchQuery {

    private final Location location;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final HashMap<GuestType, Integer> guests;

    public String getDurationString() {
        return TimeUtils.formatFilterDuration(this.checkIn, this.checkOut);
    }

    public int getTotalGuests() {
        return this.guests.values().stream().mapToInt(integer -> integer).sum();
    }

    public String getTotalGuestsString() {
        int totalGuests = this.guests.values().stream().mapToInt(integer -> integer).sum();
        if (totalGuests == 1) {
            return "1 guest";
        }

        return String.format("%s guests", totalGuests);
    }
}

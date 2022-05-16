package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    public static LocalDate getDaysFromToday(int days) {
        return LocalDate.now().plusDays(days);
    }

    public static String formatFilterDuration(LocalDate checkIn, LocalDate checkOut) {
        String checkInPattern = "MMM dd";
        String checkOutPattern = checkIn.getMonthValue() == checkOut.getMonthValue() ? "dd" : checkInPattern;

        return String.format(
                "%s – %s",
                checkIn.format(DateTimeFormatter.ofPattern(checkInPattern)),
                checkOut.format(DateTimeFormatter.ofPattern(checkOutPattern))
        );
    }
}

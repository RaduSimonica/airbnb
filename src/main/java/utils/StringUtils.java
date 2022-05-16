package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String extractNumberFromText(String text) {
        return text.replaceAll("\\D+","");
    }

    public static String extractFirstNumberFromText(String text) {
        text = text.replace(",", "");
        Pattern pattern = Pattern.compile("(^|\\s)([0-9]+)($|\\s)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(2);
        }

        return text;
    }

    public static double parseRating(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

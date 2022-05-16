package utils;

public class StringUtils {

    public static String extractNumberFromText(String text) {
        return text.replaceAll("\\D+","");
    }
}

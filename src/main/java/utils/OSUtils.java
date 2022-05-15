package utils;


public class OSUtils {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isMac() {
        return OS.contains("mac") || OS.contains("darwin");
    }

    public static boolean isLinux() {
        return OS.contains("nux") || OS.contains("linux");
    }

    public static boolean isWindows() {
        return OS.contains("windows") || OS.contains("win");
    }
}

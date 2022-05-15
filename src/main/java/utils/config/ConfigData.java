package utils.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.OSUtils;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigData {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONFIG = "config.properties";

    public static String getChromeDriverEnvVariable() {
        return getGenericProperty(ConfigProperty.DRIVER_CHROME_ENV_VARIABLE);
    }

    public static String getChromeDriverPath() {
        if (OSUtils.isMac()) {
            return getGenericProperty(ConfigProperty.DRIVER_CHROME_PATH_MAC);
        }
        if (OSUtils.isLinux()) {
            return getGenericProperty(ConfigProperty.DRIVER_CHROME_PATH_LINUX);
        }
        if (OSUtils.isWindows()) {
            return getGenericProperty(ConfigProperty.DRIVER_CHROME_PATH_WINDOWS);
        }

        LOGGER.log(
                Level.WARN,
                "Cannot determine OS type. Returning Linux driver path.\n" +
                        "If it fails, please hardcode the return to your OS type."
        );

        return getGenericProperty(ConfigProperty.DRIVER_CHROME_PATH_LINUX);
    }

    public static Integer getDriverImplicitWait() {
        ConfigProperty property = ConfigProperty.DRIVER_WAIT_IMPLICIT;
        try {
            return Integer.parseInt(Objects.requireNonNull(getGenericProperty(property)));
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.log(Level.ERROR, String.format("Could not parse {%s} property as Integer.", property.get()));
            return 0;
        }
    }

    public static Integer getDriverPageLoadWait() {
        ConfigProperty property = ConfigProperty.DRIVER_WAIT_PAGE_LOAD;
        try {
            return Integer.parseInt(Objects.requireNonNull(getGenericProperty(property)));
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.log(Level.ERROR, String.format("Could not parse {%s} property as Integer.", property.get()));
            return 0;
        }
    }

    private static String getGenericProperty(ConfigProperty property) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG)) {
            Properties properties = new Properties();

            if (inputStream != null) {
                properties.load(inputStream);
                return properties.getProperty(property.get());
            }
        } catch (Exception e) {
            LOGGER.log(
                    Level.ERROR,
                    String.format("Could not retrieve %s property from %s file", property, CONFIG)
            );
        }

        return null;
    }
}

package utils.config;

public enum ConfigProperty {

    DRIVER_CHROME_PATH_MAC("driver.chrome.path.mac"),
    DRIVER_CHROME_PATH_LINUX("driver.chrome.path.linux"),
    DRIVER_CHROME_PATH_WINDOWS("driver.chrome.path.windows"),
    DRIVER_CHROME_ENV_VARIABLE("driver.chrome.env_variable"),
    DRIVER_WAIT_IMPLICIT("driver.wait.implicit"),
    DRIVER_WAIT_PAGE_LOAD("driver.wait.page_load");

    private final String value;

    ConfigProperty(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
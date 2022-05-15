package driver;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.config.ConfigData;
import org.testng.Assert;


import java.time.Duration;

public class Base {

    private static final Logger LOGGER = LogManager.getLogger();

    protected WebDriver driver;

    public void setup() {
        setupChromeDriver();
    }

    public void teardown() {
        this.driver.quit();
    }

    private void setupChromeDriver() {
        System.setProperty(ConfigData.getChromeDriverEnvVariable(), ConfigData.getChromeDriverPath());
        try {
            this.driver = new ChromeDriver(chromeOptions());
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigData.getDriverImplicitWait()));
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigData.getDriverPageLoadWait()));
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Cannot setup Chrome driver. Stopping execution.");
            Assert.fail("Cannot setup Chrome driver. Stopping execution.");
        }
    }

    private ChromeOptions chromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("enable-automation");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--dns-prefetch-disable");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return chromeOptions;
    }
}

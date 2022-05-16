package driver;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.DriverUtils;
import utils.config.ConfigData;
import org.testng.Assert;


import java.time.Duration;

public class Base {

    private Logger logger;

    protected WebDriver driver;
    protected DriverUtils driverUtils;

    @BeforeClass
    public void setup() {
        this.logger = LogManager.getLogger();
        setupChromeDriver();
        this.driverUtils = new DriverUtils(this.driver, this.getClass());
    }

    @AfterClass
    public void teardown() {
        this.driver.quit();
        this.logger.log(Level.INFO, "Test finished!");
    }

    private void setupChromeDriver() {
        System.setProperty(ConfigData.getChromeDriverEnvVariable(), ConfigData.getChromeDriverPath());
        try {
            this.driver = new ChromeDriver(chromeOptions());
            this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigData.getDriverImplicitWait()));
            this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigData.getDriverPageLoadWait()));
        } catch (IllegalStateException e) {
            this.logger.log(Level.ERROR, "Cannot setup Chrome driver. Driver not found", e);
            Assert.fail("Cannot setup Chrome driver. Stopping execution.");
        }
    }

    private ChromeOptions chromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        if (ConfigData.getChromeHeadless()) {
            chromeOptions.addArguments("--headless");
        }

        chromeOptions.addArguments("enable-automation");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--dns-prefetch-disable");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return chromeOptions;
    }
}

package pages;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.DriverUtils;
import utils.StringUtils;
import utils.config.ConfigData;

import java.util.List;


public class PropertyPage {

    private final Logger logger;
    private final DriverUtils driverUtils;

    @FindBy(className = "len26si")
    private List<WebElement> summaryDetails;

    public PropertyPage(WebDriver driver) {
        this.logger = LogManager.getLogger();
        PageFactory.initElements(driver, this);
        this.driverUtils = new DriverUtils(driver, this.getClass());
        this.logger.log(Level.DEBUG, "All page elements initialized.");
    }

    public int getNumberOfGuests() {
        WebElement child = this.summaryDetails.get(0).findElement(By.tagName("span"));
        this.driverUtils.waitForElementText(child, ConfigData.getDriverImplicitWait());

        return Integer.parseInt(StringUtils.extractNumberFromText(child.getText()));
    }
}

package pages;

import enums.Filter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.DriverUtils;
import utils.StringUtils;
import utils.config.ConfigData;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsPage {

    private final Logger logger;
    private final WebDriver driver;
    private final DriverUtils driverUtils;

    @FindBy(className = "to8eev7")
    private WebElement numberOfStays;

    @FindBy(className = "_l6n8jt")
    private List<WebElement> filterElements;

    @FindBy(className = "lwm61be")
    private List<WebElement> propertyLinks;

    @FindBy(className = "_6tbg2q")
    private List<WebElement> propertyImages;

    @FindBy(className = "r35zuq5")
    private List<WebElement> propertyWrappers;

    public ResultsPage(WebDriver driver) {
        this.logger = LogManager.getLogger();
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        this.driverUtils = new DriverUtils(this.driver, this.getClass());
        this.logger.log(Level.DEBUG, "All page elements initialized.");
    }

    public int getNumberOfStays() {
        this.driverUtils.waitForElementText(this.numberOfStays, ConfigData.getDriverImplicitWait());

        try {
            return Integer.parseInt(StringUtils.extractNumberFromText(this.numberOfStays.getText()));
        } catch (NumberFormatException e) {
            this.logger.log(Level.ERROR, "Cannot parse number of stays.");
            Assert.fail("Cannot parse number of stays.");
        }

        return 0;
    }

    public String getFilterValue(Filter filter) {
        try {
            switch (filter) {
                case LOCATION:
                    this.driverUtils.waitForElementTextInList(
                            this.filterElements,
                            0,
                            ConfigData.getDriverImplicitWait()
                    );
                    return this.filterElements.get(0).getText();
                case DURATION:
                    this.driverUtils.waitForElementTextInList(
                            this.filterElements,
                            1,
                            ConfigData.getDriverImplicitWait()
                    );
                    return this.filterElements.get(1).getText();
                case GUESTS:
                    this.driverUtils.waitForElementTextInList(
                            this.filterElements,
                            2,
                            ConfigData.getDriverImplicitWait()
                    );
                    return this.filterElements.get(2).getText();
                default:
                    this.logger.log(Level.ERROR, String.format("Invalid filter option %s", filter));
            }
        } catch (Exception e) {
            this.logger.log(
                    Level.ERROR,
                    String.format("Cannot find element for filter %s in filterElements", filter.get()),
                    e
            );
        }

        return null;
    }

    public List<String> getAllListingIds() {
        return this.propertyLinks.stream().map(this::getListingId).collect(Collectors.toList());
    }

    public String getListingId(WebElement element) {
        return StringUtils.extractNumberFromText(element.getAttribute("target"));
    }

    public PropertyPage openListing(String listingId) {
        for (WebElement element : this.propertyWrappers) {
            String targetAttr = element.getAttribute("target");
            if (StringUtils.extractNumberFromText(targetAttr).contains(listingId)) {
                this.logger.log(Level.DEBUG, String.format("Opening listing: %s", listingId));
                this.driverUtils.click(element, String.format("Listing: %s", listingId));
                return new PropertyPage(this.driver);
            }
        }

        this.logger.log(Level.ERROR, String.format("Failed to open property: %s", listingId));
        Assert.fail(String.format("Failed to open property: %s", listingId));
        return null;
    }
}

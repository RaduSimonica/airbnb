package pages;

import enums.Checkbox;
import enums.Filter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.DriverUtils;
import utils.StringUtils;
import utils.config.ConfigData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @FindBy(className = "r35zuq5")
    private List<WebElement> propertyWrappers;

    @FindBy(className = "c12h3gv8")
    private List<WebElement> propertiesTitles;

    @FindBy(className = "v1tureqs")
    private WebElement filtersButton;

    @FindBy(className = "_1ouw2w0")
    private List<WebElement> filtersCategories;

    @FindBy(xpath = "//div[@data-section-id=\"FILTER_SECTION_CONTAINER:MORE_FILTERS_AMENITIES_WITH_SUBCATEGORIES:TAB_ALL_HOMES\"]")
    private WebElement amenitiesSection;

    @FindBy(className = "_1yf4i4f")
    private List<WebElement> checkboxes;

    @FindBy(className = "_1ku51f04")
    private WebElement showStaysButton;

    @FindBy(className = "_zgc1p6")
    private WebElement filterFooter;

    @FindBy(className = "g1tup9az")
    private List<WebElement> propertySummaryDetails;

    @FindBy(className = "a8jt5op")
    private List<WebElement> pricesInList;

    @FindBy(className = "_1rhps41")
    private List<WebElement> pricesOnMap;

    @FindBy(className = "_1mhd7uz")
    private WebElement listingPopup;

    @FindBy(className = "b1ap2j3w")
    private WebElement closePillButton;

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

    public void clickFiltersButton() {
        this.driverUtils.click(this.filtersButton, "Filters button");
        this.driverUtils.waitFor(1500);
    }

    public void selectNumberOfBedrooms(int bedrooms) {
        for (WebElement element : this.filtersCategories) {
            if (element.getAttribute("aria-label").equalsIgnoreCase("Bedrooms")) {
                if (bedrooms == 0) {
                    this.driverUtils.click(
                            element.findElement(By.xpath("//div[@data-testid=\"menuItemButton-Any\"]")),
                            "Any Bedrooms"
                    );
                    return;
                }
                if (bedrooms >= 8) {
                    this.driverUtils.click(
                            element.findElement(By.xpath("//div[@data-testid=\"menuItemButton-8+\"]")),
                            "8+ Bedrooms"
                    );
                    return;
                }
                this.driverUtils.click(
                        element.findElement(
                                By.xpath(String.format("//div[@data-testid=\"menuItemButton-%s\"]", bedrooms))
                        ),
                        String.format("%s Bedrooms", bedrooms)
                );
                return;
            }
        }

        this.logger.log(Level.ERROR, "Failed to select bedrooms.");
        Assert.fail("Failed to select bedrooms.");
    }

    public void clickShowMoreAmenities() {
        this.driverUtils.click(
                this.amenitiesSection.findElement(By.tagName("button")),
                "Show more amenities button"
        );
    }

    public void clickCheckbox(Checkbox checkbox) {
        for (WebElement element : this.checkboxes) {
            if (element.getAttribute("name").equalsIgnoreCase(checkbox.get())) {
                this.driverUtils.click(element, checkbox.get());
                return;
            }
        }

        this.logger.log(Level.ERROR, String.format("Failed to click %s checkbox.", checkbox.get()));
    }

    public void clickShowStaysButton() {
        this.driverUtils.click(this.showStaysButton, "Show stays");
    }

    public Map<String, Integer> getNumberOfBedroomsForListings() {
        Map<String, Integer> allListings = new HashMap<>();

        for (WebElement element : this.propertySummaryDetails) {
            WebElement title = element.findElement(By.xpath(".//div[@class='t1jojoys dir dir-ltr']"));
            System.out.println(title.getAttribute("id"));
            WebElement bedrooms = element.findElement(By.xpath(".//span[2]/span[3]"));
            System.out.println(bedrooms.getText());
            allListings.put(
                    title.getAttribute("id"),
                    Integer.parseInt(StringUtils.extractNumberFromText(bedrooms.getText()))
            );
        }

        this.logger.log(Level.INFO, String.format("Found the number of bedrooms for %s listings.", allListings.size()));
        return allListings;
    }

    public int getNumberOfBedrooms(String listingId) {
        for (WebElement element : this.propertySummaryDetails) {
            WebElement title = element.findElement(By.xpath(".//div[@class='t1jojoys dir dir-ltr']"));
            if (title.getAttribute("id").contains(listingId)) {
                WebElement bedrooms = element.findElement(By.xpath(".//span[2]/span[3]"));
                this.logger.log(
                        Level.INFO,
                        String.format(
                                "Found %s for listing id: %s",
                                bedrooms.getText(),
                                listingId
                        )
                );
                return Integer.parseInt(StringUtils.extractNumberFromText(bedrooms.getText()));
            }
        }

        this.logger.log(Level.ERROR, String.format("Failed to get number of bedrooms for listing id: %s", listingId));
        return 0;
    }

    public int hoverOnListingReturningPrice(String listingId) {
        for (WebElement element : this.propertiesTitles) {
            String titleAttr = element.getAttribute("aria-labelledby");
            if (StringUtils.extractNumberFromText(titleAttr).contains(listingId)) {
                this.logger.log(Level.DEBUG, String.format("Hovering on listing: %s", listingId));
                this.driverUtils.hoverElement(element);
                WebElement price = element.findElement(
                        By.xpath(".//div[contains(@class, '_1n700sq')]/span[contains(@class, 'a8jt5op')]")
                );
                return Integer.parseInt(StringUtils.extractFirstNumberFromText(price.getText()));
            }
        }

        this.logger.log(Level.ERROR, String.format("Failed to find property: %s", listingId));
        Assert.fail(String.format("Failed to find property: %s", listingId));
        return 0;
    }

    public boolean isPillHighlighted(int price) {
        for (WebElement element : this.pricesOnMap) {
            int priceOnMap = Integer.parseInt(StringUtils.extractNumberFromText(element.getText()));
            if (price == priceOnMap) {
                WebElement pill = element.findElement(By.xpath("./../.."));
                this.logger.log(Level.INFO, String.format("Found pill with price %s.", price));
                return pill.getAttribute("style").startsWith("background-color: rgb(34, 34, 34);");
            }
        }

        this.logger.log(Level.ERROR, "Failed to identify highlighted pill");
        return false;
    }

    public void clickPill(int price) {
        for (WebElement element : this.pricesOnMap) {
            int priceOnMap = Integer.parseInt(StringUtils.extractNumberFromText(element.getText()));
            if (price == priceOnMap) {
                WebElement pill = element.findElement(By.xpath("./../.."));
                this.logger.log(Level.INFO, String.format("Found pill with price %s.", priceOnMap));
                this.driverUtils.click(pill, String.format("Pill with price: %s", priceOnMap));
                return;
            }
        }
        this.logger.log(Level.ERROR, String.format("Failed to identify pill with price: %s", price));
    }

    public void closePill() {
        this.driverUtils.click(this.closePillButton, "Close Pill button");
    }

    public ListingDetails gatherDetailsFromListing(String listingId) {
        for (WebElement element : this.propertiesTitles) {
            String titleAttr = element.getAttribute("aria-labelledby");
            if (StringUtils.extractNumberFromText(titleAttr).contains(listingId)) {
                ListingDetails listingDetails = ListingDetails.builder()
                        .name(element.findElement(By.xpath(".//div[contains(@class, 't1jojoys')]")).getText())
                        .price(
                                Integer.parseInt(
                                        StringUtils.extractFirstNumberFromText(
                                                element
                                                        .findElement(
                                                                By.xpath(
                                                                        ".//div[contains(@class, '_1n700sq')]" +
                                                                                "/span[contains(@class, 'a8jt5op')]"
                                                                )
                                                        )
                                                        .getText()
                                        )
                                )
                        )
                        .rating(
                                StringUtils.parseRating(
                                        element
                                                .findElement(By.xpath(".//span[contains(@class, 'ru0q88m')]"))
                                                .getText()
                                )
                        )
                        .build();

                this.logger.log(Level.INFO, String.format("Found listing details in results page: %s", listingDetails));
                return listingDetails;
            }
        }

        this.logger.log(Level.ERROR, String.format("Failed to extract details from listing id: %s", listingId));
        return null;
    }

    public ListingDetails gatherDetailsFromOpenPopup() {
        ListingDetails listingDetails =  ListingDetails.builder()
                .name(this.listingPopup.findElement(By.xpath(".//div[contains(@class, 't1jojoys')]")).getText())
                .price(
                        Integer.parseInt(
                                StringUtils.extractFirstNumberFromText(
                                        this.listingPopup
                                                .findElement(By.xpath(".//span[contains(@class, 'a8jt5op')]"))
                                                .getText()
                                )
                        )
                )
                .rating(
                        StringUtils.parseRating(
                                this.listingPopup
                                        .findElement(By.xpath(".//span[contains(@class, 'ru0q88m')]"))
                                        .getText()
                        )
                )
                .build();

        this.logger.log(Level.INFO, String.format("Found listing details in popup: %s", listingDetails));
        return listingDetails;
    }
}

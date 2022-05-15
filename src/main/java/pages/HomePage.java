package pages;

import enums.GuestType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.DriverUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomePage {

    private static final Logger LOGGER = LogManager.getLogger();
    private final DriverUtils driverUtils;

    @FindBy(className = "_oda838")
    private WebElement closeAdModal;

    @FindBy(className = "f19g2zq0")
    private List<WebElement> searchBox;

    @FindBy(id = "bigsearch-query-location-input")
    private WebElement locationInput;

    @FindBy(className = "l1vto4to")
    private List<WebElement> dateAndGuestsButtons;

    @FindBy(className = "_1258d0t")
    private List<WebElement> datePickerDays;

    @FindBy(className = "_ul9u8c")
    private List<WebElement> guestsButtons;

    @FindBy(className = "_jxxpcd")
    private WebElement bigSearchButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driverUtils = new DriverUtils(driver);
        LOGGER.log(Level.DEBUG, "All page elements initialized.");
    }

    public void closeAdModal() {
        if (this.closeAdModal.isDisplayed()) {
            this.driverUtils.click(this.closeAdModal, "closeAdModal");
            return;
        }
        LOGGER.log(Level.DEBUG, "Ad modal close button not displayed.");
    }

    public void clickAnywhereButton() {
        this.driverUtils.clickElementInListByText(this.searchBox, "Anywhere");
    }

    public void clickAnyWeekButton() {
        this.driverUtils.clickElementInListByText(this.searchBox, "Any Week");
    }

    public void clickAddGuestsButton() {
        this.driverUtils.clickElementInListByText(this.searchBox, "Add guests");
    }

    public void inputLocation(String text) {
        this.driverUtils.click(this.locationInput, "locationInput");
        this.driverUtils.sendText(this.locationInput, "locationInput", text);
    }

    public void clickCheckInButton() {
        this.driverUtils.clickElementInListByText(this.dateAndGuestsButtons, "Check in");
    }

    public void clickCheckOutButton() {
        this.driverUtils.clickElementInListByText(this.dateAndGuestsButtons, "Check out");
    }

    public void clickWhoButton() {
        this.driverUtils.clickElementInListByText(this.dateAndGuestsButtons, "Who");
    }

    public void clickDate(LocalDate localDate) {
        String localDateStr = localDate.format(DateTimeFormatter.ISO_DATE);
        String dataTestIdAttr = String.format("datepicker-day-%s", localDateStr);
        this.driverUtils.clickElementInListByAttribute(
                this.datePickerDays,
                "data-testid",
                dataTestIdAttr
        );
    }

    public void adjustNumberOfGuests(GuestType type, int adjustBy) {
        if (adjustBy == 0) {
            LOGGER.log(Level.WARN, "adjustBy value is 0. Are you sure it's correct?");
            return;
        }

        StringBuilder value = new StringBuilder()
                .append("stepper-")
                .append(type.toString().toLowerCase())
                .append("-")
                .append(adjustBy < 0 ? "decrease-" : "increase-")
                .append("button");

        for (int i = 0; i < Math.abs(adjustBy); i++) {
            this.driverUtils.clickElementInListByAttribute(
                    this.guestsButtons,
                    "data-testid",
                    value.toString()
            );
        }

        LOGGER.log(
                Level.INFO,
                String.format(
                        "Adjusted %s %s times",
                        type,
                        adjustBy
                )
        );
    }

    public void clickSearchButton() {
        this.driverUtils.click(this.bigSearchButton, "Search button");
    }
}

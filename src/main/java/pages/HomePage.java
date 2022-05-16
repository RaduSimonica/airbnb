package pages;

import enums.GuestType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.DriverUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomePage {

    private final Logger logger;
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

    @FindBy(className = "_1665lvv")
    private List<WebElement> numbersOfGuests;

    @FindBy(className = "_jxxpcd")
    private WebElement bigSearchButton;

    public HomePage(WebDriver driver) {
        this.logger = LogManager.getLogger();
        PageFactory.initElements(driver, this);
        this.driverUtils = new DriverUtils(driver, this.getClass());
        this.logger.log(Level.DEBUG, "All page elements initialized.");
    }

    public void closeAdModal() {
        if (this.closeAdModal.isDisplayed()) {
            this.driverUtils.click(this.closeAdModal, "closeAdModal");
            return;
        }
        this.logger.log(Level.DEBUG, "Ad modal close button not displayed.");
    }

    public void clickAnywhereButton() {
        this.driverUtils.clickElementInListByText(this.searchBox, "Anywhere");
    }

    public void inputLocation(String text) {
        this.driverUtils.click(this.locationInput, "locationInput");
        this.driverUtils.sendText(this.locationInput, "locationInput", text);
    }

    public void clickCheckInButton() {
        this.driverUtils.clickElementInListByText(this.dateAndGuestsButtons, "Check in");
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

    public void setNumberOfGuests(GuestType type, int value) {
        // In case the actual number of guests is not 0, subtract the current number of guests.
        value -= getNumberOfGuests(type);

        if (value == 0) {
            this.logger.log(Level.WARN, "adjustBy value is 0. Are you sure it's correct?");
            return;
        }

        StringBuilder attrValue = new StringBuilder()
                .append("stepper-")
                .append(type.toString().toLowerCase())
                .append("-")
                .append("increase-")
                .append("button");

        for (int i = 0; i < Math.abs(value); i++) {
            this.driverUtils.clickElementInListByAttribute(
                    this.guestsButtons,
                    "data-testid",
                    attrValue.toString()
            );
        }
    }

    public int getNumberOfGuests(GuestType type) {
        for (WebElement element : this.numbersOfGuests) {
            try {
                WebElement sibling = element.findElements(By.tagName("span")).get(0);
                if (sibling.getAttribute("data-testid").contains(type.toString().toLowerCase())) {
                    return Integer.parseInt(sibling.getText());
                }
            } catch (NoSuchElementException | IndexOutOfBoundsException e) {
                this.logger.log(Level.TRACE, "Cannot find sibling for number of guests", e);
            }
        }

        this.logger.log(Level.ERROR, String.format("Cannot find number %s number of guests", type));
        return 0;
    }

    public void clickSearchButton() {
        this.driverUtils.click(this.bigSearchButton, "Search button");
    }
}

package utils;

import enums.GuestType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.ResultsPage;
import pages.SearchQuery;

import java.util.Map;

public class Routines {

    private static final Logger LOGGER = LogManager.getLogger();
    private WebDriver driver;

    public Routines(WebDriver driver) {
        this.driver = driver;

    }

    public ResultsPage searchPropertiesOnHomePage(SearchQuery searchQuery) {
        LOGGER.log(Level.INFO, String.format("Starting a new search routine for %s", searchQuery.getLocation()));

        HomePage homePage = new HomePage(this.driver);
        homePage.clickAnywhereButton();
        homePage.inputLocation(searchQuery.getLocation().toString());
        homePage.clickCheckInButton();
        homePage.clickDate(searchQuery.getCheckIn());
        homePage.clickDate(searchQuery.getCheckOut());
        homePage.clickWhoButton();

        for (Map.Entry<GuestType, Integer> guest : searchQuery.getGuests().entrySet()) {
            homePage.setNumberOfGuests(guest.getKey(), guest.getValue());
        }

        homePage.clickSearchButton();

        return new ResultsPage(this.driver);
    }
}

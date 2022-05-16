package tests;

import driver.Base;
import enums.Filter;
import enums.GuestType;
import enums.Url;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.DriverUtils;
import utils.Routines;
import utils.TimeUtils;

import java.util.HashMap;

public class TestResultsAndDetailsPageMatchExtraFilters extends Base {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test(
            description = "Check if the results and details page match the extra filters."
    )
    public void testExtraFilters() {

        this.driverUtils.openPage(Url.HOMEPAGE);

        HomePage homePage = new HomePage(this.driver);
        homePage.closeAdModal();

        HashMap<GuestType, Integer> guests = new HashMap<>();
        guests.put(GuestType.ADULTS, 2);
        guests.put(GuestType.CHILDREN, 1);

        SearchQuery searchQuery = SearchQuery.builder()
                .location(Location.builder().country("Italy").city("Rome").build())
                .checkIn(TimeUtils.getDaysFromToday(7))
                .checkOut(TimeUtils.getDaysFromToday(14))
                .guests(guests)
                .build();

        ResultsPage resultsPage = new Routines(this.driver).searchPropertiesOnHomePage(searchQuery);

        resultsPage.clickFiltersButton();
        resultsPage.selectNumberOfBedrooms(5);
        // Fails here! Good night for now :)
        resultsPage.clickShowMoreAmenities();


    }
}

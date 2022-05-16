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
import pages.containers.Location;
import pages.containers.SearchQuery;
import utils.Routines;
import utils.TimeUtils;

import java.util.HashMap;

public class TestNumberOfGuests extends Base {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test(
            description = "Check if all listings from the 1st page " +
                    "can accommodate at least the number of expected guests"
    )
    public void testNumberOfGuests() {

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

        Assert.assertTrue(
                resultsPage.getNumberOfStays() > 0,
                "Search returned at least 1 result."
        );
        Assert.assertEquals(
                resultsPage.getFilterValue(Filter.LOCATION),
                searchQuery.getLocation().getCity(),
                "Location filter is correct."
        );
        Assert.assertEquals(
                resultsPage.getFilterValue(Filter.DURATION),
                searchQuery.getDurationString(),
                "Duration filter is correct."
        );
        Assert.assertEquals(
                resultsPage.getFilterValue(Filter.GUESTS),
                searchQuery.getTotalGuestsString(),
                "Guests filter is correct."
        );

        for (String listingId : resultsPage.getAllListingIds()) {
            PropertyPage propertyPage = resultsPage.openListing(listingId);

            LOGGER.log(Level.INFO, String.format("Checking number of guests for property: %s", listingId));

            this.driverUtils.switchToLastOpenedTab();

            Assert.assertTrue(
                    propertyPage.getNumberOfGuests() >= searchQuery.getTotalGuests(),
                    "Property page can accommodate at least the total number of expected guest."
            );

            this.driverUtils.switchToMainTab();
        }
    }
}

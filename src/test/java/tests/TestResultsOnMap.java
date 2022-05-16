package tests;

import driver.Base;
import enums.GuestType;
import enums.Url;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.Routines;
import utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class TestResultsOnMap extends Base {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test(description = "Check if the results and details page match the extra filters.")
    public void testMap() {

        this.driverUtils.openPage(Url.HOMEPAGE);

        HomePage homePage = new HomePage(this.driver);
        homePage.closeAdModal();

        Map<GuestType, Integer> guests = new HashMap<>();
        guests.put(GuestType.ADULTS, 2);
        guests.put(GuestType.CHILDREN, 1);

        SearchQuery searchQuery = SearchQuery.builder()
                .location(Location.builder().country("Italy").city("Rome").build())
                .checkIn(TimeUtils.getDaysFromToday(7))
                .checkOut(TimeUtils.getDaysFromToday(14))
                .guests(guests)
                .build();

        ResultsPage resultsPage = new Routines(this.driver).searchPropertiesOnHomePage(searchQuery);

        for (String listingId : resultsPage.getAllListingIds()) {
            LOGGER.log(Level.INFO, String.format("Checking listing id: %s", listingId));
            int price = resultsPage.hoverOnListingReturningPrice(listingId);

            Assert.assertTrue(
                    resultsPage.isPillHighlighted(price),
                    "Check if the correct pill is highlighted on the map"
            );

            ListingDetails expectedListingDetails = resultsPage.gatherDetailsFromListing(listingId);
            resultsPage.clickPill(price);

            Assert.assertTrue(
                    resultsPage.gatherDetailsFromOpenPopup().equals(expectedListingDetails),
                    "Check if listing details are the same in the results list and popup."
            );

            resultsPage.closePill();
        }
    }
}

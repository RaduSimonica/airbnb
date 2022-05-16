package tests;

import driver.Base;
import enums.*;
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
import java.util.List;
import java.util.Map;

public class TestResultsAndDetailsPageMatchExtraFilters extends Base {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_BEDROOMS = 5;

    @Test(description = "Check if the results and details page match the extra filters.")
    public void testExtraFilters() {

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

        resultsPage.clickFiltersButton();
        resultsPage.selectNumberOfBedrooms(NUMBER_OF_BEDROOMS);
        resultsPage.clickShowMoreAmenities();
        resultsPage.clickCheckbox(Checkbox.POOL);
        resultsPage.clickShowStaysButton();

        List<String> expectedListingIds = resultsPage.getAllListingIds();

        for (String listingId : expectedListingIds) {
            LOGGER.log(Level.INFO, String.format("Checking listing id: %s", listingId));
            Assert.assertTrue(
                    resultsPage.getNumberOfBedrooms(listingId) >= NUMBER_OF_BEDROOMS,
                    String.format(
                            "Check if Listing id %s has at least %s bedrooms.",
                            listingId,
                            NUMBER_OF_BEDROOMS
                    )
            );

            PropertyPage propertyPage = resultsPage.openListing(listingId);
            this.driverUtils.switchToLastOpenedTab();

            propertyPage.clickShowAllAmenitiesButton();
            Assert.assertTrue(
                    propertyPage.AmenityExists(Amenity.POOL),
                    "Check if the property has any type of Pool."
            );
            this.driverUtils.switchToMainTab();
        }
    }
}

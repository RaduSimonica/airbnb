package tests;

import driver.Base;
import enums.GuestType;
import enums.Url;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchQuery;
import utils.Routines;

import java.time.LocalDate;
import java.util.HashMap;

public class TestNumberOfGuests extends Base {

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void testNumberOfGuests() {

        this.driverUtils.openPage(Url.HOMEPAGE);

        HomePage homePage = new HomePage(this.driver);
        homePage.closeAdModal();

        HashMap<GuestType, Integer> guests = new HashMap<>();
        guests.put(GuestType.ADULTS, 2);
        guests.put(GuestType.CHILDREN, 1);

        new Routines(this.driver).searchPropertiesOnHomePage(
                SearchQuery.builder()
                        .location("Rome, Italy")
                        .checkIn(LocalDate.now().plusDays(7))
                        .checkOut(LocalDate.now().plusDays(14))
                        .guests(guests)
                        .build()
        );

        LOGGER.log(Level.DEBUG, "Stop");
    }
}

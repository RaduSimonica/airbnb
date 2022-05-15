package utils;

import enums.Url;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DriverUtils {

    private final Logger LOGGER = LogManager.getLogger();
    private WebDriver driver;

    public DriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void openPage(Url url) {
        this.driver.navigate().to(url.get());
        LOGGER.log(Level.INFO, String.format("Opened page: ", url));
    }

    public void clickElementInListByText(List<WebElement> webElementList, String textOnElement) {
        for (WebElement element : webElementList) {
            if (element.getText().equalsIgnoreCase(textOnElement)) {
                click(element, textOnElement);
                return;
            }
        }
        LOGGER.log(Level.WARN, String.format("'%s' element not found.", textOnElement));
    }

    public void clickElementInListByAttribute(List<WebElement> webElementList, String attrName, String attrValue) {
        String attribute = String.format(
                "%s=\"%s\"",
                attrName,
                attrValue
        );
        for (WebElement element : webElementList) {
            if (element.getAttribute(attrName).equalsIgnoreCase(attrValue)) {
                click(element, attribute);
                return;
            }
        }
        LOGGER.log(Level.WARN, String.format("Element with attribute: %s not found.", attribute));
    }

    public void click(WebElement element, String name) {
        if (element.isDisplayed()) {
            element.click();
            LOGGER.log(Level.INFO, String.format("Clicked on %s.", name));
            return;
        }
        LOGGER.log(Level.DEBUG, String.format("%s not displayed.", name));
    }

    public void sendText(WebElement element, String name, String textToSend) {
        element.sendKeys(textToSend);
        LOGGER.log(Level.INFO, String.format("Sent text to element: %s", name));
    }
}

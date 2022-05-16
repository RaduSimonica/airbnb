package utils;

import enums.Url;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.config.ConfigData;

import java.util.ArrayList;
import java.util.List;

public class DriverUtils {

    private final Logger logger;
    private final WebDriver driver;

    private String mainTab;
    private List<String> allTabs;

    public DriverUtils(WebDriver driver, Class<?> loggerClass) {
        this.driver = driver;
        this.logger = LogManager.getLogger(loggerClass);
    }

    public void openPage(Url url) {
        this.driver.navigate().to(url.get());
        this.logger.log(Level.INFO, String.format("Opened page: %s", url.get()));
    }

    public void switchToLastOpenedTab() {
        this.mainTab = this.driver.getWindowHandle();
        this.allTabs = new ArrayList<>(this.driver.getWindowHandles());

        String newestTab = this.allTabs.get(this.allTabs.size() - 1);
        this.driver.switchTo().window(newestTab);
        this.logger.log(Level.DEBUG, String.format("Switched to tab: %s", newestTab));
    }

    public void switchToMainTab() {
        this.driver.switchTo().window(this.mainTab);
        this.logger.log(Level.DEBUG, String.format("Switched back to main tab: %s", this.mainTab));
    }

    public void clickElementInListByText(List<WebElement> webElementList, String textOnElement) {
        for (WebElement element : webElementList) {
            if (element.getText().equalsIgnoreCase(textOnElement)) {
                click(element, textOnElement);
                return;
            }
        }
        this.logger.log(Level.WARN, String.format("'%s' element not found.", textOnElement));
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
        this.logger.log(Level.WARN, String.format("Element with attribute: %s not found.", attribute));
    }

    public void click(WebElement element, String name) {
        waitForElementToBeClickable(element, ConfigData.getDriverImplicitWait());
        if (element.isDisplayed() || element.isEnabled()) {
            element.click();
            this.logger.log(Level.INFO, String.format("Clicked on %s.", name));
            return;
        }
        this.logger.log(Level.DEBUG, String.format("%s not displayed.", name));
    }

    public void sendText(WebElement element, String name, String textToSend) {
        element.sendKeys(textToSend);
        this.logger.log(Level.INFO, String.format("Sent text to element: %s", name));
    }

    public void waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        for (int i = 0; i < timeoutInSeconds; i++) {
            if (element.isDisplayed() && element.isEnabled()) {
                return;
            }
            waitFor(1000);
        }
    }

    public void waitForElementText(WebElement element, int timeoutInSeconds) {
        this.logger.log(
                Level.INFO,
                String.format("Waiting for element to have text. Timeout %s seconds...", timeoutInSeconds)
        );

        int pollingRateMillis = 1000;
        for (int i = 0; i < timeoutInSeconds; i++) {
            String text = element.getText();
            if (text != null && !text.equals("")) {
                this.logger.log(
                        Level.DEBUG,
                        String.format(
                                "Text: '%s' found on Element %s after %s seconds.",
                                text,
                                element,
                                i
                        )
                );
                return;
            }
            waitFor(pollingRateMillis);
        }

        this.logger.log(
                Level.WARN,
                String.format(
                        "Timeout waiting for element %s to have text after %s seconds",
                        element,
                        timeoutInSeconds
                )
        );
    }

    public void waitForElementTextInList(List<WebElement> elements, int index, int timeoutInSeconds) {
        this.logger.log(
                Level.INFO,
                String.format("Waiting for element in list to be displayed. Timeout %s seconds...", timeoutInSeconds)
        );

        int pollingRateMillis = 1000;
        for (int i = 0; i < timeoutInSeconds; i++) {
            String text = elements.get(index).getText();
            if (text != null && !text.equals("")) {
                this.logger.log(
                        Level.DEBUG,
                        String.format(
                                "Text: '%s' found on Element %s after %s seconds.",
                                text,
                                elements.get(index),
                                i
                        )
                );
                return;
            }
            waitFor(pollingRateMillis);
        }

        this.logger.log(
                Level.WARN,
                String.format(
                        "Timeout waiting for element %s to have text after %s seconds",
                        elements.get(index),
                        timeoutInSeconds
                )
        );
    }

    public void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}

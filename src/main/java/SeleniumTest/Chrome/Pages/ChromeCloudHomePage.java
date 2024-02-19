package SeleniumTest.Chrome.Pages;

import SeleniumTest.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class ChromeCloudHomePage extends AbstractPage {
    private static final String SEARCH_BUTTON_XPATH = "//input[@placeholder='Search']";

    public void performSearchOnCloudChrome(String text) {
        WebElement searchInput = getDriver().findElement(By.xpath(SEARCH_BUTTON_XPATH));
        searchInput.click();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);
    }
}

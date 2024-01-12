package SeleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {
    WebDriver driver;

    public WebElement waitForElement(String locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public void closeUnexpectedElementIfAppears(String locator) {
        try {
            WebElement unexpectedElement = waitForElement(locator, 15);
            if (unexpectedElement != null && unexpectedElement.isDisplayed()) {
                unexpectedElement.click();
            }
        } catch (TimeoutException e) {
            System.out.println("Unexpected element did not appear.");
        }
    }
}

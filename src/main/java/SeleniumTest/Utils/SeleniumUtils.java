package SeleniumTest.Utils;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class SeleniumUtils {

    @SneakyThrows
    public static void waitUntilElementGetsDisplayed(By locator) {
        try {
            new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(1))
                    .until(item -> getDriver().findElement(locator).isDisplayed());
        } catch (Exception e) {
            throw new Exception("Element is not displayed");
        }
    }

    public static WebElement getElementWithWait(By locator) {
        try {
            return new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(1))
                    .until(item -> getDriver().findElement(locator));
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getLocatorsRelatedToPage(String pageUrl) {
        return Arrays.stream(ElementLocators.values())
                .filter(item -> pageUrl.contains(item.getDomen()))
                .map(ElementLocators::getPath)
                .toList();
    }

    public void navigateToTab(int tabNumber){
        getDriver().switchTo().window(getDriver().getWindowHandles().stream()
                .toList()
                .get(tabNumber)
        );
    }
}

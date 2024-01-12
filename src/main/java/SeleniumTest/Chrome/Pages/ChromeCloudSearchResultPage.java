package SeleniumTest.Chrome.Pages;

import SeleniumTest.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static SeleniumTest.ThreadLocalDriver.getDriver;
import static SeleniumTest.Utils.SeleniumUtils.getElementWithWait;

public class ChromeCloudSearchResultPage extends AbstractPage {
    private static final String SEARCH_RESULT_TABLE_XPATH = "//div[@class='gs-title']/a";

    public void clickSearchResultItem() {
        getElementWithWait(By.xpath("//div[@class = 'gsc-expansionArea']"));
        WebElement element = getDriver().findElements(By.xpath(SEARCH_RESULT_TABLE_XPATH))
                .stream()
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Search result contains no elements"));
        element.click();


    }
}

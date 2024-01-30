package SeleniumTest.Chrome.Components;

import SeleniumTest.Utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;

import static SeleniumTest.ThreadLocalDriver.getDriver;
import static SeleniumTest.Utils.SeleniumUtils.waitUntilElementGetsDisplayed;

public class AddEstimatePopupWindow {
    private static final String ADD_ESTIMATE_POPUP_WINDOW_XPATH = "//div[@aria-label='Add to this estimate']";
    private static final String ADD_TO_ESTIMATE_ITEM_XPATH = ADD_ESTIMATE_POPUP_WINDOW_XPATH + "//div[@role = 'button']";

    public void checkPopupWindowOpened() {
        waitUntilElementGetsDisplayed(By.xpath(ADD_ESTIMATE_POPUP_WINDOW_XPATH));
    }

    public void addEstimateElementClick(String text) {
        getEstimateProductItem(text).click();
    }

    private WebElement getEstimateProductItem(String text) {
        return getDriver().findElements(By.xpath(ADD_TO_ESTIMATE_ITEM_XPATH))
                .stream()
                .filter(item -> item.getText().contains(text))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Element containing text [%s] not found.)".formatted(text)));
    }
}

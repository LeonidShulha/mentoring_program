package SeleniumTest.Chrome.Components;

import SeleniumTest.Utils.JavaScriptExecutorUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Locale;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class ChromeDropdown {
    private static final String DROPDOWN_SELECTED_ITEM_XPATH = "//span[text()='%s']/ancestor::div[@role='combobox']";
    private static final String DROPDOWN_ELEMENT_XPATH = DROPDOWN_SELECTED_ITEM_XPATH + "/following-sibling::div/ul/li";
    private String dropdownName;

    public ChromeDropdown(String dropdownName) {
        this.dropdownName = dropdownName;
    }

    public WebElement getSelectedDropdownValue() {
        WebElement element = getDriver().findElement(By.xpath((DROPDOWN_SELECTED_ITEM_XPATH.formatted(dropdownName))));
        new JavaScriptExecutorUtils().scrollToElement(element);
        return element;
    }

    public ChromeDropdown expandDropdown() {
        getSelectedDropdownValue().click();
        getElementsFromDropdown();
        return this;
    }

    private List<WebElement> getElementsFromDropdown() {
        return getDriver().findElements(By.xpath(DROPDOWN_ELEMENT_XPATH.formatted(this.dropdownName)));
    }

    public void selectValueFromDropdown(String value) {
        findValueInDropdown(value).click();
    }

    public WebElement findValueInDropdown(String value) {
        return getElementsFromDropdown().stream()
                .filter(item -> item.getText().toLowerCase().contains(value.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Value '%s' is not found in '%s' dropdown)".formatted(value, dropdownName)));
    }
}

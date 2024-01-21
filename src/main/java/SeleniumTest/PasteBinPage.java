package SeleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;


public class PasteBinPage extends AbstractPage {

    private static final String NEW_PASTE_INPUT_FIELD_XPATH = "//textarea[@aria-label = 'New Paste']";


    private static final String DROPDOWN_SELECTED_VALUE_XPATH = "//label[text()='%s']/following-sibling::*//*[@class = 'selection']";
    private static final String DROPDOWN_OPTIONS_XPATH = "//span[@class = 'select2-results']//ul//li";
    private static final String PASTE_EXPIRATION = "Paste Expiration:";
    private static final String SYNTAX_DROPDOWN = "Syntax Highlighting:";

    private static final String PASTE_NAME_INPUT_FIELD_XPATH = "//input[@id='postform-name']";
    private static final String CREATE_PASTE_BUTTON_XPATH = "//button[text() = 'Create New Paste']";
    private static final String POLICY_POPUP_AGREE_BUTTON_XPATH = "//*[text()='AGREE']/parent::button";
    private static final String HIDE_SLIDE_BANNER_BUTTON_XPATH = "//*[contains(@id,'hideSlideBanner')]";


    public PasteBinPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement pasteInputField() {
        return driver.findElement(By.xpath(NEW_PASTE_INPUT_FIELD_XPATH));
    }


    private WebElement selectedDropdownValueOf(String dropdownName) {
        return driver.findElement(By.xpath(DROPDOWN_SELECTED_VALUE_XPATH.formatted(dropdownName)));
    }

    private WebElement nameInputField() {
        return driver.findElement(By.xpath(PASTE_NAME_INPUT_FIELD_XPATH));
    }

    private WebElement savePasteButton() {
        return driver.findElement(By.xpath(CREATE_PASTE_BUTTON_XPATH));
    }

    public void closeBanners() {
        List<String> elements = Arrays.asList(POLICY_POPUP_AGREE_BUTTON_XPATH, HIDE_SLIDE_BANNER_BUTTON_XPATH);
        elements.forEach(this::closeUnexpectedElementIfAppears);
    }

    public void setNewPasteInputFieldText(String text) {
        pasteInputField().click();
        pasteInputField().sendKeys(text);
    }

    public void setPasteExpiration(String value) {
        setDropdownValue(PASTE_EXPIRATION, value);
    }

    public void setPasteSyntaxHighlight(String value) {
        setDropdownValue(SYNTAX_DROPDOWN, value);
    }

    public void setNameInputField(String value) {
        nameInputField().click();
        nameInputField().sendKeys(value);
    }

    public void savePaste() {
        savePasteButton().click();
    }


    private void setDropdownValue(String dropdown, String value) {
        selectedDropdownValueOf(dropdown).click();
        driver.findElements(By.xpath(DROPDOWN_OPTIONS_XPATH)).stream()
                .filter(item -> item.getText().equals(value))
                .findFirst()
                .ifPresent(WebElement::click);
    }
}

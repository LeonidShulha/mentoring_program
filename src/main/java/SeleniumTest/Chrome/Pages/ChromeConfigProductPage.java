package SeleniumTest.Chrome.Pages;

import SeleniumTest.AbstractPage;
import SeleniumTest.Chrome.Components.ChromeDropdown;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static SeleniumTest.ThreadLocalDriver.getDriver;
import static SeleniumTest.Utils.SeleniumUtils.getElementWithWait;

@Getter
public class ChromeConfigProductPage extends AbstractPage {
    private static final String CONFIG_TAB_TITLE_XPATH = "//h1[@aria-label='Selected product title']";
    private static final String PRODUCT_CALCULATED_PRICE_XPATH = CONFIG_TAB_TITLE_XPATH + "/following-sibling::*[2]/span[1]";
    private static final String ADVANCED_SETTING_TOGGLE_XPATH = "//h2[contains(text(), 'Instances')]/following-sibling::div//button";
    private static final String NUMBER_OF_INSTANCES_INPUT_FIELD_XPATH = "//div[text()='Number of Instances']/parent::*/following-sibling::div//label";
    private static final String REGULAR_PROVISIONING_MODEL_SELECTION_XPATH = "//label[text()='Regular']";
    private static final String NUMBER_OF_CPU_SLIDER = "(//div[@jsname = 'PFprWc'])[1]";
    private static final String ADD_GPU_TOGGLE = "//*[text()='Add GPUs']//parent::div/preceding-sibling::div//button";
    private static final String COMMITTED_USAGE_1_YEAR_BUTTON_CSS = "input[type='radio'][id='1-year']";

    private ChromeDropdown serviceType = new ChromeDropdown("Service type");
    private ChromeDropdown operatingSystem = new ChromeDropdown("Operating System / Software");
    private ChromeDropdown machineType = new ChromeDropdown("Machine type");
    private ChromeDropdown gpuModel = new ChromeDropdown("GPU Model");
    private ChromeDropdown numberOfGpus = new ChromeDropdown("Number of GPUs");
    private ChromeDropdown localSsd = new ChromeDropdown("Local SSD");
    private ChromeDropdown region = new ChromeDropdown("Region");

    private WebElement advancedSettingsToggle() { return getDriver().findElement(By.xpath(ADVANCED_SETTING_TOGGLE_XPATH)); }

    private WebElement addGpusToggle() {
        return getDriver().findElement(By.xpath(ADD_GPU_TOGGLE));
    }

    public String getProductTitle() {
        return getElementWithWait(By.xpath(CONFIG_TAB_TITLE_XPATH)).getText();
    }

    public String getProductCalculatedPrice() {
        return getElementWithWait(By.xpath(PRODUCT_CALCULATED_PRICE_XPATH)).getText();
    }

    public void setNumberOfInstances(int number) {
        WebElement element = getDriver().findElement(By.xpath(NUMBER_OF_INSTANCES_INPUT_FIELD_XPATH));
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(String.valueOf(number));
    }

    public boolean getAdvancedSettingToggle() {
        return Boolean.parseBoolean(
                advancedSettingsToggle().getAttribute("aria-checked")
        );
    }

    public void setAdvancedSettingToggle(boolean isActive) {
        if (getAdvancedSettingToggle() != isActive) {
            advancedSettingsToggle().click();
        }
    }

    public boolean getAddGpusToggle() {
        return Boolean.parseBoolean(
                addGpusToggle().getAttribute("aria-checked")
        );
    }

    public void setAddGpuToggle(boolean isActive) {
        if (getAddGpusToggle() != isActive) {
            addGpusToggle().click();
        }
    }

    public void setCommittedUsage(String option) {
        String xpath = "//label[@for='%s']";
        switch (option) {
            case "1 year" -> javaScriptExecutorUtils.scrollToElement(getDriver().findElement(By.xpath(xpath.formatted("1-year")))).click();
            default -> throw new IllegalArgumentException("No committed use discount options");
        }
    }
}
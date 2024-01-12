package SeleniumTest.Chrome.Components;

import SeleniumTest.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class EstimateDetailsPopup extends AbstractPage {
    private static final String DETAILS_POPUP_XPATH = "//div[@role='dialog' and @aria-label='View Details']";
    private static final String DETAIL_VALUE_GENERAL_XPATH = DETAILS_POPUP_XPATH + "//div[text()='%s']/following::*";
    private static final String MACHINE_TYPE_VALUE_XPATH = DETAIL_VALUE_GENERAL_XPATH.formatted("Machine type");
    private static final String OPERATION_SYSTEM_VALUE_XPATH = DETAIL_VALUE_GENERAL_XPATH.formatted("Operating System / Software");
    private static final String REGION_VALUE_XPATH = DETAIL_VALUE_GENERAL_XPATH.formatted("Region");
    private static final String LOCAL_SSD_VALUE_XPATH = DETAIL_VALUE_GENERAL_XPATH.formatted("Local SSD");
    private static final String COMMITTED_USE_VALUE_XPATH = DETAIL_VALUE_GENERAL_XPATH.formatted("Committed use discount options");


    //Technical locators. Not sure if it is stable
    private static final String TITLE_CSS = "h3.WSeR1e";
    private final String scrollableAreaXpath = "//h3[text() = '%s']/../following-sibling::div";
    private final String lastElementCss = "div.sfENNe:last-child";

//    private WebElement scrollableArea() {
//       retutn getDriver().findElement(By.xpath(scrollableAreaXpath));
//    }

    public String getMachineTypeValue() {
        return getDriver().findElement(By.xpath(MACHINE_TYPE_VALUE_XPATH)).getText();
    }

    public String getInstanceTypeValue() {
        return getDriver().findElement(By.xpath(OPERATION_SYSTEM_VALUE_XPATH)).getText();
    }

    public String getRegionValue() {
        return getDriver().findElement(By.xpath(REGION_VALUE_XPATH)).getText();
    }

    public String getLocalSsdValue() {
        return getDriver().findElement(By.xpath(LOCAL_SSD_VALUE_XPATH)).getText();
    }

    public String getCommittedUseValue() {
        return getDriver().findElement(By.xpath(COMMITTED_USE_VALUE_XPATH)).getText();
    }
//
//    public void findElementInWindow() {
//        Actions actions = new Actions(getDriver());
//        String areaXpath = scrollableAreaXpath.formatted(getDriver().findElement(By.cssSelector(TITLE_CSS)).getText());
//        WebElement scrollableArea = getDriver().findElement(By.xpath(areaXpath));
//        WebElement lastElementOfTheList = getDriver().findElement(By.cssSelector(lastElementCss));
//        actions.moveToElement(scrollableArea);
//        scrollToElement(lastElementOfTheList);
//    }

}

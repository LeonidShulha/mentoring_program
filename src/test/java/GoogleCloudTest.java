import SeleniumTest.Chrome.Components.AddEstimatePopupWindow;
import SeleniumTest.Chrome.Components.EstimateDetailsPopup;
import SeleniumTest.Chrome.Pages.ChromeCloudHomePage;
import SeleniumTest.Chrome.Pages.ChromeCloudSearchResultPage;
import SeleniumTest.Chrome.Pages.ChromeConfigProductPage;
import SeleniumTest.Chrome.Pages.CostDetailsSidePanel;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class GoogleCloudTest extends BaseUiTest {
    private static final String CLOUD_CHROME_URL = "https://cloud.google.com/";
    private static final String CLOUD_PRICING_CALCULATOR_URL = "https://cloud.google.com/products/calculator";
    private static final String INSTANCES = "Instances";

    @SneakyThrows
    @Test
    public void testGoogleCloudPricingCalculator() {
        getDriver().get(CLOUD_CHROME_URL);
        new ChromeCloudHomePage().performSearchOnCloudChrome("Google Cloud Platform Pricing Calculator");
        new ChromeCloudSearchResultPage().clickSearchResultItem();
        CostDetailsSidePanel sidePanel = new CostDetailsSidePanel();
        sidePanel.addToEstimateButtonClick();
        AddEstimatePopupWindow addEstimatePopupWindow = new AddEstimatePopupWindow();
        addEstimatePopupWindow.checkPopupWindowOpened();
        addEstimatePopupWindow.addEstimateElementClick("Compute Engine");
        ChromeConfigProductPage configProductTab = new ChromeConfigProductPage();
        String productTitle = configProductTab.getProductTitle();
        configProductTab.setNumberOfInstances(4);
        configProductTab.setAdvancedSettingToggle(true);
        configProductTab.setAddGpuToggle(true);
        configProductTab.getServiceType().expandDropdown().selectValueFromDropdown("Instances");
        configProductTab.getMachineType().expandDropdown().selectValueFromDropdown("n1-standard-8");
        configProductTab.getOperatingSystem().expandDropdown().selectValueFromDropdown("Free: Debian, CentOS, CoreOS, Ubuntu or BYOL (Bring Your Own License)");
        configProductTab.getNumberOfGpus().expandDropdown().selectValueFromDropdown("1");
        configProductTab.getGpuModel().expandDropdown().selectValueFromDropdown("NVIDIA Tesla V100");
        configProductTab.getLocalSsd().expandDropdown().selectValueFromDropdown("2x375 GB");
        configProductTab.getRegion().expandDropdown().selectValueFromDropdown("Oregon");
        configProductTab.setCommittedUsage("1 year");
        String actualSidePanelTotalCost = sidePanel.getTotalEstimatedCost();
        String actualProductCalculatedPrice = configProductTab.getProductCalculatedPrice();
        String actualSingleItemCost = sidePanel.getProductDetailsSingleItem(INSTANCES).getItemCost();
        sidePanel.getProductDetailsSingleItem(INSTANCES).openProductMenuOptions().openProductDetailsWindow();
        EstimateDetailsPopup estimateDetailsPopup = new EstimateDetailsPopup();
        String actualMachineType = estimateDetailsPopup.getMachineTypeValue();
        String actualInstanceType = estimateDetailsPopup.getInstanceTypeValue();
        String actualRegion = estimateDetailsPopup.getRegionValue();
        String actualLocalSsd = estimateDetailsPopup.getLocalSsdValue();
        String commitmentTerm = estimateDetailsPopup.getCommittedUseValue();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualSidePanelTotalCost, "$8,593.19", "Side panel total cost is incorrect");
        softAssert.assertEquals(actualProductCalculatedPrice, "$8,593.19", "Product calculated price is incorrect");
        softAssert.assertEquals(actualSingleItemCost, "$8,593.19", "Single item cost is incorrect");
        softAssert.assertEquals(actualMachineType, "n1-standard-8, vCPUs: 8, RAM: 30 GB", "Machine type value is incorrect");
        softAssert.assertEquals(actualInstanceType, "Free: Debian, CentOS, CoreOS, Ubuntu or BYOL (Bring Your Own License)", "Instance type is incorrect");
        softAssert.assertEquals(actualRegion, "Oregon (us-west1)", "Region value is incorrect");
        softAssert.assertEquals(actualLocalSsd, "2x375 GB", "Local SSD value is incorrect");
        softAssert.assertEquals(commitmentTerm, "1 year", "Commitment term is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void navigateBetweenTabs() {
        getDriver().navigate().to(CLOUD_PRICING_CALCULATOR_URL);
        CostDetailsSidePanel sidePanel = new CostDetailsSidePanel();
        sidePanel.addToEstimateButtonClick();
        AddEstimatePopupWindow addEstimatePopupWindow = new AddEstimatePopupWindow();
        addEstimatePopupWindow.checkPopupWindowOpened();
        addEstimatePopupWindow.addEstimateElementClick("Compute Engine");
        ChromeConfigProductPage configProductTab = new ChromeConfigProductPage();
        String productTitle = configProductTab.getProductTitle();
        String cost = sidePanel.getTotalEstimatedCost();
        String detailsLink = sidePanel.getDetailedViewLink();
        // I decide do not create Separate PO because I'm lazy
        String tab_1 = getDriver().getWindowHandle();
        getDriver().switchTo().newWindow(WindowType.TAB).navigate().to(detailsLink); // Click add new tab and paste URL
        Assert.assertTrue(getDriver().findElement(By.xpath("//div/h4[text()='Cost Estimate Summary']")).isDisplayed(), "Page title is not loaded");
        getDriver().close();// close tab 2
        getDriver().switchTo().window(tab_1);
        sidePanel.getOpenDetailedView().click(); // Click Open Detailed view
        seleniumUtils.navigateToTab(1);// Get already opened tab
        Assert.assertTrue(getDriver().findElement(By.xpath("//div/h4[text()='Cost Estimate Summary']")).isDisplayed(), "Page title is not loaded");
    }
}

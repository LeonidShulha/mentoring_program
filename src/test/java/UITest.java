
import SeleniumTest.Chrome.Components.AddEstimatePopupWindow;
import SeleniumTest.Chrome.Components.EstimateDetailsPopup;
import SeleniumTest.Chrome.Pages.ChromeCloudHomePage;
import SeleniumTest.Chrome.Pages.ChromeCloudSearchResultPage;
import SeleniumTest.Chrome.Pages.ChromeConfigProductPage;
import SeleniumTest.Chrome.Pages.CostDetailsSidePanel;
import SeleniumTest.PasteBin.Pages.PasteBinPage;
import SeleniumTest.PasteBin.Pages.SubmittedPastePage;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static SeleniumTest.ThreadLocalDriver.getDriver;
import static SeleniumTest.ThreadLocalDriver.killDriver;

@Log
public class UITest {
    private static final String PASTE_BIN_URL = "https://pastebin.com";
    private static final String CLOUD_CHROME_URL = "https://cloud.google.com/";
    private static final String CLOUD_PRICING_CALCULATOR_URL = "https://cloud.google.com/products/calculator";
    private static final String INSTANCES = "Instances";

    @BeforeMethod
    public void webDriverManager() {
        getDriver();
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("window.focus();");
    }

    //Skipped as https://pastebin.com is very unstable and has a lot of banners
    @Test(enabled = false)
    public void iCanWin() {
        getDriver().get(PASTE_BIN_URL);
        PasteBinPage pasteBinPage = new PasteBinPage();
        pasteBinPage.setNewPasteInputFieldText("Hello from WebDriver");
        pasteBinPage.setPasteExpiration("10 Minutes");
        pasteBinPage.setNameInputField("helloweb");
        pasteBinPage.savePaste();
    }

    //Skipped as https://pastebin.com is very unstable and has a lot of banners
    @SneakyThrows
    @Test(enabled = false)
    public void bringItOn() {
        getDriver().get(PASTE_BIN_URL);
        Path filePath = Paths.get("src/main/resources/bringItOn");
        String content = new String(Files.readAllBytes(filePath));
        PasteBinPage pasteBinPage = new PasteBinPage();
        pasteBinPage.setNewPasteInputFieldText(content);
        pasteBinPage.setPasteSyntaxHighlight("Bash");
        pasteBinPage.setPasteExpiration("10 Minutes");
        pasteBinPage.setNameInputField("how to gain dominance among developers");
        pasteBinPage.savePaste();
        SubmittedPastePage submittedPastePage = new SubmittedPastePage();
        Assert.assertEquals(submittedPastePage.getSubmittedPasteTitle(), "how to gain dominance among developers");
        Assert.assertEquals(submittedPastePage.getSubmittedPasteTextHighlightType(), "Bash");
        Assert.assertEquals(submittedPastePage.getSubmittedPasteText(), content);
    }

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
    public void navigateBetweenTabs(){
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
        getDriver().switchTo().window(getDriver().getWindowHandles().stream()
                .skip(1) // skip the first window handle
                .findFirst() // get the second window handle
                .orElseThrow(() -> new NoSuchElementException("No second window found")));
        Assert.assertTrue(getDriver().findElement(By.xpath("//div/h4[text()='Cost Estimate Summary']")).isDisplayed(), "Page title is not loaded");




    }
    @AfterMethod
    public void closeDriver() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(10).toMillis());
        killDriver();
    }
}


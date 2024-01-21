
import SeleniumTest.Chrome.Components.AddEstimatePopupWindow;
import SeleniumTest.Chrome.Components.EstimateDetailsPopup;
import SeleniumTest.Chrome.Pages.ChromeCloudHomePage;
import SeleniumTest.Chrome.Pages.ChromeCloudSearchResultPage;
import SeleniumTest.Chrome.Pages.ChromeConfigProductPage;
import SeleniumTest.Chrome.Pages.CostDetailsSidePanel;
import SeleniumTest.PasteBin.Pages.PasteBinPage;
import SeleniumTest.PasteBin.Pages.SubmittedPastePage;
import SeleniumTest.Utils.SeleniumUtils;
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
public abstract class BaseUiTest {
    SeleniumUtils seleniumUtils = new SeleniumUtils();

    @BeforeMethod
    public void webDriverManager() {
        getDriver();
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("window.focus();");
    }



    @AfterMethod
    public void closeDriver() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(10).toMillis());
        killDriver();
    }
}


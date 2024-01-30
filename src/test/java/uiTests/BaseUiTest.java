package uiTests;

import SeleniumTest.Utils.SeleniumUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import static SeleniumTest.ThreadLocalDriver.getDriver;
import static SeleniumTest.ThreadLocalDriver.killDriver;


public abstract class BaseUiTest {
    SeleniumUtils seleniumUtils = new SeleniumUtils();

    @BeforeMethod
    public void webDriverManager() {
        getDriver();
    }

    @AfterMethod
    public void closeDriver() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(10).toMillis());
        killDriver();
    }
}

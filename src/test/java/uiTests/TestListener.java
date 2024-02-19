package uiTests;

import SeleniumTest.Utils.JavaScriptExecutorUtils;
import com.epam.reportportal.listeners.LogLevel;
import com.epam.reportportal.service.ReportPortal;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Calendar;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class TestListener implements ITestListener {
    private static final String SCRN_DIRECTORY = "target/screenshots/";
    private static final String SCRN_NAME_TEMPLATE = "screenshot_%s_%s.jpg";
    JavaScriptExecutorUtils javaScriptExecutorUtils = new JavaScriptExecutorUtils();

    @Override
    @SneakyThrows
    public void onTestFailure(ITestResult result) {
        String filePath = SCRN_DIRECTORY + SCRN_NAME_TEMPLATE.formatted(result.getMethod().getMethodName(), LocalDateTime.now());
        if (result.getThrowable() instanceof ElementNotInteractableException) {
            String failedElement = getLocatorFromExceptionMessage(result.getThrowable().getMessage());
            WebElement element = getDriver().findElement(By.xpath(failedElement));
            javaScriptExecutorUtils.highlightElement(element);
        }
        File screenshotData = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        ReportPortal.emitLog("Screenshot on failure", LogLevel.ERROR.toString(), Calendar.getInstance().getTime(), screenshotData);
    }

    private String getLocatorFromExceptionMessage(String message) {
        String[] parts = message.split("xpath: |css: ");
        String[] raw = parts[1].split("]\nSession ID");
        return raw[0];
    }
}

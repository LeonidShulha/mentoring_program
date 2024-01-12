package SeleniumTest.Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class JavaScriptExecutorUtils {

    public void closeViaJsExecutor(String locator){
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        try {
            Path filePath = Paths.get("src/main/resources/JsScriptToCloseModal.js");
            String content = new String(Files.readAllBytes(filePath)).formatted(locator);
            executor.executeScript(content);
        } catch (Exception e) {
            System.out.println("Element with locator '%s' is not found");
        }
    }
    public WebElement scrollToElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, 0)");
        while (!element.isEnabled()) {
            jse.executeScript("arguments[0].scrollIntoView(true);", element);
        }
        return element;
    }

    public WebElement scrollToElementInScrollableArea(WebElement scrollableAreaElement, WebElement targetElement) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        while (!targetElement.isEnabled()) {
            jse.executeScript("arguments[0].scrollTop = arguments[1].offsetTop;", scrollableAreaElement, targetElement);
        }
        return targetElement;
    }
}

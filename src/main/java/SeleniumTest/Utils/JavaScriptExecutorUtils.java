package SeleniumTest.Utils;

import lombok.SneakyThrows;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static SeleniumTest.ThreadLocalDriver.getDriver;


public class JavaScriptExecutorUtils {

    public void closeViaJsExecutor(String locator) {
        try {
            Path filePath = Paths.get("src/main/resources/JsScriptToCloseModal.js");
            String content = new String(Files.readAllBytes(filePath)).formatted(locator);
            getExecutor().executeScript(content);
        } catch (Exception e) {
            System.out.println("Element with locator '%s' is not found".formatted(locator));
        }
    }

    @SneakyThrows
    public WebElement scrollToElement(WebElement element) {
        getExecutor().executeScript("window.scrollTo(0, 0)");
        while (!element.isEnabled()) {
            getExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
        }
        Thread.sleep(Duration.ofSeconds(3).toMillis());
        return element;
    }

    public WebElement scrollToElementInScrollableArea(WebElement scrollableAreaElement, WebElement targetElement) {
        while (!targetElement.isEnabled()) {
            getExecutor().executeScript("arguments[0].scrollTop = arguments[1].offsetTop;", scrollableAreaElement, targetElement);
        }
        return targetElement;
    }

    public void highlightElement(WebElement element) {
        getExecutor().executeScript("var ele=arguments[0]; setTimeout(function() { ele.style.border='3px solid red'; }, 500)", element);
    }

    private JavascriptExecutor getExecutor() {
        return (JavascriptExecutor) getDriver();
    }
}

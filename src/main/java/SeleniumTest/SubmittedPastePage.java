package SeleniumTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SubmittedPastePage {
    WebDriver driver;
    private static final String SUBMITTED_PASTE_TITLE_XPATH = "//div[@class = 'info-bar']//h1";
    private static final String SUBMITTED_PASTE_TEXT_HIGHLIGHT_TYPE_XPATH = "//div[@class='highlighted-code']//div[@class='left']/a";
    private static final String SUBMITTED_PASTE_TEXT_XPATH = "//div[@class='highlighted-code']//ol";

    public SubmittedPastePage(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement submittedPasteTitle(){
        return driver.findElement(By.xpath(SUBMITTED_PASTE_TITLE_XPATH));
    }

    private WebElement submittedPasteTextHighlightType(){
        return driver.findElement(By.xpath(SUBMITTED_PASTE_TEXT_HIGHLIGHT_TYPE_XPATH));
    }

    private WebElement submittedPasteText(){
        return driver.findElement(By.xpath(SUBMITTED_PASTE_TEXT_XPATH));
    }

    public String getSubmittedPasteTitle(){
        return submittedPasteTitle().getText();
    }
    public String getSubmittedPasteTextHighlightType(){
        return submittedPasteTextHighlightType().getText();
    }
    public String getSubmittedPasteText() {
        return submittedPasteText().getText();
    }
}

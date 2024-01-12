package SeleniumTest.PasteBin.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class SubmittedPastePage {

    private static final String SUBMITTED_PASTE_TITLE_XPATH = "//div[@class = 'info-bar']//h1";
    private static final String SUBMITTED_PASTE_TEXT_HIGHLIGHT_TYPE_XPATH = "//div[@class='highlighted-code']//div[@class='left']/a";
    private static final String SUBMITTED_PASTE_TEXT_XPATH = "//div[@class='highlighted-code']//ol";


    private WebElement submittedPasteTitle() {
        return getDriver().findElement(By.xpath(SUBMITTED_PASTE_TITLE_XPATH));
    }

    private WebElement submittedPasteTextHighlightType() {
        return getDriver().findElement(By.xpath(SUBMITTED_PASTE_TEXT_HIGHLIGHT_TYPE_XPATH));
    }

    private WebElement submittedPasteText() {
        return getDriver().findElement(By.xpath(SUBMITTED_PASTE_TEXT_XPATH));
    }

    public String getSubmittedPasteTitle() {
        return submittedPasteTitle().getText();
    }

    public String getSubmittedPasteTextHighlightType() {
        return submittedPasteTextHighlightType().getText();
    }

    public String getSubmittedPasteText() {
        return submittedPasteText().getText();
    }
}

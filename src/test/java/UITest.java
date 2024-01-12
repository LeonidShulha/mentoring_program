import SeleniumTest.Driver;
import SeleniumTest.PasteBinPage;
import SeleniumTest.SubmittedPastePage;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static SeleniumTest.Driver.getDriver;


public class UITest {
    private static final String URL = "https://pastebin.com";

    @BeforeMethod
    public void webDriverManager() {
        getDriver().get("https://google.com");
    }

    @Test
    public void iCanWin() {
        getDriver().get(URL);
        PasteBinPage pasteBinPage = new PasteBinPage(getDriver());
        pasteBinPage.closeBanners();
        pasteBinPage.setNewPasteInputFieldText("Hello from WebDriver");
        pasteBinPage.setPasteExpiration("10 Minutes");
        pasteBinPage.setNameInputField("helloweb");
        pasteBinPage.savePaste();


    }

    @SneakyThrows
    @Test
    public void bringItOn() {
        getDriver().get(URL);
        Path filePath = Paths.get("src/main/resources/bringItOn");
        String content = new String(Files.readAllBytes(filePath));
        PasteBinPage pasteBinPage = new PasteBinPage(getDriver());
        pasteBinPage.closeBanners();
        pasteBinPage.setNewPasteInputFieldText(content);
        pasteBinPage.setPasteSyntaxHighlight("Bash");
        pasteBinPage.setPasteExpiration("10 Minutes");
        pasteBinPage.setNameInputField("how to gain dominance among developers");
        pasteBinPage.savePaste();
        SubmittedPastePage submittedPastePage = new SubmittedPastePage(getDriver());
        Assert.assertEquals(submittedPastePage.getSubmittedPasteTitle(), "how to gain dominance among developers");
        Assert.assertEquals(submittedPastePage.getSubmittedPasteTextHighlightType(), "Bash");
        Assert.assertEquals(submittedPastePage.getSubmittedPasteText(), content);
    }

    @AfterMethod
    public void closeDriver() {
        Driver.killDriver();
    }
}


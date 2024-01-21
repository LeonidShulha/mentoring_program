import SeleniumTest.PasteBin.Pages.PasteBinPage;
import SeleniumTest.PasteBin.Pages.SubmittedPastePage;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public class PasteBinTest extends BaseUiTest {
    private static final String PASTE_BIN_URL = "https://pastebin.com";

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
}

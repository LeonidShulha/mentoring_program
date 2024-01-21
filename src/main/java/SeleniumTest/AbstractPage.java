package SeleniumTest;

import SeleniumTest.Utils.JavaScriptExecutorUtils;
import SeleniumTest.Utils.SeleniumUtils;

import static SeleniumTest.ThreadLocalDriver.getDriver;

public abstract class AbstractPage {
    SeleniumUtils seleniumUtils = new SeleniumUtils();
    public JavaScriptExecutorUtils javaScriptExecutorUtils = new JavaScriptExecutorUtils();

    public AbstractPage() {
        seleniumUtils.getLocatorsRelatedToPage(getDriver().getCurrentUrl())
                .forEach(item -> javaScriptExecutorUtils.closeViaJsExecutor(item));
    }
}

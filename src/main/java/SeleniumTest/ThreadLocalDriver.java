package SeleniumTest;

import framework.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ThreadLocalDriver {
    private static ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal();
    private static boolean isRemoteRun = Boolean.parseBoolean(TestConfig.getProperties().getProperty("remoteRun"));
    private static String browser = TestConfig.getProperties().getProperty("browser");
    private static String gridHubUrl = TestConfig.getProperties().getProperty("urlGrid.Hub");

    @SneakyThrows
    public static WebDriver getDriver() {
        if (threadDriver.get() == null) {
            initDriver();
        }
        return threadDriver.get();
    }

    private static void initDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        if (isRemoteRun) {
            threadDriver.set(new RemoteWebDriver(new URL(gridHubUrl), capabilities));
        } else {
            switch (browser.toLowerCase()) {
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    threadDriver.set(new ChromeDriver(chromeOptions));
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    threadDriver.set(new FirefoxDriver(firefoxOptions));
                }
                case "safari" -> {
                    SafariOptions safariOptions = new SafariOptions();
                    WebDriverManager.safaridriver().setup();
                    threadDriver.set(new SafariDriver(safariOptions));
                }
                default -> throw new IllegalArgumentException("Invalid browser property set: " + browser);
            }
        }
        threadDriver.get().manage().window().maximize();
        threadDriver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        threadDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        threadDriver.get().manage().timeouts().setScriptTimeout(Duration.ofSeconds(10));
    }

    public static void killDriver() {
        if (threadDriver.get() != null) {
//            ((WebDriver) threadDriver.get()).close();
            threadDriver.get().quit();
            threadDriver.remove();
        }
    }
}

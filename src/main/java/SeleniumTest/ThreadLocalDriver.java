package SeleniumTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.time.Duration;

public class ThreadLocalDriver {
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal();

    public ThreadLocalDriver() {
    }

    public static WebDriver getDriver() {
        if (threadDriver.get() == null) {
            initDriver();
        }

        return (WebDriver) threadDriver.get();
    }

    private static void initDriver() {
        String browser = System.getProperty("browser", "chrome");
        byte var2 = -1;
        switch (browser.hashCode()) {
            case -1361128838:
                if (browser.equals("chrome")) {
                    var2 = 0;
                }
                break;
            case -849452327:
                if (browser.equals("firefox")) {
                    var2 = 1;
                }
                break;
            case 2332:
                if (browser.equals("IE")) {
                    var2 = 3;
                }
                break;
            case 3108285:
                if (browser.equals("edge")) {
                    var2 = 2;
                }
                break;
            case 105948115:
                if (browser.equals("opera")) {
                    var2 = 4;
                }
        }

        switch (var2) {
            case 0:
                WebDriverManager.chromedriver().setup();
                threadDriver.set(new ChromeDriver());

                break;
            case 1:
                WebDriverManager.firefoxdriver().setup();
                threadDriver.set(new FirefoxDriver());
                break;
            case 2:
                WebDriverManager.edgedriver().setup();
                threadDriver.set(new EdgeDriver());
                break;
            case 3:
                WebDriverManager.iedriver().setup();
                threadDriver.set(new InternetExplorerDriver());
                break;
            default:
                throw new IllegalArgumentException("Entered browser value is not recognized");
        }

        ((WebDriver) threadDriver.get()).manage().window().maximize();
        ((WebDriver) threadDriver.get()).manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        ((WebDriver) threadDriver.get()).manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        ((WebDriver) threadDriver.get()).manage().timeouts().setScriptTimeout(Duration.ofSeconds(10));
    }

    public static void killDriver() {
        if (threadDriver.get() != null) {
//            ((WebDriver) threadDriver.get()).close();
            threadDriver.get().quit();
            threadDriver.remove();
        }

    }
}

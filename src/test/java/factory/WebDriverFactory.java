package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    public static WebDriver createInstance(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                throw new UnsupportedOperationException("❌ Firefox is currently not supported for this test.");

            default:
                throw new IllegalArgumentException("❌ Browser not supported: " + browser);
        }


        driver.manage().window().maximize();
        return driver;
    }
}


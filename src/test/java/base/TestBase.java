package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class TestBase {

    protected WebDriver driver;
    public WebDriver getDriver() {
        return driver;
    }
    private static final Logger log = LogManager.getLogger(TestBase.class);

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        log.info("✅ Browser launched and maximized");
    }

    @AfterMethod
    public void captureFailureScreenshot(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String path = "screenshots/" + result.getName() + ".png";
            try {
                FileUtils.copyFile(src, new File(path));
                log.error("❌ Test Failed - Screenshot saved at: {}", path);
            } catch (IOException e) {
                log.error("❌ Failed to save screenshot", e);
            }
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("🧹 Browser closed");
        }
    }
}

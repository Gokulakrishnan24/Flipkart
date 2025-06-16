package base;

import utils.DriverManager;
import factory.WebDriverFactory; // ✅ This was missing 'import'

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;


public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browser) {
        WebDriver driver = WebDriverFactory.createInstance(browser);
        DriverManager.setDriver(driver);
        DriverManager.getDriver().manage().window().maximize();
        log.info("✅ Browser launched: {} and maximized", browser);
    }

    @AfterMethod
    public void captureFailureScreenshot(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
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
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
            log.info("🧹 Browser closed and thread removed");
        }
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}

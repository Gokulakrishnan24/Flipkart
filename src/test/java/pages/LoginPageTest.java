package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPageTest {

    private static final Logger log = LogManager.getLogger(LoginPageTest.class);

    @Test
    public void loginFlipkart() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        log.info("🚀 ChromeDriver launched");

        try {
            driver.get("https://www.flipkart.com/");
            log.info("🌐 Navigated to Flipkart homepage");

            // Close login popup if present
            try {
                WebElement closeBtn = driver.findElement(By.cssSelector("button._2KpZ6l._2doB4z"));
                closeBtn.click();
                log.info("✅ Closed login popup");
            } catch (Exception e) {
                log.warn("⚠️ Login popup not found, proceeding.");
            }

            // Click Login
            driver.findElement(By.xpath("//span[text()='Login']")).click();
            log.info("👤 Clicked on Login");

            // Enter phone number
            driver.findElement(By.xpath("//input[contains(@class, 'r4vIwl')]"))
                    .sendKeys("9003701741");
            log.info("📞 Entered phone number");

            // Validate login URL
            String expectedUrl = "https://www.flipkart.com/account/login?ret=/";
            String actualUrl = driver.getCurrentUrl();
            Assert.assertEquals(actualUrl, expectedUrl, "❌ URL mismatch");
            log.info("✅ URL validated successfully");

        } catch (Exception e) {
            log.error("❌ Exception occurred during login test", e);
            throw e;
        } finally {
            driver.quit();
            log.info("🧹 Browser closed");
        }
    }
}

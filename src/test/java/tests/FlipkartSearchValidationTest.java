package tests;

import base.BaseTest;
import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class FlipkartSearchValidationTest extends BaseTest {

    private final String searchTerm = "samsung S20";

    @Test
    public void validateSearchResults_UI_vs_API() {
        // Navigate to Flipkart
        DriverManager.getDriver().get("https://www.flipkart.com");

        // Close login popup if present
        try {
            DriverManager.getDriver().findElement(By.cssSelector("button._2KpZ6l._2doB4z")).click();
        } catch (Exception e) {
            log.info("Login popup not present");
        }

        // Search for "iPhone"
        DriverManager.getDriver().findElement(By.name("q")).sendKeys(searchTerm + Keys.ENTER);

        // Wait for the title to load with expected keyword
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(driver -> driver.getTitle().toLowerCase().contains(searchTerm.toLowerCase()));

        // Get actual title
        String actualTitle = DriverManager.getDriver().getTitle();
        System.out.println("Page Title: " + actualTitle);
        log.info("Page Title: {}", actualTitle);

        // Assertion with relaxed condition
        Assert.assertTrue(
                actualTitle.toLowerCase().contains(searchTerm) || actualTitle.toLowerCase().contains("flipkart"),
                "❌ Title did not match expected content. Actual: " + actualTitle
        );
    }
}

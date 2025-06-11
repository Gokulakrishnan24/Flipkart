package tests;

import base.TestBase;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FlipkartHomePage;
import java.io.File;
import java.io.IOException;


public class FlipkartTest extends TestBase {

    private static final Logger logger = LogManager.getLogger(FlipkartTest.class);

    @Test
    public void completeFlow() {
        logger.info("Navigating to Flipkart home page...");
        driver.get("https://www.flipkart.com");

        FlipkartHomePage flipkart = new FlipkartHomePage(driver);

        logger.info("Closing login popup if visible...");
        flipkart.loginPopupClose();

        logger.info("Searching for product: iPhone 14");
        flipkart.searchProduct("iPhone 14");

        logger.info("Clicking on the first product from the search result...");
        flipkart.clickFirstProduct();

        logger.info("Switching to new tab for product details...");
        flipkart.switchToNewTab();

        logger.info("Attempting to add the product to cart...");
        flipkart.addToCart();

        logger.info("Validating if the product was added to cart...");
        boolean productAdded = flipkart.isItemInCart();

        if (!productAdded) {
            logger.warn("❌ Product was not added to the cart. Capturing screenshot...");
            captureScreenshot();
        }

        Assert.assertTrue(productAdded,
                "❌ Product was not added to cart. Check if the 'Add to Cart' button was clicked and cart updated.");

        logger.info("✅ Product successfully added to cart.");
    }

    private void captureScreenshot() {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenshots/" + "cart_check.png"));
            logger.info("Screenshot saved to screenshots/{}", "cart_check.png");
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
        }
    }
}


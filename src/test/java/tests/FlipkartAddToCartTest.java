package tests;

import base.BaseTest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FlipkartHomePage;
import utils.DriverManager;

import java.io.File;
import java.io.IOException;

public class FlipkartAddToCartTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(FlipkartAddToCartTest.class);

    @Test
    public void completeFlow() throws InterruptedException {
        logger.info("Navigating to Flipkart home page...");
        DriverManager.getDriver().get("https://www.flipkart.com");

        FlipkartHomePage flipkart = new FlipkartHomePage(DriverManager.getDriver());

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

        logger.info("Checking if item is added to cart...");
        boolean isInCart = flipkart.isItemInCart();

        if (!isInCart) {
            logger.warn("❌ Product was not added to the cart. Capturing screenshot...");
            captureScreenshot("cart_check");
        }

        Assert.assertTrue(isInCart,
                "❌ Product was not added to cart. Check if the 'Add to Cart' button was clicked and cart updated.");

        logger.info("✅ Product successfully added to cart.");
    }

    private void captureScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File screenshot = ts.getScreenshotAs(OutputType.FILE);

            File dir = new File("screenshots");
            if (!dir.exists()) dir.mkdirs();

            File destination = new File(dir, fileName + ".png");
            FileUtils.copyFile(screenshot, destination);

            logger.info("Screenshot saved to {}", destination.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
        }
    }
}

package tests;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.FlipkartHomePage;
import drivers.DriverManager;
import utils.ExtentLogger;
import java.io.File;
import java.io.IOException;

@Listeners(listeners.TestListener.class)
public class FlipkartAddToCartTest extends BaseTest {

    @Test
    public void completeFlow() {
        try {
            ExtentLogger.info("Navigating to Flipkart home page...");
            DriverManager.getDriver().get("https://www.flipkart.com");

            FlipkartHomePage flipkart = new FlipkartHomePage(DriverManager.getDriver());

            ExtentLogger.info("Closing login popup if visible...");
            flipkart.loginPopupClose();

            ExtentLogger.info("Searching for product: iPhone 14");
            flipkart.searchProduct("iPhone 14");

            ExtentLogger.info("Clicking on the first product from the search result...");
            flipkart.clickFirstProduct();

            ExtentLogger.info("Switching to new tab for product details...");
            flipkart.switchToNewTab();

            ExtentLogger.info("Attempting to add the product to cart...");
            flipkart.addToCart();

            ExtentLogger.info("Checking if item is added to cart...");
            boolean isInCart = flipkart.isItemInCart();

            if (!isInCart) {
                captureScreenshot("cart_check");
                ExtentLogger.attachScreenshot("screenshots/cart_check.png", "❌ Product not added to cart");
            }

            Assert.assertTrue(isInCart, "❌ Product was not added to cart.");
            ExtentLogger.pass("✅ Product successfully added to cart.");

        } catch (Exception e) {
            captureScreenshot("exception");
            ExtentLogger.attachScreenshot("screenshots/exception.png", "❌ Exception occurred during test");
            ExtentLogger.fail("❌ Test failed: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    private void captureScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dir = new File("screenshots");
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(dir, fileName + ".png");
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            ExtentLogger.fail("❌ Screenshot capture failed: " + e.getMessage());
        }
    }
}

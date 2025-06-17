package tests;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FlipkartHomePage;
import utils.DriverManager;
import java.io.File;
import java.io.IOException;
import listeners.RetryListener;
import listeners.TestListener;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class, RetryListener.class})
public class FlipkartAddToCartTest extends BaseTest {

    @Test
    public void completeFlow() {
        test = extent.createTest("Flipkart Add to Cart Flow");
        try {
            test.info("Navigating to Flipkart home page...");
            DriverManager.getDriver().get("https://www.flipkart.com");

            FlipkartHomePage flipkart = new FlipkartHomePage(DriverManager.getDriver());

            test.info("Closing login popup if visible...");
            flipkart.loginPopupClose();

            test.info("Searching for product: iPhone 14");
            flipkart.searchProduct("iPhone 14");

            test.info("Clicking on the first product from the search result...");
            flipkart.clickFirstProduct();

            test.info("Switching to new tab for product details...");
            flipkart.switchToNewTab();

            test.info("Attempting to add the product to cart...");
            flipkart.addToCart();

            test.info("Checking if item is added to cart...");
            boolean isInCart = flipkart.isItemInCart();

            if (!isInCart) {
                test.warning("❌ Product was not added to the cart. Capturing screenshot...");
                captureScreenshot("cart_check");
            }

            Assert.assertTrue(isInCart,
                    "❌ Product was not added to cart. Check if the 'Add to Cart' button was clicked and cart updated.");

            test.pass("✅ Product successfully added to cart.");

        } catch (Exception e) {
            test.fail("❌ Test execution failed: " + e.getMessage());
            captureScreenshot("exception");
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    private void captureScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File screenshot = ts.getScreenshotAs(OutputType.FILE);

            File dir = new File("screenshots");
            if (!dir.exists()) dir.mkdirs();

            File destination = new File(dir, fileName + ".png");
            FileUtils.copyFile(screenshot, destination);

            test.addScreenCaptureFromPath(destination.getAbsolutePath());
            test.info("📸 Screenshot saved to: " + destination.getAbsolutePath());
        } catch (IOException e) {
            test.info("❌ Failed to capture screenshot: " + e.getMessage());
        }
    }
}

package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;

public class FlipkartHomePage {

    private static final Logger log = LogManager.getLogger(FlipkartHomePage.class);
    WebDriver driver;

    public FlipkartHomePage(WebDriver driver) {
        this.driver = driver;
    }

    By loginPopupClose = By.cssSelector("button._2KpZ6l._2doB4z");
    By searchInput = By.name("q");
    By firstProduct = By.className("KzDlHZ");
    By cartTotalCount = By.className("p6sArZ"); // or your correct selector
    By addToCartBtn = By.className("NwyjNT");

    public void loginPopupClose() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(loginPopupClose)).click();
            log.info("✅ Login popup closed.");
        } catch (TimeoutException e) {
            log.warn("⚠️ Login popup not found or not clickable.");
        } catch (Exception e) {
            log.error("❌ Unexpected error while closing login popup", e);
        }
    }

    public void searchProduct(String productName) {
        try {
            driver.findElement(searchInput).sendKeys(productName);
            driver.findElement(searchInput).submit();
            log.info("🔍 Searched for product: {}", productName);
        } catch (Exception e) {
            log.error("❌ Failed to search product: {}", productName, e);
            throw e;
        }
    }

    public void clickFirstProduct() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
            log.info("🛒 Clicked on the first product in search results.");
        } catch (Exception e) {
            log.error("❌ Failed to click on the first product", e);
            throw e;
        }
    }

    public void switchToNewTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            log.info("🪟 Switched to new tab.");
        } catch (Exception e) {
            log.error("❌ Failed to switch to the new tab", e);
            throw e;
        }
    }

    public void addToCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
            log.info("🧺 Clicked 'Add to Cart' button.");
        } catch (Exception e) {
            log.error("❌ Failed to click 'Add to Cart' button.", e);
            throw new RuntimeException("❌ Could not add item to cart.");
        }
    }

    public boolean isItemInCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotalCount));
            String countText = cartCount.getText();
            boolean inCart = !countText.equals("0");
            log.info("🛒 Cart item check: {} items in cart.", countText);
            return inCart;
        } catch (Exception e) {
            log.warn("⚠️ Failed to verify cart item count.", e);
            return false;
        }
    }
}

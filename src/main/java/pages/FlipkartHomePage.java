package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;
import java.util.List;

public class FlipkartHomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ✅ Constructor
    public FlipkartHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ✅ Locators
    private By loginPopupCloseBtn = By.cssSelector("button._2KpZ6l._2doB4z");
    private By searchBox = By.name("q");
    private By searchBtn = By.cssSelector("button[type='submit']");
    private By productTitles = By.className("tUxRFH");

    private By addToCartBtn = By.xpath("//button[contains(text(),'Add to cart')]");
    private By cartIcon = By.cssSelector("a[title='Cart']"); // Adjust if needed
    private By cartItem = By.cssSelector("a._2Kn22P.gBNbID"); // Or actual cart item selector

    // ✅ Actions
    public void loginPopupClose() {
        try {
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(loginPopupCloseBtn));
            closeBtn.click();
        } catch (Exception e) {
            System.out.println("Login popup not displayed or already closed.");
        }
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).sendKeys(productName);
        driver.findElement(searchBtn).click();
    }

    public void clickFirstProduct() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(20));
        WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tUxRFH")));
        firstProduct.click();
    }


    public void switchToNewTab() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    public void addToCart() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement addToCartBtn = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[contains(text(),'Add to cart') or contains(text(),'ADD TO CART')]")));
        addToCartBtn.click();
        Thread.sleep(2000); // or better: wait for a cart-specific element

    }



    public boolean isItemInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            WebElement placeOrderBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(text(),'Place Order')]")));
            return placeOrderBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
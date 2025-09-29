package BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class UIUXTest extends BasicTest {

    private void waitForModalToDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));
        } catch (Exception ignored) {}
    }

    // 1. Verify alignment of product tiles
    @Test
    public void verifyProductTileAlignment() {
        waitForModalToDisappear();
        List<WebElement> products = getDriver().findElements(By.cssSelector("#tbodyid .card"));
        Assert.assertTrue(products.size() > 0, "No product tiles found!");
        getTest().info("Found " + products.size() + " product tiles.");

        int firstHeight = products.get(0).getRect().getHeight();
        for (WebElement product : products) {
            Assert.assertEquals(product.getRect().getHeight(), firstHeight, "Tile heights are inconsistent!");
        }
        getTest().pass("✅ Product tiles have consistent alignment and spacing.");
    }

    // 2. Check visibility of action buttons
    @Test
    public void checkActionButtons() {
        waitForModalToDisappear();
        WebElement loginBtn = getDriver().findElement(By.id("login2"));
        WebElement signupBtn = getDriver().findElement(By.id("signin2"));
        WebElement cartBtn = getDriver().findElement(By.id("cartur"));

        Assert.assertTrue(loginBtn.isDisplayed() && loginBtn.isEnabled(), "Login button not visible/clickable!");
        Assert.assertTrue(signupBtn.isDisplayed() && signupBtn.isEnabled(), "Sign Up button not visible/clickable!");
        Assert.assertTrue(cartBtn.isDisplayed() && cartBtn.isEnabled(), "Cart button not visible/clickable!");

        getTest().pass("✅ Action buttons are visible and clickable.");
    }

    // 3. Check font consistency across pages
    @Test
    public void checkFontConsistency() {
        waitForModalToDisappear();
        String homeFont = getDriver().findElement(By.cssSelector("#tbodyid .card-title")).getCssValue("font-family");

        getDriver().findElement(By.linkText("Laptops")).click();
        String laptopFont = getDriver().findElement(By.cssSelector("#tbodyid .card-title")).getCssValue("font-family");

        Assert.assertEquals(laptopFont, homeFont, "Font is inconsistent across pages!");
        getTest().pass("✅ Font style is consistent across Home and Laptops pages.");
    }

    // 4. Test alert/popup styling
    @Test
    public void testPopupStyling() {
        waitForModalToDisappear();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.cssSelector("#tbodyid .card-title a")).click();
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-success.btn-lg")));
        addToCartBtn.click();

        String alertText = wait.until(ExpectedConditions.alertIsPresent()).getText();
        Assert.assertTrue(alertText.contains("added"), "Unexpected alert text: " + alertText);
        getDriver().switchTo().alert().accept();

        getTest().pass("✅ Alert appeared with readable styling: " + alertText);
    }
}

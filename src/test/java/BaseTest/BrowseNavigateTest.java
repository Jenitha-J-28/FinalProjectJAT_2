package BaseTest;

import Pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class BrowseNavigateTest extends BasicTest {
    LoginPage login;

    @Test
    public void verifyLaptopProducts() { browseCategory("Laptops"); }
    @Test public void verifyPhoneProducts() { browseCategory("Phones"); }
    @Test public void verifyMonitorProducts() { browseCategory("Monitors"); }

    private void browseCategory(String category) {
        login = new LoginPage(getDriver());
        login.doLogin("jenitha@gmail.com", "abcdefg");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        waitForModalToDisappear();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(category))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tbodyid .card-title")));

        List<WebElement> products = getDriver().findElements(By.cssSelector("#tbodyid .card-title"));
        Assert.assertTrue(products.size() > 0, "No products found for category: " + category);

        getTest().info("Products in " + category + ": " + products.stream().map(WebElement::getText).toList());
    }

    private void waitForModalToDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));
        } catch (Exception ignored) {}
    }
}

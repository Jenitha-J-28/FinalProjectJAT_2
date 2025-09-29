package BaseTest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import Pages.CartPage;
import Pages.LoginPage;

public class CartTest extends BasicTest {
    CartPage cart;
    LoginPage login;

    @Test(priority = 1)
    public void addAndViewProductToCart() {
        login = new LoginPage(getDriver());
        cart = new CartPage(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));

        login.doLogin("jenitha@gmail.com", "abcdefg");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));

        cart.SelectProductByName(cart.getProductName1());
        cart.ClickOnAddToCart();
        String alertMsg = cart.handleAlertIfPresent();
        getTest().info("Alert: " + alertMsg);

        cart.ClickOnCartLink();
        getTest().info("Product name: " + cart.getProductName());
        getTest().info("Product price: " + cart.getProductPrice());
    }

    @Test(priority = 2)
    public void deleteProduct() {
        login = new LoginPage(getDriver());
        cart = new CartPage(getDriver());

        login.doLogin("jenitha@gmail.com", "abcdefg");

        cart.ClickOnCartLink();
        cart.deleteProduct();
        getTest().info("Deleted product. Updated total: " + cart.TotalPrice());
    }

    @Test(priority = 3)
    public void addMultipleProducts() {
        login = new LoginPage(getDriver());
        cart = new CartPage(getDriver());

        login.doLogin("jenitha@gmail.com", "abcdefg");

        // Add first product
        cart.SelectProductByName(cart.getProductName1());
        cart.ClickOnAddToCart();
        getTest().info("Added first product. Alert: " + cart.handleAlertIfPresent());

        // Add second product
        cart.clickOnHomeLink();
        cart.SelectProductByName(cart.getProductName2());
        cart.ClickOnAddToCart();
        getTest().info("Added second product. Alert: " + cart.handleAlertIfPresent());

        cart.ClickOnCartLink();
        getTest().info("Cart products: " + cart.getProductName() + " | Total: " + cart.TotalPrice());
    }
}

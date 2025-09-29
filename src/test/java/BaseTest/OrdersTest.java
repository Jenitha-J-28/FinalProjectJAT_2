package BaseTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import Pages.CartPage;
import Pages.LoginPage;
import Pages.OrdersPage;

public class OrdersTest extends BasicTest {
    OrdersPage orders;
    LoginPage login;
    CartPage cart;

    @Test(priority = 1)
    public void placeOrderWithValidData() {
        login = new LoginPage(getDriver());
        cart = new CartPage(getDriver());
        orders = new OrdersPage(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));

        login.doLogin("jenitha@gmail.com", "abcdefg");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));

        cart.SelectProductByName("Nokia lumia 1520");
        cart.ClickOnAddToCart();
        getTest().info("Alert: " + cart.handleAlertIfPresent());

        cart.ClickOnCartLink();
        orders.doPlaceOrder("Jeni3", "India", "Chennai", "123456787", "September", "2020");

        getTest().info("Order placed successfully. Popup title: " + orders.getOrderPopupTitle());
        getTest().info("Order details: " + orders.getOrderPopupDetails());
        orders.confirmOrder();
    }

    @Test(priority = 2)
    public void placeOrderWithEmptyData() {
        login = new LoginPage(getDriver());
        cart = new CartPage(getDriver());
        orders = new OrdersPage(getDriver());

        login.doLogin("jenitha@gmail.com", "abcdefg");

        cart.SelectProductByName("Samsung galaxy s6");
        cart.ClickOnAddToCart();
        getTest().info("Alert: " + cart.handleAlertIfPresent());

        cart.ClickOnCartLink();
        orders.clickOnPlaceOrder();
        orders.clickOnPurchase();
        getTest().info("Alert on empty order: " + orders.getAlertMessageAndAccept());
    }
}

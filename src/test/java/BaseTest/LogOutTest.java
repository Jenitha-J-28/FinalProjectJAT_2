package BaseTest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;

public class LogOutTest extends BasicTest {
    LoginPage login;

    @Test
    public void verifyLogOut() {
        login = new LoginPage(getDriver());
        login.doLogin("jenitha@gmail.com", "abcdefg");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout2")));
        logoutBtn.click();

        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login2")));
        Assert.assertTrue(loginBtn.isDisplayed(), "Login button not visible after logout");
        getTest().pass("Login button visible after logout");

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("demoblaze.com"), "User not redirected to home page");
        getTest().pass("User redirected to Home Page: " + currentUrl);
    }
}

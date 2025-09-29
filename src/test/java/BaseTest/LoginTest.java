package BaseTest;

import org.testng.annotations.Test;
import Pages.LoginPage;

public class LoginTest extends BasicTest {
    LoginPage login;

    @Test(priority = 1)
    public void loginWithValidData() throws InterruptedException {
        login = new LoginPage(getDriver());
        login.doLogin("jenikarthi928@gmail.com", "abcdefg");

        String loggedInUser = login.getLoggedInUserText();
        getTest().info("Logged in user text: " + loggedInUser);
    }

    @Test(priority = 2)
    public void loginWithIncorrectPswd() {
        login = new LoginPage(getDriver());
        login.clickOnLoginLink();
        login.enterUserName("Jenitha@gmail.com");
        login.enterPassword("Kevin1");
        login.clickOnLoginBtn();

        String alertMsg = login.getAlertMessageAndAccept();
        getTest().info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 3)
    public void loginWithEmptyFields() {
        login = new LoginPage(getDriver());
        login.clickOnLoginLink();
        login.doEmptyLogin();

        String alertMsg = login.getAlertMessageAndAccept();
        getTest().info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 4)
    public void loginWithInvalidEmailFormat() {
        login = new LoginPage(getDriver());
        login.clickOnLoginLink();
        login.enterUserName("Jenitha.com");
        login.enterPassword("Kevin1");
        login.clickOnLoginBtn();

        String alertMsg = login.getAlertMessageAndAccept();
        getTest().info("Alert message displayed: " + alertMsg);
    }
}

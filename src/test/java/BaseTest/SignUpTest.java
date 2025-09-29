package BaseTest;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import Pages.SignUpPage;

import java.time.Duration;

public class SignUpTest extends BasicTest {
    private SignUpPage signUp;
    private WebDriverWait wait;

    private void initWait() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void SignUpWithValidData() {
        signUp = new SignUpPage(getDriver());
        initWait();

        signUp.enterUserName("karthi21@gmail.com");
        signUp.enterPassword("abcdefg");
        signUp.clickOnSignUpBtn();

        String alertMsg = signUp.getAlertMessageAndAccept();
        getTest().info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 2)
    public void SignUpWithExistingUserName() {
        signUp = new SignUpPage(getDriver());
        initWait();

        signUp.enterUserName("Jenitha@gmail.com");
        signUp.enterPassword("Kevin12345");
        signUp.clickOnSignUpBtn();

        String alertMsg = signUp.getAlertMessageAndAccept();
        getTest().info("Alert message displayed: " + alertMsg);
    }

    @Test(priority = 3)
    public void SignUpWithEmptyField() {
        signUp = new SignUpPage(getDriver());
        initWait();

        signUp.doEmptySignUp();

        String alertMsg = signUp.getAlertMessageAndAccept();
        getTest().info("Alert message displayed: " + alertMsg);
    }
}

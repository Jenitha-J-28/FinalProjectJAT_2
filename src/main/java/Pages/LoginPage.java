package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(id = "login2")
    private WebElement loginLink;

    @FindBy(id = "loginusername")
    private WebElement userName;

    @FindBy(id = "loginpassword")
    private WebElement password;

    @FindBy(xpath = "//button[.='Log in']")
    private WebElement loginBtn;

    @FindBy(xpath = "//div[@id='logInModal']//div[@class='modal-footer']//button[1]")
    private WebElement closeBtn;

    @FindBy(xpath = "//div[@id='logInModal']//div[@class='modal-header']//button")
    private WebElement closeIcon;

    @FindBy(id = "nameofuser")
    private WebElement loggedInUser;

    // Actions
    public void clickOnLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    public void enterUserName(String UN) {
        wait.until(ExpectedConditions.visibilityOf(userName)).clear();
        userName.sendKeys(UN);
    }

    public void enterPassword(String pswd) {
        wait.until(ExpectedConditions.visibilityOf(password)).clear();
        password.sendKeys(pswd);
    }

    public void clickOnLoginBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    public void clickOnCancelBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
    }

    public void doLogin(String UN, String pswd) {
        clickOnLoginLink();
        enterUserName(UN);
        enterPassword(pswd);
        clickOnLoginBtn();
    }

    public void doEmptyLogin() {
        wait.until(ExpectedConditions.visibilityOf(userName)).clear();
        wait.until(ExpectedConditions.visibilityOf(password)).clear();
        clickOnLoginBtn();
    }

    public String getLoggedInUserText() {
        wait.until(ExpectedConditions.visibilityOf(loggedInUser));
        String text = loggedInUser.getText();
        System.out.println("Logged in successfully: " + text);
        return text;
    }

    // Alert handler (optional)
    public String getAlertMessageAndAccept() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String msg = alert.getText();
            alert.accept();
            return msg;
        } catch (Exception e) {
            return "";
        }
    }

    // For modal visibility wait (Firefox-safe)
    public WebElement getLoginModalElement() {
        return driver.findElement(By.id("logInModal"));
    }
}

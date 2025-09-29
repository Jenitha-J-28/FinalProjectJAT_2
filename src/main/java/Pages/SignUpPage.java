package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(id = "signin2")
    private WebElement signUpLink;

    @FindBy(id = "sign-username")
    private WebElement userName;

    @FindBy(id = "sign-password")
    private WebElement password;

    @FindBy(xpath = "//div[@class='modal-content']//div[@class='modal-footer']//button[text()='Sign up']")
    private WebElement signUpBtn;

    @FindBy(xpath = "//div[@id='signInModal']//div[@class='modal-content']//div[@class='modal-footer']//button[1]")
    private WebElement closeBtn;

    // Actions
    public void clickOnSignUpLink() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpLink)).click();
    }

    public void enterUserName(String UN) {
        wait.until(ExpectedConditions.visibilityOf(userName)).clear();
        userName.sendKeys(UN);
    }

    public void enterPassword(String PSWD) {
        wait.until(ExpectedConditions.visibilityOf(password)).clear();
        password.sendKeys(PSWD);
    }

    public void clickOnSignUpBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpBtn)).click();
    }

    public void clickOnCloseBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
    }

    public void doEmptySignUp() {
        wait.until(ExpectedConditions.visibilityOf(userName)).clear();
        wait.until(ExpectedConditions.visibilityOf(password)).clear();
        clickOnSignUpBtn();
    }

    // Combined action
    public void signUp(String UN, String PSWD) {
        clickOnSignUpLink();
        enterUserName(UN);
        enterPassword(PSWD);
        clickOnSignUpBtn();
    }

    // Alert handling
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
    public WebElement getSignUpModalElement() {
        return driver.findElement(By.id("signInModal"));
    }
}

package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrdersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public OrdersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(xpath = "//div[@id='page-wrapper']/div//button")
    private WebElement placeOrderBtn;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "country")
    private WebElement countryField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "card")
    private WebElement cardField;

    @FindBy(id = "month")
    private WebElement monthField;

    @FindBy(id = "year")
    private WebElement yearField;

    @FindBy(xpath = "//div[@id='orderModal']//button[contains(text(),'Purchase')]")
    private WebElement purchaseBtn;

    @FindBy(xpath = "//div[@id='orderModal']//button[contains(text(),'Close')]")
    private WebElement closeBtn;

    @FindBy(xpath = "//div[contains(@class,'sweet-alert')]//h2")
    private WebElement popupTitle;

    @FindBy(xpath = "//div[contains(@class,'sweet-alert')]//p")
    private WebElement popupDetails;

    // Actions
    public void doPlaceOrder(String name, String country, String city, String card, String month, String year) {
        clickOnPlaceOrder();
        nameField.sendKeys(name);
        countryField.sendKeys(country);
        cityField.sendKeys(city);
        cardField.sendKeys(card);

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
        monthField.sendKeys(month);
        yearField.sendKeys(year);
        clickOnPurchase();
    }

    public void clickOnPlaceOrder() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderBtn);
        placeOrderBtn.click();
    }

    public void clickOnPurchase() {
        purchaseBtn.click();
    }

    public void confirmOrder() {
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='sa-confirm-button-container']/button")));
        confirmBtn.click();
    }

    // Get popup information
    public String getOrderPopupTitle() {
        return wait.until(ExpectedConditions.visibilityOf(popupTitle)).getText();
    }

    public String getOrderPopupDetails() {
        return wait.until(ExpectedConditions.visibilityOf(popupDetails)).getText();
    }

    // Alert handling for empty order
    public String getAlertMessageAndAccept() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String msg = alert.getText();
        alert.accept();
        return msg;
    }
}

package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final String ProductName1 = "Samsung galaxy s6";
    private final String ProductName2 = "Nokia lumia 1520";

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()='Cart']")
    private WebElement cartLink;

    @FindBy(xpath = "//a[text()='Home ']")
    private WebElement homeLink;

    @FindBy(xpath = "//tbody/tr/td[2]")
    private WebElement addedProductName;

    @FindBy(xpath = "//tbody/tr/td[3]")
    private WebElement addedProductPrice;

    @FindBy(xpath = "//tbody/tr[1]/td[4]/a")
    private WebElement deleteBtn;

    @FindBy(id = "totalp")
    private WebElement totalPrice;

    // Actions
    public void ClickOnCartLink() {
        cartLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
    }

    public void clickOnHomeLink() {
        homeLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
    }

    public void SelectProductByName(String productName) {
        By locator = By.xpath("//a[contains(text(),'" + productName + "')]");
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
        product.click();
    }

    public void ClickOnAddToCart() {
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']")));
        addBtn.click();
    }

    public String getProductName() {
        wait.until(ExpectedConditions.visibilityOf(addedProductName));
        String name = addedProductName.getText();
        System.out.println("Recently Added Product name: " + name);
        return name;
    }

    public String getProductPrice() {
        wait.until(ExpectedConditions.visibilityOf(addedProductPrice));
        String price = addedProductPrice.getText();
        System.out.println("Recently Added Product price: " + price);
        return price;
    }

    public void deleteProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        wait.until(ExpectedConditions.stalenessOf(deleteBtn));
    }

    public String TotalPrice() {
        wait.until(ExpectedConditions.visibilityOf(totalPrice));
        String total = totalPrice.getText();
        System.out.println("Updated Total: " + total);
        return total;
    }

    public String handleAlertIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.alertIsPresent()).accept();
        } catch (Exception ignored) {}
		return ProductName1;
    }

    public WebElement getLoginModalElement() {
        return driver.findElement(By.id("logInModal"));
    }

    public String getProductName1() { return ProductName1; }
    public String getProductName2() { return ProductName2; }
}

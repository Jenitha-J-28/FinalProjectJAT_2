package BaseTest;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ResponsiveTest{
WebDriver driver;

@BeforeClass
public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get("https://www.demoblaze.com");
}


@Test (priority=1)
public void setResponsiveSize() {
	driver.manage().window().setSize(new Dimension(400,464));
}

@Test (priority=2)
public void checkHorizontalScrolling() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    Long scrollWidth = (Long) js.executeScript("return document.documentElement.scrollWidth;");
    Long clientWidth = (Long) js.executeScript("return document.documentElement.clientWidth;");

    System.out.println("Scroll Width: " + scrollWidth + ", Client Width: " + clientWidth);

    if (scrollWidth > clientWidth) {
        throw new AssertionError("❌ Horizontal scrolling is required! Page not responsive.");
    } else {
        System.out.println("✅ Page fits correctly, no horizontal scrolling needed.");
    }
}
@AfterClass
public void tearDown() {
    if (driver != null) {
        driver.quit();
    }

}
}

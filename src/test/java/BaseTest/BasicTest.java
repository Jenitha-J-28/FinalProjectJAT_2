package BaseTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BasicTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ExtentReports extent;

    @BeforeSuite(alwaysRun = true)
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter(
                "C:\\Users\\vicky\\eclipse\\FinalProjectsJAT\\extend_Reports\\FinalProject3.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void openBrowser(@Optional("chrome") String browser, Method method) {
        WebDriver localDriver;

        switch (browser.toLowerCase()) {
            case "chrome": localDriver = new ChromeDriver(); break;
            case "firefox": localDriver = new FirefoxDriver(); break;
            case "edge": localDriver = new EdgeDriver(); break;
            case "safari": localDriver = new SafariDriver(); break;
            default: throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        localDriver.manage().window().maximize();
        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        localDriver.get("https://www.demoblaze.com/");

        driver.set(localDriver);
        ExtentTest extentTest = extent.createTest(method.getName() + " on " + browser);
        test.set(extentTest);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        ExtentTest extentTest = test.get();

        try {
            // Always take screenshot after each test
            String screenshotPath = takeScreenshot(result.getName());
            extentTest.addScreenCaptureFromPath(screenshotPath);

            if (result.getStatus() == ITestResult.FAILURE) {
                extentTest.fail("Test Failed: " + result.getThrowable());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                extentTest.pass("Test Passed");
            } else {
                extentTest.skip("Test Skipped");
            }
        } catch (IOException e) {
            extentTest.warning("Could not attach screenshot: " + e.getMessage());
        }

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
        test.remove();
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Screenshot helper
    public String takeScreenshot(String testName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver.get();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destPath = "screenshots/" + testName + "_" + timestamp + ".png";
        File dest = new File(destPath);
        dest.getParentFile().mkdirs();
        FileHandler.copy(source, dest);
        return dest.getAbsolutePath();
    }

    // Getter for driver
    public WebDriver getDriver() {
        return driver.get();
    }

    // Getter for ExtentTest
    public ExtentTest getTest() {
        return test.get();
    }
}

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class MobileCalculatorTest {
    private static AppiumDriver driver;
    @BeforeAll
    public static void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("deviceName", "Redmi Note 8");
        caps.setCapability("appPackage", "com.google.android.calculator");
        caps.setCapability("appActivity", "com.android.calculator2.Calculator");
        caps.setCapability("automationName", "UiAutomator2");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver(url, caps);
    }
    @Test
    public void testAdd() {
        performCalculation("5", "add", "3", "8");
    }
    @Test
    public void testSub() {
        performCalculation("5", "sub", "3", "2");
    }
    @Test
    public void testMul() {
        performCalculation("5", "mul", "3", "15");
    }
    @Test
    public void testDiv() {
        performCalculation("6", "div", "3", "2");
    }
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void performCalculation(String num1, String operator, String num2, String expectedResult) {
        clickElement("com.google.android.calculator:id/digit_" + num1);
        clickElement("com.google.android.calculator:id/op_" + operator);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By resultLocator = MobileBy.id("com.google.android.calculator:id/result_preview");
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultLocator));

        clickElement("com.google.android.calculator:id/digit_" + num2);
        driver.findElement(MobileBy.id("com.google.android.calculator:id/eq")).click();

        String result = driver.findElement(MobileBy.id("com.google.android.calculator:id/result_final")).getText();
        assertEquals(expectedResult, result);
    }

    private void clickElement(String elementId) {
        driver.findElement(MobileBy.id(elementId)).click();
    }

}
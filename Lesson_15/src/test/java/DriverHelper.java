import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class DriverHelper {
    public static ChromeDriver driver;
    public static WebDriver getWebDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
            driver = new ChromeDriver();
            System.out.println("Test Start");
        }
        return driver;
    }
    protected static void openUrl(String url) {
        driver.get(url);
    }
}
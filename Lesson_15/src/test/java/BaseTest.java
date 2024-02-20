import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.example.DriverHelper.*;
public class BaseTest {
    private static String WB_URL = "https://www.wildberries.ru/";
    WebDriverWait wait = new WebDriverWait(driver, 10);
    public void waitPageLoaded() {
        try {
            Thread.sleep(5000); // Можно изменить на большее или меньшее количество в зависимости от скорости интернета и скорости работы браузера
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @BeforeAll
    public static void setUp() {
        getWebDriver();
        openUrl(WB_URL);
    }
    @AfterAll
    public static void close() {
        System.out.println("Test Close");
        driver.quit();
    }
}

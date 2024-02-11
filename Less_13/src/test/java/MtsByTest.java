import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class MtsByTest {
    private static WebDriver mts;
    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        mts = new ChromeDriver();

        mts.get("https://www.mts.by/");
        mts.manage().window().maximize();

        try {
            WebElement acceptButton = mts.findElement(By.cssSelector(".btn.btn_black.cookie__ok"));
            acceptButton.click();
        } catch (Exception e) {
            System.out.println("Кнопка 'Принять cookie' не найдена. Продолжаем выполнение теста.");
        }
    }
    @Test
    public void testBlockTitle() {
        WebElement blockTitle = mts.findElement(By.xpath("//h2[contains(., 'Онлайн пополнение без комиссии')]"));
        String actualTitle = blockTitle.getText();
        String expectedTitle = "Онлайн пополнение\nбез комиссии";
        assertEquals(expectedTitle, actualTitle, "Заголовок блока не соответствует ожидаемому");
    }
    @Test

    public void checkPaymentLogos() {
        List<WebElement> paymentLogos = mts.findElements(By.xpath("//div[@class='pay__partners']//img"));

        assertFalse(paymentLogos.isEmpty(), "Логотипы отсутствуют");
        assertEquals(6, paymentLogos.size(), "Неверное количество логотипов");

        for (WebElement logo : paymentLogos) {
            assertTrue(logo.isDisplayed(), "Логотип не отображается");
        }
    }
    @Test
    public void testLinkServiceRedirect() {
        By linkLocator = By.cssSelector("a[href*='/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/']");
        WebElement link = mts.findElement(linkLocator);
        link.click();

        String currentUrl = mts.getCurrentUrl();

        String expectedUrl = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";

        assertEquals(expectedUrl, currentUrl, "Ссылка не приводит к ожидаемому URL");
    }
    @Test
    public void checkContinueButton() {
        WebDriverWait wait = new WebDriverWait(mts, Duration.ofSeconds(10));

        WebElement selectNow = mts.findElement(By.xpath("//span[@class='select__now' and contains(text(), 'Услуги связи')]"));
        selectNow.click();

        WebElement phoneInput = mts.findElement(By.id("connection-phone"));
        phoneInput.sendKeys("297777777");

        WebElement sumInput = mts.findElement(By.id("connection-sum"));
        sumInput.sendKeys("100");

        WebElement emailInput = mts.findElement(By.id("connection-email"));
        emailInput.sendKeys("example@gmail.com");

        WebElement continueButton = mts.findElement(By.xpath("//form[@id='pay-connection']//button[@type='submit']"));
        assertTrue(continueButton.isEnabled(), "Кнопка 'Продолжить' не кликабельна");
        continueButton.click();

        WebElement iframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.bepaid-iframe")));
        mts.switchTo().frame(iframeElement);

        System.out.println("Переключение на фрейм выполнено успешно.");
    }

    @AfterEach
    public void setDefault() {
        mts.get("https://www.mts.by/");
    }

    @AfterAll
    public static void tearDown() {
        mts.quit();
    }
}
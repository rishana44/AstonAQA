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
    public void checkContinueButton() {
        WebDriverWait wait = new WebDriverWait(mts, Duration.ofSeconds(60));

        WebElement selectNow = mts.findElement(By.xpath("//span[@class='select__now' and contains(text(), 'Услуги связи')]"));
        selectNow.click();

        WebElement phoneInput = mts.findElement(By.id("connection-phone"));
        String expectedPhone = "297777777";
        phoneInput.sendKeys(expectedPhone);

        WebElement sumInput = mts.findElement(By.id("connection-sum"));
        String expectedSum = "100";
        sumInput.sendKeys(expectedSum);

        WebElement emailInput = mts.findElement(By.id("connection-email"));
        emailInput.sendKeys("example@gmail.com");

        WebElement continueButton = mts.findElement(By.xpath("//form[@id='pay-connection']//button[@type='submit']"));
        assertTrue(continueButton.isEnabled(), "Кнопка 'Продолжить' не кликабельна");

        continueButton.click();

        WebElement iframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.bepaid-iframe")));

        mts.switchTo().frame(iframeElement);

        WebElement actualSumElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='header__payment-amount']")));
        String actualSum = actualSumElement.getText();
        assertEquals(expectedSum + ".00 BYN", actualSum,  "Сумма оплаты не совпадает с ожидаемой");

        WebElement actualPhoneElement = mts.findElement(By.xpath("//p[@class='header__payment-info']"));
        String actualPhoneElementText = actualPhoneElement.getText();
        String actualPhone = actualPhoneElementText.substring(actualPhoneElementText.length() - 9);
        assertEquals(expectedPhone, actualPhone, "Номер телефона не совпадает с ожидаемым");

        WebElement cardNubmerField = mts.findElement(By.xpath("//label[@class='ng-tns-c47-1 ng-star-inserted']"));
        String cardNubmerFieldText = cardNubmerField.getText();
        assertTrue(cardNubmerField.isDisplayed(), "Текст не виден");
        assertEquals("Номер карты", cardNubmerFieldText, "Текст не совпадает");

        WebElement holderField = mts.findElement(By.xpath("//label[@class='ng-tns-c47-3 ng-star-inserted']"));
        String holderFieldText = holderField.getText();
        assertTrue(holderField.isDisplayed(), "Текст не виден");
        assertEquals("Имя держателя (как на карте)", holderFieldText, "Текст не совпадает");

        WebElement periodField = mts.findElement(By.xpath("//label[@class='ng-tns-c47-4 ng-star-inserted']"));
        String periodFieldText = periodField.getText();
        assertTrue(periodField.isDisplayed(), "Текст не виден");
        assertEquals("Срок действия", periodFieldText, "Текст не совпадает");

        WebElement cvcField = mts.findElement(By.xpath("//label[@class='ng-tns-c47-5 ng-star-inserted']"));
        String cvcFieldText = cvcField.getText();
        assertTrue(cvcField.isDisplayed(), "Текст не виден");
        assertEquals("CVC", cvcFieldText, "Текст не совпадает");

        List<WebElement> paymentLogos = mts.findElements(By.xpath("//div[@class='icons-container ng-tns-c47-1']//img"));
        assertFalse(paymentLogos.isEmpty(), "Логотипы отсутствуют");
        assertEquals(5, paymentLogos.size(), "Неверное количество логотипов");

        WebElement sumButton = mts.findElement(By.xpath("//button[@class='colored disabled ng-star-inserted']"));
        String sumButtonText = sumButton.getText();
        assertEquals("Оплатить " + expectedSum + ".00 BYN", sumButtonText, "Сумма оплаты не совпадает с ожидаемой");
    }
    @Test
    public void checkBlankFields() {
        WebDriverWait wait = new WebDriverWait(mts, Duration.ofSeconds(60));

        WebElement selectNow = mts.findElement(By.xpath("//span[@class='select__now' and contains(text(), 'Услуги связи')]"));

        WebElement phoneInput = mts.findElement(By.id("connection-phone"));
        String phoneField = phoneInput.getAttribute("placeholder");
        assertTrue(phoneInput.isDisplayed(), "Текст не виден");
        assertEquals("Номер телефона", phoneField, "Текст не совпадает");

        WebElement sumInput = mts.findElement(By.id("connection-sum"));
        String sumField = sumInput.getAttribute("placeholder");
        assertTrue(sumInput.isDisplayed(), "Текст не виден");
        assertEquals("Сумма", sumField, "Текст не совпадает");

        WebElement emailInput = mts.findElement(By.id("connection-email"));
        String emailField = emailInput.getAttribute("placeholder");
        assertTrue(emailInput.isDisplayed(), "Текст не виден");
        assertEquals("E-mail для отправки чека", emailField, "Текст не совпадает");

        selectNow.click();

        WebElement homeInternet = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='select__option' and text()='Домашний интернет']")));
        homeInternet.click();

        WebElement phoneInternet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("internet-phone")));
        String phoneInternetField = phoneInternet.getAttribute("placeholder");
        assertTrue(phoneInternet.isDisplayed(), "Текст не виден");
        assertEquals("Номер абонента", phoneInternetField, "Текст не совпадает");

        WebElement sumInternet = mts.findElement(By.id("internet-sum"));
        String sumInternetField = sumInternet.getAttribute("placeholder");
        assertTrue(sumInternet.isDisplayed(), "Текст не виден");
        assertEquals("Сумма", sumInternetField, "Текст не совпадает");

        WebElement emailInternet = mts.findElement(By.id("internet-email"));
        String emailInternetField = emailInternet.getAttribute("placeholder");
        assertTrue(emailInternet.isDisplayed(), "Текст не виден");
        assertEquals("E-mail для отправки чека", emailInternetField, "Текст не совпадает");

        selectNow.click();

        WebElement installment = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='select__option' and text()='Рассрочка']")));
        installment.click();

        WebElement billInstalment = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("score-instalment")));
        String billInstalmentField = billInstalment .getAttribute("placeholder");
        assertTrue(billInstalment.isDisplayed(), "Текст не виден");
        assertEquals("Номер счета на 44", billInstalmentField, "Текст не совпадает");

        WebElement sumInstalment = mts.findElement(By.id("instalment-sum"));
        String sumInstalmentField = sumInstalment.getAttribute("placeholder");
        assertTrue(sumInstalment.isDisplayed(), "Текст не виден");
        assertEquals("Сумма", sumInstalmentField, "Текст не совпадает");

        WebElement emailInstalment = mts.findElement(By.id("instalment-email"));
        String emailInstalmentField = emailInstalment.getAttribute("placeholder");
        assertTrue(emailInstalment.isDisplayed(), "Текст не виден");
        assertEquals("E-mail для отправки чека", emailInstalmentField, "Текст не совпадает");

        selectNow.click();

        WebElement debt = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='select__option' and text()='Задолженность']")));
        debt.click();

        WebElement billDebt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("score-arrears")));
        String billDebtField = billDebt .getAttribute("placeholder");
        assertTrue(billDebt.isDisplayed(), "Текст не виден");
        assertEquals("Номер счета на 2073", billDebtField, "Текст не совпадает");

        WebElement sumDebt= mts.findElement(By.id("arrears-sum"));
        String sumDebtField = sumDebt.getAttribute("placeholder");
        assertTrue(sumDebt.isDisplayed(), "Текст не виден");
        assertEquals("Сумма", sumDebtField, "Текст не совпадает");

        WebElement emailDebt = mts.findElement(By.id("arrears-email"));
        String emailDebtField = emailDebt.getAttribute("placeholder");
        assertTrue(emailDebt.isDisplayed(), "Текст не виден");
        assertEquals("E-mail для отправки чека", emailDebtField, "Текст не совпадает");
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
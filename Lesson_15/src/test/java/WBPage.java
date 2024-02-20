
import org.openqa.selenium.By;
import static org.example.DriverHelper.driver;
// Страница для вынесения логики в взаимодействия со страницей, поздно увидел коммент по МТС
public class WBPage extends BaseTest {
    public static String header = driver.findElement(By.xpath("//head//title")).getText();
    public void printHeader() {
        System.out.println("Заголовок: " + header);
        printHeader();
    }
}
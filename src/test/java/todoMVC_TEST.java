import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;



import java.io.File;
import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class todoMVC_TEST {

    private static WebDriver driver;

    private static todoMVC_POM todo;

    @BeforeAll
    public static void launchBrowser(){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @BeforeEach
    public void setupPage(){
        todo = new todoMVC_POM(driver);
        todo.navigate();
    }

    @Test
    @Order(1)
    void getTitle() throws IOException {
        todo.reactVersion();
        assertEquals("React • TodoMVC", driver.getTitle());
        takeScreenshot(driver, "screenshots/test.png");
    }

    @Test
    @Order(2)
    void addItem() throws IOException {
        todo.reactVersion();
        todo.addTodo("An item");
        takeScreenshot(driver, "screenshots/addItem.png");
    }

    @Test
    @Order(3)
    void one_item_left_status() throws IOException {
        todo.reactVersion();
        todo.addTodo("First item");
        assertEquals("1 item left", todo.getTodoCount());
        takeScreenshot(driver, "screenshots/one_item_left_status.png");
    }

    @AfterAll
    public static void closeBrowser(){
        driver.quit();
    }


    public void takeScreenshot(WebDriver driver, String path) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File DestFile = new File(path);
        FileUtils.copyFile(screenshotFile, DestFile);
    }

}

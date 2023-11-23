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



import java.io.File;
import java.io.IOException;

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
    }

    @Test
    void getTitle(){
        todo.navigate();
        todo.reactVersion();
        assertEquals("React • TodoMVC", driver.getTitle());
    }

    @Test
    void testAdd() throws IOException {
        todo.navigate();
        todo.reactVersion();
        todo.addTodo("First Item");
        takeScreenshot(driver, "test.png");
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

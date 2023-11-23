import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


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
        todo.reactVersion();
    }

    @AfterEach
    public void clearBrowserStorage(){
        todo.clearLocalStorage();
    }

    @AfterAll
    public static void closeBrowser(){
        driver.quit();
    }


    @Test
    @Order(1)
    void getTitle() throws IOException {
        assertEquals("React • TodoMVC", driver.getTitle());
        takeScreenshot(driver, "screenshots/test.png");
    }

    @Test
    @Order(2)
    void addItem() throws IOException {
        todo.addTodo("An item");
        takeScreenshot(driver, "screenshots/addItem.png");
    }

    @Test
    @Order(3)
    void one_item_left_status() throws IOException {
        todo.addTodo("First item");
        assertEquals("1 item left", todo.getTodoCount());
        takeScreenshot(driver, "screenshots/one_item_left_status.png");
    }

    @Test
    @Order(4)
    void two_item_left_status() throws IOException {
        todo.addTodo("First item");
        todo.addTodo("Second item");
        assertEquals("2 items left", todo.getTodoCount());
        takeScreenshot(driver, "screenshots/two_items_left_status.png");
    }


    @Test
    @Order(5)
    void zero_item_left_status() throws IOException{
        todo.addTodo("First item");
        todo.addTodo("Second item");
        assertEquals("2 items left", todo.getTodoCount());
        todo.markItemAsCompleted(1);
        assertEquals("1 item left", todo.getTodoCount());
        todo.markItemAsCompleted(2);
        assertEquals("0 items left", todo.getTodoCount());
        takeScreenshot(driver, "screenshots/zero_items_left_status.png");
    }

    @Test
    @Order(6)
    void tick_and_untick() throws IOException {
        todo.addTodo("First item");
        assertEquals("1 item left", todo.getTodoCount());
        todo.markItemAsCompleted(1);
        assertEquals("0 items left", todo.getTodoCount());
        todo.markItemAsCompleted(1);
        assertEquals("1 item left", todo.getTodoCount());
        takeScreenshot(driver, "screenshots/tick_and_untick.png");
    }

    @Test
    @Order(7)
    void delete_item() throws IOException, InterruptedException {
        todo.addTodo("First item");
        todo.addTodo("Second item");
        todo.addTodo("Third item");
        todo.deleteItem(2);
        assertEquals("2 items left", todo.getTodoCount());
        assertEquals("Third item", todo.itemName(2));
        takeScreenshot(driver, "screenshots/delete_second_item.png");
    }


    public void takeScreenshot(WebDriver driver, String path) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File DestFile = new File(path);
        FileUtils.copyFile(screenshotFile, DestFile);
    }

}

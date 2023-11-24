import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class todoMVC_TEST {

    private static WebDriver driver;

    private static todoMVC_POM todo;


    @BeforeAll
    public static void createDriver() {
        final String browser = System.getProperty("browser").toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                driver.manage().window().maximize();
                break;

            default:
                throw new RuntimeException("Invalid browser specified!");
        }
    }


    @BeforeEach
    public void setupPage() throws InterruptedException {
        todo = new todoMVC_POM(driver);
        todo.navigate();
        todo.reactVersion();
    }

    @AfterEach
    public void clearBrowserStorage() {
        todo.clearLocalStorage();
    }

    @AfterAll
    public static void closeBrowser() {
        driver.quit();
    }

    @RegisterExtension
    ScreenshotWatcher watcher = new ScreenshotWatcher(driver, "failed_screenshots");


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
        todo.toggleItem(1);
        assertEquals("1 item left", todo.getTodoCount());
        todo.toggleItem(2);
        assertEquals("0 items left", todo.getTodoCount());
        takeScreenshot(driver, "screenshots/zero_items_left_status.png");
    }

    @Test
    @Order(6)
    void tick_and_untick() throws IOException {
        todo.addTodo("First item");
        assertEquals("1 item left", todo.getTodoCount());
        todo.toggleItem(1);
        assertEquals("0 items left", todo.getTodoCount());
        todo.toggleItem(1);
        assertEquals("1 item left", todo.getTodoCount());
        takeScreenshot(driver, "screenshots/tick_and_untick.png");
    }

    @Test
    @Order(7)
    void delete_item() throws IOException {
        todo.addTodo("First item");
        todo.addTodo("Second item");
        todo.addTodo("Third item");
        todo.deleteItem(2);
        assertEquals("2 items left", todo.getTodoCount());
        assertEquals("Third item", todo.itemName(2));
        takeScreenshot(driver, "screenshots/delete_second_item.png");
    }

    @Test
    @Order(8)
    void check_footer_does_not_exist(){
        todo.addTodo("First item");
        assertTrue(todo.isFooterDisplayed());
        todo.deleteItem(1);
        assertFalse(todo.isFooterDisplayed());
    }

    @Test
    @Order(9)
    void check_footer_exists(){
        todo.addTodo("First item");
        assertTrue(todo.isFooterDisplayed());
    }

    @Test
    @Order(10)
    void check_active_tasks(){
        todo.addTodo("First task");
        todo.addTodo("Second task");
        todo.addTodo("Third task");
        todo.toggleItem(2);
        todo.statusActive.click();
        assertEquals("https://todomvc.com/examples/react/#/active", driver.getCurrentUrl());
        assertEquals("Third task", todo.itemName(2));
    }

    @Test
    @Order(11)
    void check_all_tasks(){
        todo.addTodo("First task");
        todo.addTodo("Second task");
        todo.addTodo("Third task");
        todo.toggleItem(2);
        todo.statusActive.click();
        assertEquals("https://todomvc.com/examples/react/#/active", driver.getCurrentUrl());
        assertEquals("Third task", todo.itemName(2));
        todo.statusAll.click();
        assertEquals("https://todomvc.com/examples/react/#/", driver.getCurrentUrl());
        assertEquals("Second task", todo.itemName(2));
    }

    @Test
    @Order(12)
    void check_completed_tasks(){
        todo.addTodo("First task");
        todo.addTodo("Second task");
        todo.addTodo("Third task");
        todo.toggleItem(1);
        todo.toggleItem(3);
        todo.statusCompleted.click();
        assertEquals("https://todomvc.com/examples/react/#/completed", driver.getCurrentUrl());
        assertEquals("First task", todo.itemName(1));
        assertEquals("Third task", todo.itemName(2));
    }

    @Test
    @Order(13)
    void check_clear_completed_is_displayed(){
        todo.addTodo("First task");
        todo.toggleItem(1);
        assertTrue(todo.isClearCompleteDisplayed());
    }

    @Test
    @Order(14)
    void check_clear_completed_is_not_displayed(){
        todo.addTodo("First task");
        todo.toggleItem(1);
        assertTrue(todo.isClearCompleteDisplayed());
        todo.toggleItem(1);
        assertFalse(todo.isClearCompleteDisplayed());
    }

    @Test
    @Order(15)
    void check_completed_items_are_deleted_when_clicking_clearComplete(){
        todo.addTodo("First task");
        todo.addTodo("Second task");
        todo.addTodo("Third task");
        todo.toggleItem(1);
        todo.toggleItem(3);
        todo.clearCompletedButton.click();
        assertEquals("Second task", todo.itemName(1));
    }

    @Test
    @Order(16)
    void toggle_all_tests_as_completed() throws InterruptedException {
        todo.addTodo("First task");
        todo.addTodo("Second task");
        todo.addTodo("Third task");
        todo.toggleAllButton.click();
        todo.statusActive.click();
        assertNull(todo.itemName(1));
        todo.statusCompleted.click();
        assertEquals("Second task", todo.itemName(2));
    }

    @Test
    @Order(17)
    void toggle_all_tests_as_not_completed() throws InterruptedException {
        todo.addTodo("First task");
        todo.addTodo("Second task");
        todo.addTodo("Third task");
        todo.toggleAllButton.click();
        todo.statusActive.click();
        assertNull(todo.itemName(1));
        todo.statusAll.click();
        todo.toggleAllButton.click();
        todo.statusActive.click();
        assertEquals("Third task", todo.itemName(3));
    }


    @Test
    @Order(18)
    void item_cannot_be_empty(){
        todo.addTodo("      ");
        assertNull(todo.itemName(1));
    }

    @Test
    @Order(19)
    void adds_single_character_item(){
        todo.addTodo("a");
        assertEquals("a", todo.itemName(1));
        todo.addTodo("b");
        assertEquals("b", todo.itemName(2));
        todo.addTodo("c");
        assertEquals("c", todo.itemName(3));
    }

    @Test
    @Order(20)
    void accentued_characters_allowed(){
        todo.addTodo("áéíóúýÁÉÍÓÚÝ");
        assertEquals("áéíóúýÁÉÍÓÚÝ", todo.itemName(1));
    }

    @Test
    @Order(21)
    void emoji_allowed() throws IOException {
        todo.inputTodo.click();
        sendKeysWithEmojis(todo.inputTodo, "\uD83D\uDE0A");
        todo.inputTodo.sendKeys(Keys.ENTER);
        assertEquals("\uD83D\uDE0A", todo.itemName(1));
        takeScreenshot(driver, "emoji_characters.png");
    }

    // Helper method to send emojis to an input field
    private void sendKeysWithEmojis(WebElement element, String text) {
        String script = "var elm = arguments[0], "
                + "    txt = arguments[1];"
                + "elm.value += txt;"
                + "elm.dispatchEvent(new Event('keydown', {bubbles: true}));"
                + "elm.dispatchEvent(new Event('keypress', {bubbles: true}));"
                + "elm.dispatchEvent(new Event('input', {bubbles: true}));"
                + "elm.dispatchEvent(new Event('keyup', {bubbles: true}));";

        ((JavascriptExecutor) driver).executeScript(script, element, text);
    }

    @Test
    @Order(22)
    void item_with_4000_characters() {
        String longstring = generateString(4000);
        todo.addTodo(longstring);
        String itemName = (String) todo.itemName(1);
        int itemCharSize = itemName.length();
        assertEquals(4000, itemCharSize);
    }


    private String generateString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        char characterToRepeat = 'a';
        while (stringBuilder.length() < length) {
            int remainingCharacters = length - stringBuilder.length();
            int charactersToAdd = Math.min(remainingCharacters, 10);
            stringBuilder.append(String.valueOf(characterToRepeat).repeat(charactersToAdd));
        }
        return stringBuilder.toString();
    }
    public void takeScreenshot(WebDriver driver, String path) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File DestFile = new File(path);
        FileUtils.copyFile(screenshotFile, DestFile);
    }

}

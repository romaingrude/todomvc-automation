import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class test_multiple_browsers {

    private static WebDriver driver;
    private static todoMVC_POM todo;

    @BeforeAll
    public static void launchBrowser() {

    }


    @AfterEach
    public void closeBrowser() {
        // Quit the WebDriver after each test
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome", "safari"})
    @Order(1)
    void getTitle(String browser) throws InterruptedException {
        // Set up WebDriver based on the browser type
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
            driver.manage().window().maximize();

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        todo = new todoMVC_POM(driver);
        todo.navigate();
        todo.reactVersion();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.titleIs("React • TodoMVC"));


        // The WebDriver instance is set up, so you can use it here
        assertEquals("React • TodoMVC", driver.getTitle());
    }

    @ParameterizedTest
    @ValueSource(strings = {"firefox", "chrome", "safari"})
    @Order(2)
    void addItem(String browser) throws IOException, InterruptedException {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
            driver.manage().window().maximize();

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        todo = new todoMVC_POM(driver);
        todo.navigate();
        todo.reactVersion();
        todo.addTodo("An item");
    }
}
//    @ParameterizedTest
//    @ValueSource(strings = {"firefox", "chrome", "safari"})
//    @Order(3)
//    void one_item_left_status(String browser) throws InterruptedException {
//        if (browser.equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        } else if (browser.equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//        }
//        else if (browser.equalsIgnoreCase("safari")) {
//            WebDriverManager.safaridriver().setup();
//            driver = new SafariDriver();
//            driver.manage().window().maximize();
//
//        } else {
//            throw new IllegalArgumentException("Unsupported browser: " + browser);
//        }
//        todo = new todoMVC_POM(driver);
//        todo.navigate();
//        todo.reactVersion();
//        todo.addTodo("First item");
//        assertEquals("1 item left", todo.getTodoCount());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"firefox", "chrome", "safari"})
//    @Order(4)
//    void two_item_left_status(String browser) throws IOException, InterruptedException {
//        if (browser.equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        } else if (browser.equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//        }
//        else if (browser.equalsIgnoreCase("safari")) {
//            WebDriverManager.safaridriver().setup();
//            driver = new SafariDriver();
//            driver.manage().window().maximize();
//
//        } else {
//            throw new IllegalArgumentException("Unsupported browser: " + browser);
//        }
//        todo = new todoMVC_POM(driver);
//        todo.navigate();
//        todo.reactVersion();
//        todo.addTodo("First item");
//        todo.addTodo("Second item");
//        assertEquals("2 items left", todo.getTodoCount());
//    }
//
//}

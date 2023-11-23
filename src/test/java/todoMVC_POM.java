import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class todoMVC_POM {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "input[class='new-todo']")
    public WebElement inputTodo;

    

    
    
    public todoMVC_POM(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }



    // Clicks the React version
    public void reactVersion(){
        WebElement ReactLink = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.linkText("React"))));
        ReactLink.click();
    }

    
    // Navigates to the todoMVC webpage
    public void navigate(){
        driver.get("https://todomvc.com/");
    }


    public void addTodo(String newTodoItem){
        wait.until(ExpectedConditions.elementToBeClickable(inputTodo));
        inputTodo.click();
        inputTodo.sendKeys(newTodoItem);
        inputTodo.sendKeys(Keys.ENTER);
    }


}

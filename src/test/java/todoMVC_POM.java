import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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

    @FindBy(className = "todo-count")
    public WebElement todoCount;

    
    
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


    // Adds an item
    public void addTodo(String newTodoItem){
        wait.until(ExpectedConditions.elementToBeClickable(inputTodo));
        inputTodo.click();
        inputTodo.sendKeys(newTodoItem);
        inputTodo.sendKeys(Keys.ENTER);
    }


    // Returns number of items left
    public String getTodoCount(){
        return todoCount.getText();
    }


    // Clicks the toggle button to mark item as conmpleted
    public void markItemAsCompleted(int item_number){
        String locator = "li:nth-child(" + item_number + ") .toggle";
       WebElement markComplete = driver.findElement(By.cssSelector(locator));
       markComplete.click();
    }

    public void deleteItem(int item_number) throws InterruptedException {
        WebElement hover = driver.findElement(By.cssSelector("li:nth-child(" + item_number + ") .view"));
        // Creates action to be able to hover over the element
        Actions actions = new Actions(driver);
        actions.moveToElement(hover).perform();
        WebElement deleteButton = driver.findElement(By.cssSelector("li:nth-child(" + item_number + ") .destroy"));
        deleteButton.click();
    }

    public String itemName(int item_number){
        String locator = "li:nth-child(" + item_number + ") .view";
        WebElement itemName = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(locator))));
        return itemName.getText();
    }

    public void clearLocalStorage(){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.localStorage.clear();");
    }


}

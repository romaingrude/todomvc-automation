import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class todoMVC_POM {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "input[class='new-todo']")
    public WebElement inputTodo;

    @FindBy(className = "todo-count")
    public WebElement todoCount;

    @FindBy(xpath = "//a[@href='#/']")
    public WebElement statusAll;

    @FindBy(xpath = "//a[@href='#/active']")
    public WebElement statusActive;

    @FindBy(xpath = "//a[@href='#/completed']")
    public WebElement statusCompleted;

    @FindBy(className = "clear-completed")
    public WebElement clearCompletedButton;

    @FindBy(css = "input.toggle-all")
    public WebElement toggleAllButton;

    @FindBy(xpath = "//*[text() = 'React']")
    public WebElement reactLink;


    public todoMVC_POM(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }



    // Clicks the React version
    public void reactVersion() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(reactLink));
        reactLink.click();
        wait.until(ExpectedConditions.titleIs("TodoMVC: React"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }

    
    // Navigates to the todoMVC webpage
    public void navigate(){
        driver.get("https://todomvc.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }


    // Adds an item
    public void addTodo(String newTodoItem){
        wait.until(ExpectedConditions.elementToBeClickable(inputTodo));
        inputTodo.click();
        inputTodo.sendKeys(newTodoItem);
        inputTodo.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }


    // Returns number of items left
    public String getTodoCount(){
        return todoCount.getText();
    }


    // Clicks the toggle button to mark item as completed
    public void toggleItem(int item_number){
        String locator = "li:nth-child(" + item_number + ") .toggle";
       WebElement markComplete = driver.findElement(By.cssSelector(locator));
       markComplete.click();
       driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    public void deleteItem(int item_number) {
        WebElement hover = driver.findElement(By.cssSelector("li:nth-child(" + item_number + ") .view"));
        // Creates action to be able to hover over the element
        Actions actions = new Actions(driver);
        actions.moveToElement(hover).perform();
        WebElement deleteButton = driver.findElement(By.cssSelector("li:nth-child(" + item_number + ") .destroy"));
        deleteButton.click();
    }

    public Object itemName(int item_number){
        try {
            String locator = "li:nth-child(" + item_number + ") .view";
            WebElement itemName = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(locator))));
            return itemName.getText();
        } catch (Exception e){
            return null;
        }
    }

    public void clearLocalStorage(){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.localStorage.clear();");
    }


    public boolean isFooterDisplayed() {
        List<WebElement> footerElements = driver.findElements(By.cssSelector("footer[class='footer']"));
        // If the list is not empty, the element exists
        return !footerElements.isEmpty();
    }

    public boolean isClearCompleteDisplayed(){
        List<WebElement> clearCompletedButton = driver.findElements(By.className("clear-completed"));
        return !clearCompletedButton.isEmpty();
    }




}

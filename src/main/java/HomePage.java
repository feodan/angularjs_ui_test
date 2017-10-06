import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.text.MessageFormat;
import java.util.List;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class HomePage {
    private final WebDriver driver;
    private String todoCheckboxLocator = "//*[text()=''{0}'']/../input";
    private String todoLabelText = "//*[text()=''{0}'']";
//    private By todoInputLocator = cssSelector("[ng-model='todoList.todoText']");
//    private By addTodoButtonLocator = cssSelector("[type='submit']");
//    private WebElement todoInput = driver.findElement(cssSelector("[ng-model='todoList.todoText']"));
    @FindBy(how = How.CSS, using = "[ng-model='todoList.todoText']")
    private WebElement todoInput;
    @FindBy(how = How.CSS, using = "[type='submit']")
    private WebElement addTodoButton;
//    private WebElement addTodoButton = driver.findElement(cssSelector("[type='submit']"));
    private By todoItemsLocator = cssSelector("ul.unstyled li span");
    private By archiveLocator = cssSelector("[ng-click='todoList.archive()']");

    public HomePage(WebDriver driver) {
        this.driver = driver;

        if (!"AngularJS — Superheroic JavaScript MVW Framework".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the home page");
        }
    }

    /**
     * Finds to_do with specific name and checks it
     *
     * @param todoName specify to_do name
     * @return home page
     */
    public HomePage checkTodo(String todoName) {
        driver.findElement(xpath(MessageFormat.format(todoCheckboxLocator, todoName))).click();
        return this;
    }

    /**
     * Adds new to_do by typing to_name and pressing Add button
     *
     * @param todoName specify to_do name
     * @return home page
     */
    public HomePage addTodo(String todoName) {
//        driver.findElement(todoInputLocator).sendKeys(todoName);
//        driver.findElement(addTodoButtonLocator).click();
        todoInput.sendKeys(todoName);
        addTodoButton.click();
        return this;
    }

    /**
     * Verifies specified to_do in the list
     *
     * @param todoName specify to_do name
     * @return true id to_do is presented in the list and false if not presented
     */
    public boolean checkTodoPresence(String todoName) {
        List<WebElement> myElements = driver.findElements(todoItemsLocator);
        for (WebElement e : myElements) {
            if (e.getText().equals(todoName))
                return true;
        }
        return false;
    }

    /**
     * Archives specified to_do by checking it and pressing Archive button
     *
     * @param todoName specify to_do name
     * @return home page
     */
    public HomePage archiveTodo(String todoName) {
        checkTodo(todoName);
        driver.findElement(archiveLocator).click();
        return this;
    }

    /**
     * Verifies selection of specified to_do
     *
     * @param todoName specify to_do name
     * @return true if specified to_do is checked and false if to_do is unchecked
     */
    public boolean checkSelectionOfTodo(String todoName) {
        return driver.findElement(xpath(MessageFormat.format(todoLabelText, todoName))).getAttribute("class").equals("done-true");
    }
}

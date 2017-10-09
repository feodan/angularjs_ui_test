import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.MessageFormat;
import java.util.List;

import static org.openqa.selenium.By.xpath;

public class HomePage {
    private WebDriver driver;

    private String todoCheckboxLocator = "//*[text()=''{0}'']/../input";
//    @FindBy(css = todoCheckboxLocator)
//    private WebElement todoCheckbox;

    private String todoLabelText = "//div[@app-run=\"todo.html\"]//*[text()=''{0}'']";

    @FindBy(css = "[ng-model='todoList.todoText']")
    private WebElement todoInput;

    @FindBy(css = "[type='submit']")
    private WebElement addTodoButton;

    @FindBy(css = "ul.unstyled li span")
    private List<WebElement> todoItems;

    @FindBy(css = "[ng-click='todoList.archive()']")
    private WebElement archive;

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
//        todoCheckbox.click();
        return this;
    }

    /**
     * Adds new to_do by typing to_name and pressing Add button
     *
     * @param todoName specify to_do name
     * @return home page
     */
    public HomePage addTodo(String todoName) {
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
        List<WebElement> myElements = todoItems;
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
        archive.click();
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

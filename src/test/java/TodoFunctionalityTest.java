import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TodoFunctionalityTest {
    private WebDriver webDriver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/dev/ChromeWebDriver/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://angularjs.org/");
    }

    /**
     * Test verifies that to_do is displayed on UI after its creation and disappearing after its removing
     */
    @Test
    public void testAddRemoveTodo() {
        HomePage homePage = new HomePage(webDriver);
        String todoName = "AAA";

        homePage.addTodo(todoName);
        assertTrue(MessageFormat.format("Can''t find {0} todo", todoName), homePage.checkTodoPresence(todoName));

        homePage.archiveTodo(todoName);
        assertFalse(MessageFormat.format("Can find {0} todo", todoName), homePage.checkTodoPresence(todoName));
    }

    /**
     * Test verifies that to_do after checking becomes strikethrough and still visible.
     * After unchecking to_do becomes unstrikethrough and still visible.
     */
    @Test
    public void testCheckUncheckTodo() {
        HomePage homePage = new HomePage(webDriver);
        String todoName = "AAA";

        homePage.addTodo(todoName);
        homePage.checkTodo(todoName);
        assertTrue(MessageFormat.format("Can''t find {0} todo", todoName), homePage.checkTodoPresence(todoName));
        assertTrue(MessageFormat.format("{0} todo isn''t selected", todoName),homePage.checkSelectionOfTodo(todoName));

        homePage.checkTodo(todoName);
        assertTrue(MessageFormat.format("Can''t find {0} todo", todoName), homePage.checkTodoPresence(todoName));
        assertFalse(MessageFormat.format("{0} todo is selected", todoName),homePage.checkSelectionOfTodo(todoName));

        homePage.archiveTodo(todoName);
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class AbvTest {
    private WebDriver webDriver;
    @Before
    public void setUp()
    {

        webDriver = new FirefoxDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));


    }
    @Test
    public void TestLogin_ValidCredentials_ShouldLoginCorrectly()
    {

        webDriver.get("http://abv.bg");
        String validUsername = "Insert username here";
        String validPassword = "Insert password here";
        WebElement userId = webDriver.findElement(By.id("username"));
        WebElement passwordId = webDriver.findElement(By.id("password"));

        userId.clear();
        userId.sendKeys(validUsername);

        passwordId.clear();
        passwordId.sendKeys(validPassword);

        WebElement loginButton = webDriver.findElement(By.id("loginBut"));
        loginButton.click();
        WebElement username_LoggedIn = webDriver.findElement(By.id("gwt-uid-387"));

        assertEquals(
                validUsername,
                username_LoggedIn.getText());
        assertEquals("https://nm20.abv.bg/Mail.html",
                webDriver.getCurrentUrl()
        );





    }
    @Test
    public void TestSendEmail_AllRequiredFieldsPopulated_ShouldSendAndReceive() throws InterruptedException {
        this.TestLogin_ValidCredentials_ShouldLoginCorrectly();

        String initial_number = webDriver.findElement(By.xpath("//em[@class='fl']")).getText();
        WebElement compose_button = webDriver.findElement(By.className("abv-button"));
        compose_button.click();

        WebElement to_field = webDriver.findElement(By.xpath("//input[@class='fl']"));

            to_field.clear();

            to_field.sendKeys("Insert email name here");



        WebElement subject_field = webDriver.findElement(By.xpath("//input[@class='gwt-TextBox']"));
        subject_field.clear();
        subject_field.sendKeys("Test message");
        subject_field.click();
        to_field.click();

        WebElement text_field = webDriver.findElement(By.xpath("//iframe[@class='gwt-RichTextArea']"));
        webDriver.switchTo().frame(text_field);
        text_field.sendKeys("Test message");
        webDriver.switchTo().defaultContent();

        WebElement send_button = webDriver.findElement(By.className("abv-button"));
        send_button.click();

        webDriver.navigate().refresh();

        List<WebElement> email_list = webDriver.findElements(By.className("inbox-cellTableSecondColumn"));

        String resulting_number = webDriver.findElement(By.xpath("//em[@class='fl']")).getText();
        assertEquals(Integer.parseInt(initial_number) +1 ,Integer.parseInt(resulting_number) );

        email_list.get(0).click();
        WebElement subject1 = webDriver.findElement(By.className("abv-letterSubject"));
        WebElement subject2 = subject1.findElement(By.className("gwt-HTML"));
        assertEquals("Test message",subject2.getText());


        String sender = webDriver.findElement((By.xpath("//div[span[text()='Insert email's  name here']]"))).getText();
        assertEquals("Car Fella",sender.substring(0,9));







    }
    @After
    public void terDown()
    {

    }



}

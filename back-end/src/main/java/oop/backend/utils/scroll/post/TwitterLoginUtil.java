package oop.backend.utils.scroll.post;

import oop.backend.utils.scroll.ScrollUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TwitterLoginUtil extends ScrollUtil {
    public static WebDriver loginAndNavigate(String twitterLink,String navigate ,String username, String password)
        throws Exception {
        WebDriver driver = ScrollUtil.setUp(twitterLink);
        Thread.sleep(3000);
        WebElement usernameInput = driver.findElement(
            By.cssSelector("input[autocomplete='username'][name='text']"));
        usernameInput.sendKeys(username);
        usernameInput.sendKeys(Keys.ENTER);
        
        Thread.sleep(1000);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[autocomplete='current-password'][name='password']")));
        passwordInput.sendKeys(password);
        passwordInput.sendKeys(Keys.ENTER);
        
        Thread.sleep(5000);
        
        driver.navigate().to(navigate);
        
        Thread.sleep(5000);
        
        return driver;
    }
}

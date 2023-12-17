package oop.backend.utils.scroll.blog;

import oop.backend.utils.scroll.ScrollUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class BlogUtil {
    public static String getDoc(String url) {
        WebDriver driver = null;
        try {
            driver = ScrollUtil.setUp(url);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                WebElement acceptCookie = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
                acceptCookie.click();
            } catch (Exception ignored) {
            }
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            int i = 0;
            while (true) {
                if (i <= 1) {
                    try {
                        WebElement clickElement = driver.findElement(By.cssSelector("div.ib-page-title"));
                        clickElement.click();
                    } catch (Exception ignored) {
                    }
                }
                Actions actions = new Actions(driver);
                actions.keyDown(Keys.ARROW_DOWN).build().perform();
                i++;
                if (i == 2000) break;
            }

            return (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }
    }
}

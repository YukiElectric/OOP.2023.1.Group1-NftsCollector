package oop.backend.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Tiện ích này để truy cập vào trang web rồi cuộn trang web sau đó lấy html hay không tùy theo loại web
 */
public class ScrollUtil {
    public static String scrollUseURL(String url) throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        
        WebDriver driver = new FirefoxDriver(options);
        try {
            driver.get(url);
            Thread.sleep(3000);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
                + ", document.body.offsetHeight, document.documentElement.clientHeight,"
                + " document.documentElement.scrollHeight,"
                + " document.documentElement.offsetHeight )");
            int steps = 10;
            long delayBetweenStepsInMillis = 1000;
            long scrollStep = pageHeight / steps;
            for (int i = 0; i <20;i++) {
                long yOffset = i * scrollStep;
                jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
                Thread.sleep(delayBetweenStepsInMillis);
            }
            return (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
        } finally {
            {
                driver.quit();
            }
        }
    }
    public static WebDriver setUp(String url) throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        
        WebDriver driver = new FirefoxDriver();
        
        driver.get(url);
        Thread.sleep(3000);
        return driver;
    }
    public static String scrollHTML(WebDriver driver) throws Exception {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
            + ", document.body.offsetHeight, document.documentElement.clientHeight,"
            + " document.documentElement.scrollHeight,"
            + " document.documentElement.offsetHeight )");
        int steps = 10;
        long delayBetweenStepsInMillis = 1000;
        long scrollStep = pageHeight / steps;
        for (int i = 0; i <20;i++) {
            long yOffset = i * scrollStep;
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
        }
        return (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
    }
}


package oop.backend.utils.scroll;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Tiện ích này để truy cập vào trang web rồi cuộn trang web sau đó lấy html hay không tùy theo loại web
 */
public class ScrollUtil {
    public static WebDriver setUp(String url) throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

        WebDriver driver = new FirefoxDriver();

        driver.get(url);
        Thread.sleep(1000);
        return driver;
    }

    public static Document scrollAndGetDoc(WebDriver driver) throws Exception {
        Document masterDocument = null;
        Thread.sleep(2000);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
                + ", document.body.offsetHeight, document.documentElement.clientHeight,"
                + " document.documentElement.scrollHeight,"
                + " document.documentElement.offsetHeight )");
        int steps = 20;
        long delayBetweenStepsInMillis = 500;
        long scrollStep = pageHeight / steps;
        for (int i = 0; i < steps; i++) {
            long yOffset = i * scrollStep;
            String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
            Document pageDocument = Jsoup.parse(html);
            if (masterDocument == null) {
                masterDocument = pageDocument.clone();
            } else {
                Elements bodyElements = pageDocument.body().children();
                for(Element element : bodyElements){
                    masterDocument.body().appendChild(element);
                }
            }
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
        }
        driver.quit();
        return masterDocument;
    }

    public static Document scrollInfinitePage(WebDriver driver) throws Exception{
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        Document masterDocument = null;

        int i=0;
        Thread.sleep(3000);
        long lastPageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
                + ", document.body.offsetHeight, document.documentElement.clientHeight,"
                + " document.documentElement.scrollHeight,"
                + " document.documentElement.offsetHeight )");
        // Lặp cho đến khi trang không còn cuộn được nữa
        do {
            if (i== 30) {
                break;
            }

            // Lấy chiều cao trang trước khi cuộn
            long pageHeight = lastPageHeight *i/4;


            // Cuộn trang xuống cuối cùng
            jsExecutor.executeScript("window.scrollTo(0,"+ pageHeight+" );");

            // Đợi một khoảng thời gian (có thể điều chỉnh)
            Thread.sleep(1000);

            String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
            Document pageDocument = Jsoup.parse(html);
            if (masterDocument == null) {
                masterDocument = pageDocument.clone();
            } else {
                Elements bodyElements = pageDocument.body().children();
                for(Element element : bodyElements){
                    masterDocument.body().appendChild(element);
                }
            }
            i++;
        } while (true);
        // Đóng trình duyệt sau khi hoàn thành
        driver.quit();
        return masterDocument;
    }
}


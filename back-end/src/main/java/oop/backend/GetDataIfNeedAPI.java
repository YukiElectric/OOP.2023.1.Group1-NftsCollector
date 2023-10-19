package oop.backend;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GetDataIfNeedAPI {
    public static void main(String[] args) throws Exception {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless"); // Config lại cho web sẽ hiển thị ở chế độ ẩn không hiện lên
        
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe"); //Config môi trường để web chạy
        //Ở đây là xài firefox lên muốn lấy bắt buộc mọi người phải có browser này hạn chế dùng google do giữa driver và browser phiên bản không tương thích
        WebDriver driver = new FirefoxDriver(options); //Đưa config vào cho web ở đây là không hiện lên hay là Minimize
        
        driver.get("https://twitter.com/login");  //Đưa url vào web để dẫn đến trang ở đây truyền trực tiếp nhưng thường hãy tách biến ra và config cứng lại
        
        Thread.sleep(5000); // Đợi 5s để web ổn định có thể lấy dễ hơn
        
        WebElement usernameInput = driver.findElement(  // Tìm đến phần tử có thẻ input và có 2 thuộc tính autocomplete và name
            By.cssSelector("input[autocomplete='username'][name='text']"));
        usernameInput.sendKeys("your_twitter_account"); //Set cho phần tử đó có giá trị là tài khoản đăng nhập
        usernameInput.sendKeys(Keys.ENTER);     //Ấn Enter để chuyển trang tùy vào trang có thể là ấn Enter có thể là button ấn nhưng 90% là enter
        
        Thread.sleep(5000);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Đợi 10s load web trong trường hợp web có js ẩn div hoặc các element khác
        WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated( // Tìm phần tử có password như trên sau đó gửi mật khẩu của mình vào
            By.cssSelector("input[autocomplete='current-password'][name='password']")));
        password.sendKeys("your_twitter_password");
        password.sendKeys(Keys.ENTER);
        
        Thread.sleep(5000); // Đợi 5s để đăng nhập
        
        driver.navigate().to("https://twitter.com/search?q=%23nft&src=typed_query&f=live"); //Navigate hay chuyển hướng đến trang web cần sau khi đăng nhập do đã có cookie lên có thể lấy được dễ dàng
        
        Thread.sleep(5000); //Đợi 5s để navigate ổn định
        
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;  //Lấy ra html đề phòng html bị js ẩn đi thì sẽ lấy hết toàn bộ
        String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
        System.out.println(html);
        
        driver.quit(); //Tắt trình duyệt cho đỡ tốn tài nguyên
    }
}

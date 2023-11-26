package oop.frontend.jfxutils;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import oop.frontend.App;

import java.io.File;
import java.util.Scanner;
/**
 * Lớp tiện ích để làm việc với WebView trong ứng dụng JavaFX.
 */
public class WebViewUtil {

    /**
     * Đọc nội dung từ một file và hiển thị nó trong WebView.
     *
     * @param pathFile Đường dẫn đến file cần đọc.
     * @return WebView chứa nội dung của file.
     * @throws Exception Khi có lỗi xảy ra trong quá trình đọc file.
     */    private static String readFile(String pathFile) throws Exception {
        StringBuilder strBuilder = new StringBuilder();
        File file = new File(App.class.getResource(pathFile).getPath());
        Scanner fileData = new Scanner(file);
        while (fileData.hasNextLine()) {
            String data = fileData.nextLine();
            strBuilder.append(data);
        }
        fileData.close();
        return strBuilder.toString();
    }
    /**
     * Thiết lập nội dung của file vào WebView.
     *
     * @param pathFile Đường dẫn đến file cần hiển thị trong WebView.
     * @return WebView chứa nội dung của file.
     * @throws Exception Khi có lỗi xảy ra trong quá trình đọc file hoặc thiết lập nội dung vào WebView.
     */
    public static WebView setView(String pathFile) throws Exception {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.loadContent(readFile(pathFile));
        return browser;
    }
}

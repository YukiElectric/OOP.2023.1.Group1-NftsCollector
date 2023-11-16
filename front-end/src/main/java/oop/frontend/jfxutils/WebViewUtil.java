package oop.frontend.jfxutils;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import oop.frontend.App;

import java.io.File;
import java.util.Scanner;

public class WebViewUtil {
    private static String readFile(String pathFile) throws Exception {
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

    public static WebView setView(String pathFile) throws Exception {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.loadContent(readFile(pathFile));
        return browser;
    }
}

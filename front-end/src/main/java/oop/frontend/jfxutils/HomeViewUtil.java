package oop.frontend.jfxutils;


import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import oop.frontend.App;
import oop.frontend.common.Constants;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class HomeViewUtil {
    private static String readFile() throws Exception {
        StringBuilder strBuilder = new StringBuilder();
        File file = new File("GioiThieu.txt");
        Scanner fileData = new Scanner(file);
        while (fileData.hasNextLine()) {
            String data = fileData.nextLine();
            strBuilder.append(data);
        }
        fileData.close();
        return strBuilder.toString();
    }

    public static void setWebView(BorderPane borderPane) throws Exception {
        Image image = new Image(Objects.requireNonNull(App.class.getResource(Constants.HOME_IMAGE_VIEW)).openStream());
        ImageView imageView = new ImageView(image);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imageView);
        borderPane.setCenter(scrollPane);
    }
}

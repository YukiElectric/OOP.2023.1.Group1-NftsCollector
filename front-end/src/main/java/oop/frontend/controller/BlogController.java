/**
 * Đây là lớp BlogController điều khiển giao diện blog.
 */
package oop.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import oop.frontend.App;
import oop.frontend.common.Constants;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class BlogController extends VBox {

    /**
     * Đối tượng ImageView hiển thị hình ảnh của blog.
     */
    @FXML
    private ImageView imageView;

    /**
     * Đối tượng Label hiển thị tiêu đề của blog.
     */
    @FXML
    private Label title;

    /**
     * Đối tượng Label hiển thị thời gian của blog.
     */
    @FXML
    private Label time;

    /**
     * Đối tượng Label hiển thị nội dung của blog.
     */
    @FXML
    private Label article;

    /**
     * Đối tượng Hyperlink chứa liên kết đến blog.
     */
    @FXML
    private Hyperlink link;

    /**
     * Phương thức mở trình duyệt web với đường dẫn được cung cấp.
     *
     * @param url Đường dẫn của trang web .
     */
    private void openWebpage(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                Desktop.getDesktop().browse(new URI(url));
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Can't open link in web browser");
                alert.showAndWait();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thiết lập thông tin của blog từ dữ liệu được cung cấp.
     *
     * @param data Dữ liệu của blog bao gồm hình ảnh, tiêu đề, thời gian, nội dung và liên kết.
     * @throws Exception Ngoại lệ nếu có lỗi xảy ra trong quá trình thiết lập.
     */
    private void setItem(Map<String, String> data) throws Exception {
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        String urlImage = data.get("image");
        URL url = new URL(urlImage);
        InputStream inputStream = url.openStream();
        Image image = new Image(inputStream);
        imageView.setImage(image);
        inputStream.close();
        time.setText(data.get("time"));
        title.setText(data.get("title"));
        article.setText(data.get("article"));
        link.setText(data.get("linkBlog"));
        link.setOnAction(event -> openWebpage(link.getText()));
    }

    /**
     * Constructor của BlogController với dữ liệu blog được cung cấp.
     *
     * @param data Dữ liệu của blog bao gồm hình ảnh, tiêu đề, thời gian, nội dung và liên kết.
     * @throws Exception Ngoại lệ nếu có lỗi xảy ra trong quá trình khởi tạo.
     */
    public BlogController(Map<String, String> data) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Constants.BLOG_VIEW_SOURCE));
        fxmlLoader.setController(this);
        this.getChildren().add(fxmlLoader.load());
        setItem(data);
    }
}

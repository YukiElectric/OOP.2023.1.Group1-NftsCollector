package oop.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import oop.frontend.App;
import oop.frontend.common.Constants;

import java.io.IOException;
import java.util.Map;
/**
 * tạo lớp PostItemController để quản lý một mục hiển thị thông tin bài đăng.
 */
public class PostItemController extends VBox {
    @FXML
    private Label postID;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label body;
    /**
     * Thiết lập các thành phần giao diện với dữ liệu từ Map.
     *
     * @param data Dữ liệu của bài đăng để hiển thị.
     */
    private void setElements(Map<String , String> data) {
        postID.setText(data.get("postId"));
        id.setText(data.get("id"));
        name.setText(data.get("name"));
        email.setText(data.get("email"));
        body.setText(data.get("body"));
    }
    /**
     * Constructor của Controller.
     *
     * @param data Dữ liệu của bài đăng để hiển thị.
     * @throws IOException Nếu có lỗi khi tải FXML.
     */
    public PostItemController(Map<String , String> data) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Constants.POST_ITEM_SOURCE));
        fxmlLoader.setController(this);
        this.getChildren().add(fxmlLoader.load());
        setElements(data);
    }
}


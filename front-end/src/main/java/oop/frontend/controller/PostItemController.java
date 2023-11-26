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
    private Label floorPrice;

    @FXML
    private Label img;


    @FXML
    private Label name;

    @FXML
    private Label volume;
    /**
     * Thiết lập các thành phần giao diện với dữ liệu từ Map.
     *
     * @param data Dữ liệu của bài đăng để hiển thị.
     */
    private void setElements(Map<String , String> data) {
        floorPrice.setText(data.get("floorPrice"));
        img.setText(data.get("img"));
        name.setText(data.get("name"));
        volume.setText(data.get("volume"));
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


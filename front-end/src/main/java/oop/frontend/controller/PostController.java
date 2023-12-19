package oop.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import oop.frontend.App;
import oop.frontend.common.Constants;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class PostController extends VBox {
    @FXML
    private ImageView imageView;

    @FXML
    private Label content;

    @FXML
    private Label hashtag;

    @FXML
    private Label user;
    private void setData(Map<String, String> data) throws Exception{
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        String urlImage = data.get("image");
        if(!urlImage.isEmpty()) {
            URL url = new URL(urlImage);
            InputStream inputStream = url.openStream();
            Image image = new Image(inputStream);
            imageView.setImage(image);
            inputStream.close();
        }else {
            Image image = new Image(App.class.getResource(Constants.POST_NO_IMAGE).openStream());
            imageView.setImage(image);
        }
        user.setText(data.get("user"));
        content.setText(data.get("content"));
        hashtag.setText(data.get("hashtag"));
    }
    public PostController(Map<String, String> data) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Constants.POST_VIEW_SOURCE));
        fxmlLoader.setController(this);
        this.getChildren().add(fxmlLoader.load());
        setData(data);
    }
}

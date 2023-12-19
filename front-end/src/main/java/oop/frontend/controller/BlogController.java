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
    @FXML
    private ImageView imageView;

    @FXML
    private Label labelFirst;

    @FXML
    private Label labelSecond;

    @FXML
    private Label title;

    @FXML
    private Label time;

    @FXML
    private Label article;

    @FXML
    private Hyperlink link;

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

    private void setItem(Map<String, String> data) throws Exception{
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        String urlImage = data.get("image");
        URL url = new URL(urlImage);
        InputStream inputStream = url.openStream();
        Image image = new Image(inputStream);
        imageView.setImage(image);
        inputStream.close();
        String label = data.get("label");
        if(label==null){
            labelFirst.setVisible(false);
            labelSecond.setVisible(false);
        }else {
            String[] labels = label.split(",");
            if(labels.length==1) {
                labelFirst.setText(labels[0]);
                labelSecond.setVisible(false);
            }
            else if(labels.length==2) {
                labelFirst.setText(labels[0]);
                labelSecond.setText(labels[1]);
            }else{
                labelFirst.setVisible(false);
                labelSecond.setVisible(false);
            }
        }
        time.setText(data.get("time"));
        title.setText(data.get("title"));
        article.setText(data.get("article"));
        link.setText(data.get("linkBlog"));
        link.setOnAction(event -> openWebpage(link.getText()));
    }

    public BlogController(Map<String, String> data) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Constants.POST_ITEM_SOURCE));
        fxmlLoader.setController(this);
        this.getChildren().add(fxmlLoader.load());
        setItem(data);
    }
}

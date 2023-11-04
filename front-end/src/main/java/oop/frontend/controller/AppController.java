package oop.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable{
    @FXML
    private ImageView view;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String imageUrl = "https://ecdn.game4v.com/g4v-content/uploads/2022/09/25083529/Gojo-2-game4v-1664069728-55.jpg";

        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            Image image = new Image(inputStream);
            view.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

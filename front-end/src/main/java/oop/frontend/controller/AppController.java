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
        String imageUrl = "https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-6/386399127_6986504418079350_6084006873254665818_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=813123&_nc_ohc=Nc1fBQ0E9oEAX_6UWle&_nc_ht=scontent.fsgn2-4.fna&cb_e2o_trans=q&oh=00_AfBoytUE_xpiXc0RQT8uZX348qKq40fic4yUFcbr0UPCXg&oe=652D48F0";

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

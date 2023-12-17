package oop.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oop.frontend.common.Constants;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Constants.HOME_VIEW_SOURCE));
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth()/1.2;
        double screenHeight = screen.getVisualBounds().getHeight()/1.2;
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setTitle(Constants.APP_TITLE);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResource(Constants.APP_ICON)).openStream()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

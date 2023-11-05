package oop.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/homepage.fxml"));
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth()/2;
        double screenHeight = screen.getVisualBounds().getHeight()/2;
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

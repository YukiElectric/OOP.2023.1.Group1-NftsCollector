package oop.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ToggleGroup menu;
    @FXML
    private TabPane tab_bar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
        System.out.println(menu.getSelectedToggle());
    }
}

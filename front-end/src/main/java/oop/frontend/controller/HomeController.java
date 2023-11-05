package oop.frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import oop.frontend.jfxutils.HomeViewUtil;
import oop.frontend.jfxutils.TabPaneUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ToggleGroup menu;
    @FXML
    private TabPane tabPane;
    @FXML
    private BorderPane homeView;

    @FXML
    void returnHome(MouseEvent event) {
        tabPane.getSelectionModel().select(0);
        if(menu.getSelectedToggle()!=null) menu.getSelectedToggle().setSelected(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TabPaneUtil.setSelection(menu, tabPane, 1);
        try {
            HomeViewUtil.setWebView(homeView);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

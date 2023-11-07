package oop.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oop.frontend.jfxutils.ComboBoxUtil;
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
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> trendingComboBox;

    @FXML
    private ComboBox<String> topComboBox;

    @FXML
    private ToggleGroup menuTrending;

    @FXML
    private TabPane trendingTabPane;

    @FXML
    private ToggleGroup menuTop;

    @FXML
    private TabPane topTabPane;

    @FXML
    private VBox postView;

    @FXML
    void returnHome(MouseEvent event) {
        tabPane.getSelectionModel().select(0);
        menu.getToggles().get(5).setSelected(true);
    }

    @FXML
    void setSelection(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TabPaneUtil.setSelection(menu, tabPane, 1);
        TabPaneUtil.setSelection(menuTrending, trendingTabPane);
        TabPaneUtil.setSelection(menuTop, topTabPane);
        TabPaneUtil.resetIndexTabPane(menu, menuTop, menuTrending);
        TabPaneUtil.setViewChange(menu, postView);
        ComboBoxUtil.setItem(comboBox);
        ComboBoxUtil.setItem(topComboBox);
        ComboBoxUtil.setItem(trendingComboBox);
        ComboBoxUtil.resetComboBoxItem(comboBox, menu);
        ComboBoxUtil.resetComboBoxItem(topComboBox, menu);
        ComboBoxUtil.resetComboBoxItem(topComboBox, menuTop);
        ComboBoxUtil.resetComboBoxItem(trendingComboBox, menu);
        ComboBoxUtil.resetComboBoxItem(trendingComboBox, menuTrending);
        try {
            HomeViewUtil.setWebView(homeView);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

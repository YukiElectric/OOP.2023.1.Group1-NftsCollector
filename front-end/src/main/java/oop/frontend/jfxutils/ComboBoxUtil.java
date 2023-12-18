package oop.frontend.jfxutils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.controller.EPlatformController;

public class ComboBoxUtil {
    public static void setItem(ComboBox<String> comboBox){
        comboBox.setItems(FXCollections.observableArrayList("Day","Week","Month"));
    }

    public static void resetComboBoxItem(ComboBox<String> comboBox, ToggleGroup menu){
        menu.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue!=null){
                comboBox.setValue(null);
            }
        }));
    }

    public static void setViewByComboBox(ComboBox<String> comboBox, VBox vBox, String url){
        int selected = comboBox.getSelectionModel().getSelectedIndex();
        switch (selected){
            case 0 -> VboxViewUtil.setViewVBox(vBox, Constants.API_URL + url, "Day", EPlatformController.class, 1);
            case 1 -> VboxViewUtil.setViewVBox(vBox, Constants.API_URL + url, "Week", EPlatformController.class, 1);
            case 2 -> VboxViewUtil.setViewVBox(vBox, Constants.API_URL + url, "Month", EPlatformController.class, 1);
            default -> {}
        }
    }

}

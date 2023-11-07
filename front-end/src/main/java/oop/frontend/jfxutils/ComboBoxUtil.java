package oop.frontend.jfxutils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;

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

}

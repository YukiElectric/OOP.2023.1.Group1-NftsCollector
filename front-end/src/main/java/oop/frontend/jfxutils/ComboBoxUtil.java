package oop.frontend.jfxutils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.controller.PostItemController;
/**
 * Cách sort thông tin theo thời gian
 */
public class ComboBoxUtil {
    /**
     * Thiết lập các mục cho ComboBox để chọn đơn vị thời gian.
     *
     * @param comboBox ComboBox để hiển thị các mục lựa chọn.
     */
    public static void setItem(ComboBox<String> comboBox){
        comboBox.setItems(FXCollections.observableArrayList("Day","Week","Month"));
    }
    /**
     * Thiết lập sự kiện reset cho ComboBox khi chọn mục khác trong ToggleGroup.
     *
     * @param comboBox ComboBox cần reset.
     * @param menu     ToggleGroup để xác định khi nào cần reset ComboBox.
     */
    public static void resetComboBoxItem(ComboBox<String> comboBox, ToggleGroup menu){
        menu.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue!=null){
                comboBox.setValue(null);
            }
        }));
    }
    /**
     * Hiển thị giao diện dựa trên lựa chọn trong ComboBox.
     *
     * @param comboBox ComboBox chứa các mục lựa chọn để sort theo thời gian.
     * @param vBox     VBox để hiển thị dữ liệu tương ứng với lựa chọn.
     */

    public static void setViewByComboBox(ComboBox<String> comboBox, VBox vBox){
        int selected = comboBox.getSelectionModel().getSelectedIndex();
        switch (selected){
            case 0 -> VboxViewUtil.setViewVBox(vBox, Constants.API_URL, "", PostItemController.class);
            case 1 -> VboxViewUtil.setViewVBox(vBox, Constants.API_URL, "", PostItemController.class);
            case 2 -> VboxViewUtil.setViewVBox(vBox, Constants.API_URL, "", PostItemController.class);
            default -> {}
        }
    }

}

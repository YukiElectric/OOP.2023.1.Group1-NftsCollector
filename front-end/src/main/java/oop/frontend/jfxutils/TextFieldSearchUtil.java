package oop.frontend.jfxutils;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.controller.PostItemController;

public class TextFieldSearchUtil {

    /**
     * Xử lý hành động tìm kiếm khi người dùng thực hiện tìm kiếm trong JFXTextField.
     *
     * @param search JFXTextField chứa từ khóa tìm kiếm.
     * @param view   VBox cần hiển thị kết quả tìm kiếm.
     */    public static void onSearchAction(JFXTextField search, VBox view){
        boolean check = !(search.getText().isEmpty() || search.getText().isBlank());
        if(check) VboxViewUtil.setViewVBox(view, Constants.API_URL, search.getText(), PostItemController.class);
        search.clear();
    }
}

package oop.frontend.jfxutils;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.controller.EPlatformController;

public class TextFieldSearchUtil {
    public static void onSearchAction(JFXTextField search, VBox view){
        boolean check = !(search.getText().isEmpty() || search.getText().isBlank());
        if(check) VboxViewUtil.setViewVBox(view, Constants.API_URL, "", EPlatformController.class, 0);
        search.clear();
    }
}

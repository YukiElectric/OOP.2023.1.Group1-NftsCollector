package oop.frontend.jfxutils;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.controller.EPlatformController;
import oop.frontend.controller.PostController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TextFieldSearchUtil {
    public static void onSearchAction(JFXTextField search, VBox view){
        boolean check = !(search.getText().isEmpty() || search.getText().isBlank());
        if(check) {
            String searchText = search.getText();
            try {
                searchText = URLEncoder.encode(searchText, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            searchText.replace("+","%20").replace(".","%2E");
            VboxViewUtil.setViewVBox(view, Constants.API_URL, "post?search=" + searchText, PostController.class, 0);
        }
        search.clear();
    }
}

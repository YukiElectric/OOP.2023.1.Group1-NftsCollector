/**
 * Lớp HeaderController điều khiển phần header của giao diện.
 */
package oop.frontend.controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Set;

public class HeaderController extends VBox {

    /**
     * Phương thức chuyển đổi tên từ dạng chuỗi viết liền thành dạng chuẩn
     *
     * @param input
     * @return Tên chuẩn với khoảng cách giữa các từ.
     */
    private String convertName(String input) {
        String[] words = input.split("(?=[A-Z])");

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * Constructor của HeaderController với danh sách các dữ liệu cần hiển thị trong header.
     * hàm này tạo và hiển thị các nhãn (Label) chứa các chuỗi từ đã nhập chuyển đổi sang dạng chuẩn,
     * trong một giao diện lưới (GridPane) trong phần header của ứng dụng.
     *
     *
     *
     * @param data Danh sách các dữ liệu cần hiển thị trong header.
     */
    public HeaderController(Set<String> data) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        gridPane.setStyle("-fx-background-color: #2CF2A2;");
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        gridPane.getRowConstraints().add(rowConstraints);
        int index = 0;
        for (String key : data) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHalignment(HPos.CENTER);
            columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            columnConstraints.setMinWidth(10.0);
            columnConstraints.setPrefWidth(100.0);
            gridPane.getColumnConstraints().add(columnConstraints);
            Label label = new Label(convertName(key));
            label.setFont(Font.font("System", FontWeight.BOLD, 15));
            label.setWrapText(true);
            gridPane.add(label, index++, 0);
            Insets labelInsets = new Insets(10.0);
            GridPane.setMargin(label, labelInsets);
        }
        this.getChildren().add(gridPane);
    }
}

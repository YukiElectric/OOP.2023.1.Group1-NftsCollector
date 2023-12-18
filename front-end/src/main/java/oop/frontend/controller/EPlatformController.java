package oop.frontend.controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import java.util.Map;
import java.util.Set;

public class EPlatformController extends VBox {
    public EPlatformController(Map<String , String> data) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(10.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        gridPane.getRowConstraints().add(rowConstraints);

        Set<String> keys = data.keySet();
        int index = 0;
        for (String key : keys) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHalignment(HPos.CENTER);
            columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            columnConstraints.setMinWidth(10.0);
            columnConstraints.setPrefWidth(100.0);
            gridPane.getColumnConstraints().add(columnConstraints);
            Label label = new Label(data.get(key));
            label.setWrapText(true);
            gridPane.add(label, index++, 0);
            Insets labelInsets = new Insets(10.0);
            GridPane.setMargin(label, labelInsets);
        }
        this.getChildren().add(gridPane);
    }
}


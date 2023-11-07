package oop.frontend.jfxutils;

import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;

public class TabPaneUtil {
    private static void setTabPane(ToggleGroup menu, TabPane tabPane, int index) {
        menu.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
        menu.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (menu.getSelectedToggle() != null) {
                ToggleButton selectedButton = (ToggleButton) menu.getSelectedToggle();
                int selectedIndex = menu.getToggles().indexOf(selectedButton);
                tabPane.getSelectionModel().select(selectedIndex + index);
            }
        });
    }

    public static void setSelection(ToggleGroup menu, TabPane tabPane) {
        menu.getToggles().get(0).setSelected(true);
        setTabPane(menu, tabPane, 0);
    }

    public static void setSelection(ToggleGroup menu, TabPane tabPane, int index) {
        setTabPane(menu, tabPane, index);
    }

    public static void resetIndexTabPane(ToggleGroup menu, ToggleGroup topMenu, ToggleGroup trendingMenu) {
        menu.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                topMenu.getToggles().get(0).setSelected(true);
                trendingMenu.getToggles().get(0).setSelected(true);
            }
        }));
    }

    public static void setViewChange(ToggleGroup toggleGroup, VBox vBox) {
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
                int selectedIndex = toggleGroup.getToggles().indexOf(selectedButton);
                switch (selectedIndex) {
                    case 0:
                        VboxViewUtil.setViewViewVBox(vBox, Constants.API_URL, "");
                        break;
                }
            }
        });
    }
}

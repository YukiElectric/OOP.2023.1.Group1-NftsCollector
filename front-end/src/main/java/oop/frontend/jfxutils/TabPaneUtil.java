package oop.frontend.jfxutils;

import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.controller.BlogController;
import oop.frontend.controller.EPlatformController;
import oop.frontend.controller.PostController;

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

    public static void resetIndexTabPane(ToggleGroup menu, ToggleGroup topMenu, ToggleGroup trendingMenu, ToggleGroup analyzeMenu) {
        menu.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                topMenu.getToggles().get(0).setSelected(true);
                trendingMenu.getToggles().get(0).setSelected(true);
                analyzeMenu.getToggles().get(0).setSelected(true);
            }
        }));
    }

    public static void setViewChange(ToggleGroup toggleGroup, VBox postView, VBox hotTagView, VBox trendingView, VBox topView, VBox blogView, VBox analyzeView ,int index, String url) {
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
                int selectedIndex = toggleGroup.getToggles().indexOf(selectedButton);
                if (index == 0)
                    switch (selectedIndex) {
                        case 0 -> VboxViewUtil.setViewVBox(postView, Constants.API_URL, "post?search=", PostController.class, 0);
                        case 1 -> VboxViewUtil.setViewVBox(blogView, Constants.API_URL, "blog", BlogController.class, 0);
                        case 2 -> VboxViewUtil.setViewVBox(hotTagView, Constants.API_URL, "hottag", EPlatformController.class, 1);
                        case 3 -> VboxViewUtil.setViewVBox(trendingView, Constants.API_URL, "trending/opensea/AllTime", EPlatformController.class, 1);
                        case 4 -> VboxViewUtil.setViewVBox(topView, Constants.API_URL, "top/opensea/AllTime", EPlatformController.class, 1);
                        case 5 -> LineChartUtil.getInstance().setView(analyzeView,Constants.API_URL, "analysis/top/opensea"); /// thay cái này bằng api để lấy phân tích của opensea
                        default -> {
                        }
                    }
                else
                    switch (selectedIndex) {
                        case 0 -> VboxViewUtil.setViewVBox(postView, Constants.API_URL, url+"/opensea/AllTime", EPlatformController.class, 1);
                        case 1 -> VboxViewUtil.setViewVBox(hotTagView, Constants.API_URL, url+"/binance/AllTime", EPlatformController.class, 1);
                        case 2 -> VboxViewUtil.setViewVBox(trendingView, Constants.API_URL, url+"/niftygateway/AllTime", EPlatformController.class, 1);
                        case 3 -> VboxViewUtil.setViewVBox(topView, Constants.API_URL, url+"/rarible/AllTime", EPlatformController.class, 1);
                        default -> {
                        }
                    }
            }
        });
    }
}

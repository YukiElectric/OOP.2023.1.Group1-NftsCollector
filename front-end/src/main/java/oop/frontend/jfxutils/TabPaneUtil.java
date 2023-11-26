package oop.frontend.jfxutils;

import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.controller.PostItemController;
/**
 * Các phương thức tiện ích để quản lý TabPane trong ứng dụng JavaFX.
 */
public class TabPaneUtil {

    /**
     * Thiết lập chức năng cho TabPane khi chọn các mục trong ToggleGroup.
     *
     * @param menu    ToggleGroup chứa các nút để chọn Tab.
     * @param tabPane TabPane cần được điều khiển.
     * @param index   Chỉ số ban đầu của TabPane.
     */    private static void setTabPane(ToggleGroup menu, TabPane tabPane, int index) {
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
    /**
     * Thiết lập chọn mặc định cho TabPane và ToggleGroup.
     *
     * @param menu    ToggleGroup chứa các nút để chọn Tab.
     * @param tabPane TabPane cần được điều khiển.
     */
    public static void setSelection(ToggleGroup menu, TabPane tabPane, int index) {
        setTabPane(menu, tabPane, index);
    }
    /**
     * Thiết lập lại chỉ số của TabPane khi chọn mục trong ToggleGroup.
     *
     * @param menu        ToggleGroup chứa các nút để chọn Tab.
     * @param topMenu     ToggleGroup chứa các nút trong phần Top menu.
     * @param trendingMenu ToggleGroup chứa các nút trong phần Trending menu.
     */
    public static void resetIndexTabPane(ToggleGroup menu, ToggleGroup topMenu, ToggleGroup trendingMenu) {
        menu.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                topMenu.getToggles().get(0).setSelected(true);
                trendingMenu.getToggles().get(0).setSelected(true);
            }
        }));
    }
    /**
     * Thiết lập sự thay đổi giao diện khi chọn mục trong ToggleGroup.
     *
     * @param toggleGroup   ToggleGroup chứa các nút để chọn Tab.
     * @param postView      VBox hiển thị danh sách bài đăng.
     * @param hotTagView    VBox hiển thị danh sách hot tags.
     * @param trendingView  VBox hiển thị danh sách nổi bật.
     * @param topView       VBox hiển thị danh sách top.
     * @param index         Chỉ số ban đầu của TabPane.
     */
    public static void setViewChange(ToggleGroup toggleGroup, VBox postView, VBox hotTagView, VBox trendingView, VBox topView, int index) {
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
                int selectedIndex = toggleGroup.getToggles().indexOf(selectedButton);
                if (index == 0)
                    switch (selectedIndex) {
                        case 0 -> VboxViewUtil.setViewVBox(postView, Constants.API_URL, "", PostItemController.class);
                        case 1 -> VboxViewUtil.setViewVBox(hotTagView, Constants.API_URL, "", PostItemController.class);
                        case 2 -> VboxViewUtil.setViewVBox(trendingView, Constants.API_URL, "", PostItemController.class);
                        case 3 -> VboxViewUtil.setViewVBox(topView, Constants.API_URL, "", PostItemController.class);
                        default -> {
                        }
                    }
                else
                    switch (selectedIndex) {
                        case 0 -> VboxViewUtil.setViewVBox(postView, Constants.API_URL, "", PostItemController.class);
                        case 1 -> VboxViewUtil.setViewVBox(hotTagView, Constants.API_URL, "", PostItemController.class);
                        case 2 -> VboxViewUtil.setViewVBox(trendingView, Constants.API_URL, "", PostItemController.class);
                        case 3 -> VboxViewUtil.setViewVBox(topView, Constants.API_URL, "", PostItemController.class);
                        default -> {
                        }
                    }
            }
        });
    }
}

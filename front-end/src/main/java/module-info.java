module oop.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.web;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens oop.frontend to javafx.fxml;
    opens oop.frontend.controller to javafx.fxml;
    exports oop.frontend;
}

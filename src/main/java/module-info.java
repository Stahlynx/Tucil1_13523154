module com.control {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.control to javafx.fxml;
    exports com.control;
}

module com.mycompany.s_gestio_hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql; 
    //requires javafx.swing;
    requires java.desktop; 
    requires javafx.base;
    opens com.mycompany.s_gestio_hotel to javafx.fxml;
    exports com.mycompany.s_gestio_hotel;
}
module com.mycompany.sistema_gestio_hotel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.sistema_gestio_hotel to javafx.fxml;
    exports com.mycompany.sistema_gestio_hotel;
}

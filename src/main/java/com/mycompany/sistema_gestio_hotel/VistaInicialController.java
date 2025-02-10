package com.mycompany.sistema_gestio_hotel;

import java.io.IOException;
import javafx.fxml.FXML;

public class VistaInicialController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}

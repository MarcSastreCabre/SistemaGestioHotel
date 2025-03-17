/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel;

import com.mycompany.s_gestio_hotel.model.Factura;
import com.mycompany.s_gestio_hotel.model.GesitioDades;
import com.mycompany.s_gestio_hotel.model.Model;
import com.mycompany.s_gestio_hotel.model.Reserva;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.GestureEvent;

/**
 *
 * @author alumne
 */
public class FacturaController {
    Model model;
    private static Factura factura;
    private static Reserva reserva;
    @FXML
    TextField id_factura;
    @FXML
    DatePicker data_emissio;
    @FXML 
    TextField base_imposable;
    @FXML
    TextField iva;
    @FXML
    TextField total;
    @FXML
    ComboBox metode_pagament;
    @FXML
    Button crearFact;
    public void injecta(Model obj) {
        model = obj;
    }

    public static void setFactura(Factura factura) {
        FacturaController.factura = factura;
    }

    public static void setReserva(Reserva reserva) {
        FacturaController.reserva = reserva;
    }
    
    public void initialize(){
        metode_pagament.setItems(FXCollections.observableArrayList("Targeta","Efectiu", "PayPal", "Transferencia"));
        if(factura != null){
            id_factura.setText(""+factura.getId_factura());
            data_emissio.setValue(factura.getData_emisio().toLocalDate());
            base_imposable.setText(""+factura.getBaseimposable());
            iva.setText(""+factura.getIva());
            total.setText(""+factura.getTotal());
            metode_pagament.getSelectionModel().select(factura.getMetode_pagament());
            if(factura.getMetode_pagament() != null){
                metode_pagament.setDisable(true);
                crearFact.setVisible(false);
            } else{
                metode_pagament.setDisable(false);
                crearFact.setVisible(true);
            }
        }else{
            //error
        }
    }
    @FXML
    private void crearFactura() throws SQLException{
        if(reserva != null && metode_pagament.getSelectionModel().getSelectedIndex() != -1){
            GesitioDades gd = new GesitioDades();
            factura.setMetode_pagament(metode_pagament.getSelectionModel().getSelectedItem().toString());
            reserva.setFactura(factura);
            // pujar a base de dades
            gd.afegeixFactura(factura);
            metode_pagament.setDisable(true);
            crearFact.setVisible(false);
        }
    }
    
    @FXML
    private void switchToReserva() throws IOException {
        App.setRoot("reserva");
    }
    
}

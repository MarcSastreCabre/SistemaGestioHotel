/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel;

import com.mycompany.s_gestio_hotel.model.Client;
import com.mycompany.s_gestio_hotel.model.ClientEmpleat;
import com.mycompany.s_gestio_hotel.model.Factura;
import com.mycompany.s_gestio_hotel.model.GesitioDades;
import com.mycompany.s_gestio_hotel.model.Habitacio;
import com.mycompany.s_gestio_hotel.model.Model;
import com.mycompany.s_gestio_hotel.model.Reserva;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author alumne
 */
public class ReservaController {
    Model model;
    private static Reserva reserva;
    @FXML
    TextField id_reserva;
    @FXML 
    ComboBox client;
    @FXML
    DatePicker dataReserva;
    @FXML
    DatePicker dataInici;
    @FXML
    DatePicker dataFi;
    @FXML
    ComboBox tipusReserva;
    @FXML
    TextField tipus_iva;
    @FXML
    TextField preu_total;
    @FXML
    ComboBox habitacio;
    @FXML
    ListView reserves;
    @FXML 
    Button crear_gen_veu_fact; 
    @FXML 
    Button mk_edt_res;
    public void initialize(){
        client.setItems(FXCollections.observableArrayList(model.getClient().values()));// aixo s'aura de cambiar i actualitzar
        tipusReserva.setItems(FXCollections.observableArrayList("PM","AD"));
        habitacio.setItems(FXCollections.observableArrayList(model.getHabitacions().values()));
        //crear_gen_veu_fact.setText("Generar Factura");
        crear_gen_veu_fact.setVisible(false);
        mk_edt_res.setText("Crear");
        mk_edt_res.setVisible(true);
        itemsDisable(false);
        id_reserva.setText(""+Reserva.getLastIdReserva());
        if(reserva != null){
            reCargarReserva();
        }
    }
    
    public void injecta(Model obj) {
        model = obj;
    }
    public static void setReserva(Reserva r){
        reserva = r;
    }
    
    private void reCargarReserva() {
        reserves.getSelectionModel().select(reserva);
        crear_gen_veu_fact.setVisible(true);
        if(reserva.getFactura() != null){
            crear_gen_veu_fact.setText("Veure facutra");
        mk_edt_res.setVisible(false);
            itemsDisable(true);
        } else {
            crear_gen_veu_fact.setText("Generar Factura");
        mk_edt_res.setVisible(true);
        mk_edt_res.setText("Editar");
            itemsDisable(false);
        }
        //crear_gen_veu_fact.setText("Generar Factura");
        id_reserva.setText(""+reserva.getId_reserva());
        //reserves.selectionModelProperty().setValue(model.getClient().get(reserva.getId_client()));
        Object o = model.getClient().get(reserva.getId_client());
        client.getSelectionModel().select(o);

        reserves.setItems(FXCollections.observableArrayList(model.convertClient(o).getReserves()));
        
        //
        dataReserva.setValue(reserva.getData_reserva().toLocalDate());
        dataInici.setValue(reserva.getData_inici().toLocalDate());
        dataFi.setValue(reserva.getData_fi().toLocalDate());
        tipusReserva.getSelectionModel().select(reserva.getTipus_reserva());
        preu_total.setText(""+reserva.getPreu_total_reserva());
        tipus_iva.setText(""+reserva.getTipus_iva());
        habitacio.getSelectionModel().select(model.getHabitacions().get(reserva.getId_habitacio()));
    }
    @FXML
    private void treureSeleccio(){
        netejarCamps();
        reserves.getSelectionModel().select(null);
        reserva = null;
    }
    @FXML
    private void cambiarClient(){
        if(reserves.getSelectionModel().getSelectedIndex() != -1){
            netejarCamps();
        }
        reserves.setItems(FXCollections.observableArrayList(model.convertClient(client.getSelectionModel().getSelectedItem()).getReserves()));
        reserva = null;
    }
    @FXML
    private void reservesSelected(){
        Reserva r = (Reserva) reserves.getSelectionModel().getSelectedItem();
        if(r != null){
            reserva = r;
            reCargarReserva();
        }
    }
    private void netejarCamps(){
        id_reserva.setText(""+Reserva.getLastIdReserva());
        itemsDisable(false);
        mk_edt_res.setVisible(true);
        mk_edt_res.setText("Crear");
        crear_gen_veu_fact.setVisible(false);
        //client.getSelectionModel().clearSelection();
        dataReserva.setValue(null);
        dataInici.setValue(null);
        dataFi.setValue(null);
        tipusReserva.getSelectionModel().clearSelection();
        tipus_iva.clear();
        preu_total.clear();
        habitacio.getSelectionModel().select(null);
        //reserves.setItems(null);
    }
    private void itemsDisable(boolean b){
        id_reserva.setDisable(b);
        //client.getSelectionModel().clearSelection();
        dataReserva.setDisable(b);
        dataInici.setDisable(b);
        dataFi.setDisable(b);
        tipusReserva.setDisable(b);
        tipus_iva.setDisable(b);
        preu_total.setDisable(b);
        habitacio.setDisable(b);
        //reserves.setItems(null);
    }
    @FXML
    private void switchToInici() throws IOException {
        reserva = null;
        App.setRoot("inici");
        
    }
    
    @FXML
    private void switchToClient() throws IOException {
        PersonaController.setPersona(client.getSelectionModel().getSelectedItem());
        reserva = null;
        App.setRoot("persona");
    }
    
    @FXML
    private void switchToFacturaAndCreateFact() throws IOException {
        if(reserva != null){
            if(reserva.getFactura() != null){
                FacturaController.setFactura(reserva.getFactura());
                App.setRoot("factura");
            } else{                        //(int id_factura, Date data_emisio, String metode_pagament, double baseimposable, int iva, double total)
                FacturaController.setFactura(new Factura(reserva.getId_reserva(), Date.valueOf(LocalDate.now()), null, Double.parseDouble(preu_total.getText().replace(",", "."))/1+Integer.parseInt(tipus_iva.getText()), Integer.parseInt(tipus_iva.getText()), Double.parseDouble(preu_total.getText().replace(",", "."))));
                FacturaController.setReserva(reserva);
                App.setRoot("factura");
            }
            
        }
    }
    @FXML
    private void crearEditarReserva() throws SQLException{
        if(validarReserva()){
            GesitioDades gd = new GesitioDades();
            Client c = model.convertClient(client.getSelectionModel().getSelectedItem());
            Habitacio h = (Habitacio) habitacio.getSelectionModel().getSelectedItem();
                    
            
            //(int id_reserva, Date data_reserva, Date data_inici, Date data_fi, String tipus_reserva, int tipus_iva, double preu_total_reserva, int id_client, Factura factura)
            Reserva r = new Reserva(Integer.parseInt(id_reserva.getText()), Date.valueOf(dataReserva.getValue()), Date.valueOf(dataInici.getValue()), Date.valueOf(dataFi.getValue()), tipusReserva.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(tipus_iva.getText()), Double.parseDouble(preu_total.getText().replace(",", ".")), c.getId_client(), h.getId_habitacio(), null);
            //c.getReserves().add(r);
            if(!model.getReserves().contains(r)){
                if(reserves.getSelectionModel().getSelectedIndex() != -1){
                    gd.modificarReserva(r);
                    c.getReserves().remove(r);
                    model.getReserves().remove(r);
                    System.out.println("        Hola");
                } else{
                    gd.afegeixReserva(r);
                }
                c.getReserves().add(r);
                model.getReserves().add(r);
                reserves.setItems(FXCollections.observableArrayList(c.getReserves()));
                reserves.getSelectionModel().select(r);
                reserva = r;
                System.out.println(r);
            } else {
                System.out.println("        Error la reserva ja existeix"+model.getReserves());
                for (Reserva reserve : model.getReserves()) {
                    if(reserve.equals(r)){
                        System.out.println("La reserva es: "+reserve);
                    }
                }
            }
        }
    }
    
    
    private boolean validarReserva(){
        if(     
                id_reserva.getText().isEmpty() ||  
                client.getSelectionModel().isEmpty() || 
                dataReserva.getValue() == null || 
                dataInici.getValue() == null || 
                dataFi.getValue() == null || 
                tipusReserva.getSelectionModel().isEmpty() || 
                tipus_iva.getText().isEmpty() || 
                preu_total.getText().isEmpty() ||
                habitacio.getSelectionModel().isEmpty()){
            return false;
        }
        if(!dataInici.getValue().isBefore(dataFi.getValue()) || dataReserva.getValue().isAfter(dataInici.getValue())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error dades!!!");
            alert.setContentText("No s'han introduit/modificat be les dades");
            alert.show(); 
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void calcularPreu(){
        if(     
                dataInici.getValue() != null &&
                dataFi.getValue() != null &&
                !tipusReserva.getSelectionModel().isEmpty() &&
                !tipus_iva.getText().isEmpty() &&
                !habitacio.getSelectionModel().isEmpty() &&
                !model.isNotInt(tipus_iva.getText())){
            Habitacio h = (Habitacio) habitacio.getSelectionModel().getSelectedItem();
            double preuNit;
            if(tipusReserva.getSelectionModel().getSelectedItem().equals("AD")){
                preuNit = h.getPreu_nit_AD();
            } else {
                preuNit = h.getPreu_nit_PM();
            }
            DecimalFormat df = new DecimalFormat("#.##");
            int dias = dataInici.getValue().until(dataFi.getValue()).getDays();
            int iva = Integer.parseInt(tipus_iva.getText());
            double ivaDouble = (iva+100f)/100f;
            System.out.println(ivaDouble +" "+(iva+100f));
            preu_total.setText(""+df.format(preuNit*dias*ivaDouble));
        } else {
            preu_total.clear();
        }
    }
    
    
    
}

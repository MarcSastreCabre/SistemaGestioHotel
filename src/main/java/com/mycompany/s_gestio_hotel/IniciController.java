package com.mycompany.s_gestio_hotel;

import com.mycompany.s_gestio_hotel.model.*;
//import com.mycompany.s_gestio_hotel.model.GesitioDades;
//import com.mycompany.s_gestio_hotel.model.Model;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class IniciController {
    // declaro les variables 
    Model model;
    @FXML
    private ListView llPersones;
    @FXML
    private ListView llReserves;
    @FXML
    private ListView llTasques;
    @FXML
    private CheckBox filtratAut;
    @FXML
    private Button filtarPersona;
    @FXML
    private Button filtarReserva;
    @FXML
    private Button filtarTasca;
    
    // quan vull treure les seleccions les trec i cargo les inicials
    @FXML
    private void treureSeleccions(){
        llPersones.setItems(null);
        llReserves.setItems(null);
        llTasques.setItems(null);
        llPersones.setItems(FXCollections.observableArrayList(model.getPersones().values()));
        llReserves.setItems(FXCollections.observableArrayList(model.getReserves()));
        llTasques.setItems(model.getTasquesListF());
    }
    //Gal inicialitzar cargo totes les dades necesaries
    public void initialize(){
        //gd.llistaUsuaris2();
        llPersones.setItems(FXCollections.observableArrayList(model.getPersones().values()));
        llReserves.setItems(FXCollections.observableArrayList(model.getReserves()));
        llTasques.setItems(FXCollections.observableArrayList(model.getTasquesListF()));
        filtarPersona.setVisible(true);
        filtarReserva.setVisible(true);
        filtarTasca.setVisible(true);
    }
    // per filtrar una persona selecciono la classe de els atributs corresponents que te i necesito filtrar
    public void filtrarPersona(){
        Object o = llPersones.getSelectionModel().getSelectedItem();
        if(o != null){
            if(o.getClass() == Client.class){
                llReserves.setItems(FXCollections.observableArrayList(((Client) o).getReserves()));
                llTasques.setItems(null);
            } else if(o.getClass() == Empleat.class){
                llReserves.setItems(null);
                llTasques.setItems(FXCollections.observableArrayList(((Empleat) o).getTascaObsList()));
                //(Empleat) o).getReserves();
                //llTasques.setItems(FXCollections.observableArrayList();
                //llReserves.setItems(null);
            } else if(o.getClass() == ClientEmpleat.class){
                llReserves.setItems(FXCollections.observableArrayList((((ClientEmpleat) o).getClient().getReserves())));
                //llTasques.setItems(model.filtrarTasca(((ClientEmpleat) o)));
                llTasques.setItems(FXCollections.observableArrayList(((ClientEmpleat) o).getEmpleat().getTascaObsList()));
            }
            
        }
    }
    // quan activo el filtrar automatic no cal que es seleccioni el boto
    public void filtrarPersontaAut(){
        if(filtratAut.isSelected()){
            filtrarPersona();
        }
    }
    // per filtrar cada un dels elements
    public void filtrarReserva(){
        Object r = llReserves.getSelectionModel().getSelectedItem();
        if(r != null && r.getClass() == Reserva.class){
            llPersones.setItems(FXCollections.observableArrayList(model.buscarClient(((Reserva) r).getId_client())));
        }
        //llPersones.setItems(FXCollections.observableArrayList(model.getPersones()));
    }
    public void filtrarReservaAut(){
        if(filtratAut.isSelected()){
            filtrarReserva();
        }
    }
    public void filtrarTasca(){
        Object t = llTasques.getSelectionModel().getSelectedItem();
        if(t != null && t.getClass() == Tasca.class){
            llPersones.setItems(FXCollections.observableArrayList(((Tasca) t).getEmpl_tasca_est_val()));
        }
        //llPersones.setItems(FXCollections.observableArrayList(model.getPersones()));
    }
    public void filtrarTascaAut(){
        if(filtratAut.isSelected()){
            filtrarTasca();
        }
    }
    public void injecta(Model obj) {
        model = obj;
    }
    // per anar a cada una de les classes
    @FXML
    private void switchToPersona() throws IOException {
        App.setRoot("persona");
    }
    @FXML
    private void switchToReserva() throws IOException {
        App.setRoot("reserva");
    }
    @FXML
    private void switchToTasca() throws IOException {
        App.setRoot("tasca");
    }
    // per editar
    @FXML
    private void editPersona() throws IOException {
        Object pers = llPersones.getSelectionModel().getSelectedItem();
        if(pers != null){
            PersonaController.setPersona(pers);
            switchToPersona();
            
        }

    }
    @FXML
    private void editReserva() throws IOException{
        //Object pers = 
        Reserva r = (Reserva) llReserves.getSelectionModel().getSelectedItem();
        if(r != null){
            ReservaController.setReserva(r);
            switchToReserva();
        }
    }
    @FXML
    private void editTasca() throws IOException{
        Tasca t = (Tasca) llTasques.getSelectionModel().getSelectedItem();
        if(t != null){
            TascaController.setTasca(t);
            switchToTasca();
        }
    }
    // per quan filtres automaticament s'oculti tot
    @FXML
    private void ocultarSelectors(){
        filtarPersona.setVisible(!filtratAut.isSelected());
        filtarReserva.setVisible(!filtratAut.isSelected());
        filtarTasca.setVisible(!filtratAut.isSelected());
    }
    
}

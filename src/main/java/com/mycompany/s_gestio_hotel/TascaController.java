/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel;

import com.mycompany.s_gestio_hotel.model.Model;
import com.mycompany.s_gestio_hotel.model.Tasca;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author alumne
 */
public class TascaController {
    Model model;
    private static Tasca tasca;
    @FXML
    TextField id_tasca;
    @FXML
    TextArea descripcio;
    @FXML
    DatePicker dataCreacio;
    @FXML
    DatePicker dataExecucio;
    @FXML
    ComboBox estat;
    @FXML
    ListView empleats;
    @FXML
    Button seleccionar_iniciar_compleatar;
    public void injecta(Model obj) {
        model = obj;
    }

    public static void setTasca(Tasca tasca) {
        TascaController.tasca = tasca;
    }
    public void initialize(){
        id_tasca.setText(""+Tasca.getNextId());
        id_tasca.setEditable(false);
        System.out.println("    Ha entrat a tasca");
        estat.setItems(FXCollections.observableArrayList("Finalitzat", "En curs", "Pendent"));
        if(tasca != null){
            reCargarTasca();
            for (String string : tasca.getEmpl_tasca_est().keySet()) {
                for (Object object : tasca.getEmpl_tasca_est().get(string)) {
                    System.out.println("        "+object+"      Estat: "+string);
                }
            }
        } else{
            
        }
    }
    private void reCargarTasca(){
        id_tasca.setText(""+tasca.getId_tasca());
        descripcio.setText(tasca.getDescripcio());
        dataCreacio.setValue(tasca.getData_creacio().toLocalDate());
        dataExecucio.setValue(tasca.getData_execusio().toLocalDate());
        estat.getSelectionModel().select(tasca.getEstat());
        empleats.setItems(FXCollections.observableArrayList(tasca.getEmpl_tasca_est_val_est()));// s'ha de modificar perque surti els empleats que han completat la tasca per ordre
        //empleats.setItems(model.filtrarTascaOEmpleat(tasca.getEmpl_tasca_est()));
    }
    
    private void netejarTasca(){
        id_tasca.clear();
        descripcio.clear();
        dataCreacio.setValue(LocalDate.now());
        dataExecucio.setValue(null);
        estat.getSelectionModel().clearSelection();
        //empleats.setItems(FXCollections.observableArrayList(tasca.getEmpl_tasca_est_val_est()));// s'ha de modificar perque surti els empleats que han completat la tasca per ordre
        //empleats.setItems(model.filtrarTascaOEmpleat(tasca.getEmpl_tasca_est()));
    }
    @FXML
    private void switchToInici() throws IOException {
        App.setRoot("inici");
        tasca = null;
    }
}

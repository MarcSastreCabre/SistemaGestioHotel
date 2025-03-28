/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s_gestio_hotel;

import com.mycompany.s_gestio_hotel.model.Empleat;
import com.mycompany.s_gestio_hotel.model.GesitioDades;
import com.mycompany.s_gestio_hotel.model.Model;
import com.mycompany.s_gestio_hotel.model.Tasca;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    //Declaro els elements fets servir
    GesitioDades gd = new GesitioDades();
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
    @FXML
    ListView tasques;
    @FXML
    ComboBox empleatsNoSel;
    //aquest metode serveix per si vull editar directament el model
    public void injecta(Model obj) {
        model = obj;
    }
    //El seguent metode serveix per injectar la tasca i cargar una ja creada
    public static void setTasca(Tasca tasca) {
        TascaController.tasca = tasca;
    }
    //al inicialitzar cargo els elements necesaris, si he pasat una tasca crido el metode recargar tasca
    public void initialize(){
        id_tasca.setText(""+Tasca.getNextId());
        id_tasca.setEditable(false);
        System.out.println("    Ha entrat a tasca");
        tasques.setItems(model.getTasquesListF());
        estat.setItems(FXCollections.observableArrayList("Finalitzat", "En curs", "Pendent"));
        dataCreacio.setValue(LocalDate.now());
        estat.getSelectionModel().select("Pendent");
        if(tasca != null){
            reCargarTasca();
            tasques.getSelectionModel().select(tasca);
            for (String string : tasca.getEmpl_tasca_est().keySet()) {
                for (Object object : tasca.getEmpl_tasca_est().get(string)) {
                    System.out.println("        "+object+"      Estat: "+string);
                }
            }
        } else{
            
        }
    }
    //el seguent metode serveix per cargar una tasca seleccionada
    private void reCargarTasca(){
        id_tasca.setText(""+tasca.getId_tasca());
        descripcio.setText(tasca.getDescripcio());
        dataCreacio.setValue(tasca.getData_creacio().toLocalDate());
        dataExecucio.setValue(tasca.getData_execusio().toLocalDate());
        empleats.setItems(FXCollections.observableArrayList(tasca.getEmpl_tasca_est_val_est()));// s'ha de modificar perque surti els empleats que han completat la tasca per ordre
        estat.getSelectionModel().select(tasca.getEstat());
        //empleats.setItems(model.filtrarTascaOEmpleat(tasca.getEmpl_tasca_est()));
        empleatsNoSel.setItems(FXCollections.observableArrayList(tasca.empleatsNoSeleccionats()));
    }
    // amb aixo trec els atriviuts
    private void netejarTasca(){
        id_tasca.setText(""+Tasca.getNextId());
        descripcio.clear();
        dataCreacio.setValue(LocalDate.now());
        dataExecucio.setValue(null);
        tasques.getSelectionModel().clearSelection();
        estat.getSelectionModel().select("Pendent");
        empleats.setItems(null);
        //empleats.setItems(FXCollections.observableArrayList(tasca.getEmpl_tasca_est_val_est()));// s'ha de modificar perque surti els empleats que han completat la tasca per ordre
        //empleats.setItems(model.filtrarTascaOEmpleat(tasca.getEmpl_tasca_est()));
    }
    //Aquest es el boto per anar al inici
    @FXML
    private void switchToInici() throws IOException {
        App.setRoot("inici");
        tasca = null;
    }
    // Quan deselecciones un empleat neteges
    @FXML
    private void deseleccionar(){
        tasca = null;
        netejarTasca();
    }
    // i quan selecciones una al listview recargues i cambies el id
    @FXML
    private void seleccionarTasca(){
        if(tasques.getSelectionModel().getSelectedIndex() != -1){
            tasca = (Tasca) tasques.getSelectionModel().getSelectedItem();
            reCargarTasca();
        }
    }
    // Per afegir un empleat afegeixo al empleat i afegeixo a la base de dades
    @FXML
    private void afegirEmpleat() throws SQLException{
        if(empleatsNoSel.getSelectionModel().getSelectedIndex() != -1){
            Object e =  empleatsNoSel.getSelectionModel().getSelectedItem();
            model.convertEmpleat(e).afegirTasca(tasca, "Pendent", e);
            reCargarTasca();
            gd.afegeixEmpleatTasca(model.convertEmpleat(e), tasca, "Pendent");
        }
    }
    // Per pujar un empleat en la posiscio de la tasca també haig de modificar la base de ades i cambiar el empleat de llista de la tasca
    @FXML
    private void PujarEmpleat(){
        if(empleats.getSelectionModel().getSelectedIndex() != -1 ){
            Object e =  empleats.getSelectionModel().getSelectedItem();
            if(e.getClass() != String.class){
            String estAct = model.convertEmpleat(e).pujarTasca(tasca, e);
            reCargarTasca();
            gd.modificarEmpleatTasca(model.convertEmpleat(e), tasca, estAct);
            gd.modificarEstatTasca(tasca);
            }
        }
    }
    // per validar la tasca comprovo que els camps estiguin plens, si no afegeixo un error
    private boolean validarTasca(){
        if(
                id_tasca.getText().isEmpty() ||
                descripcio.getText().isEmpty() ||
                dataCreacio.getValue() == null ||
                dataExecucio.getValue() == null ||
                estat.getSelectionModel().getSelectedIndex() == -1
                ){
             
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error dades!!!");
            alert.setContentText("No s'han introduit be les dades");
            alert.show(); 
            return false;
        }
        return true;
    }
    // quan creo una tasca haig recullo els metodes i els afegeixo a la base de dades
    @FXML
    private void crearEditarTasca() throws SQLException{
        if(validarTasca()){
            if(tasca == null){
                Tasca t = new Tasca(Integer.parseInt(id_tasca.getText()), descripcio.getText(), Date.valueOf(dataCreacio.getValue()), Date.valueOf(dataExecucio.getValue()), estat.getSelectionModel().getSelectedItem().toString());
                Model.getTasques().put(t.getId_tasca(), t);
                tasques.setItems(model.getTasquesListF());
                tasques.getSelectionModel().select(t);
                gd.afegeixTasca(t);
            } else {
                tasca.modificarAtribusTasca(descripcio.getText(), Date.valueOf(dataExecucio.getValue()));
            }
        }    
    }
    
}

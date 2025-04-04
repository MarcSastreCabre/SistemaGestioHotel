package com.mycompany.s_gestio_hotel;

import com.mycompany.s_gestio_hotel.model.Client;
import com.mycompany.s_gestio_hotel.model.ClientEmpleat;
import com.mycompany.s_gestio_hotel.model.Empleat;
import com.mycompany.s_gestio_hotel.model.GesitioDades;
import com.mycompany.s_gestio_hotel.model.Model;
import com.mycompany.s_gestio_hotel.model.Persona;
import com.mycompany.s_gestio_hotel.model.Reserva;
import com.mycompany.s_gestio_hotel.model.Tasca;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
public class PersonaController {
    //declaro tots els elements que faig servir
    Model model;
    private static Object persona;
    GesitioDades gd = new GesitioDades();
    @FXML
    private TextField ID;
    @FXML
    private TextField nom;
    @FXML
    private TextField cognom;
    @FXML
    private TextField adresa;
    @FXML
    private TextField DNI;
    @FXML
    private TextField telefon;
    @FXML
    private DatePicker data_naixement;
    @FXML
    private TextField email;
    @FXML
    private ListView persones;
    @FXML
    private CheckBox CBclient;
    @FXML
    private CheckBox CBempleat;
    @FXML
    private TextField id_client;
    @FXML
    private DatePicker data_registre;
    @FXML
    private ComboBox tip_client;
    @FXML
    private TextField targeta_credit;
    @FXML
    private ListView<Reserva> reserves;
    @FXML
    private TextField id_empleat;
    @FXML
    private TextField lloc_feina;
    @FXML
    private DatePicker data_contratacio;
    @FXML
    private TextField salariBrut;
    @FXML
    private TextField estatLaboral;
    @FXML
    private Button cre_assg_proc_fet;
    @FXML
    private Button afg_mod_per;
    @FXML
    private ListView tasques;
    @FXML
    private Tab pagClient;
    @FXML
    private Tab pagEmpleat;
    @FXML
    private TabPane tabPane;
    @FXML
    ComboBox tasquesNoSel;
         //initialize
    // al inicialitzar completo tos els elements de la forma que els necesito
    public void initialize(){
        DNI.setEditable(true);
        ID.setText(""+Persona.getNextId()); 
        id_client.setText(""+Client.getNextIdC()); 
        id_empleat.setText(""+Empleat.getNextIdE());
        data_registre.setValue(LocalDate.now());
        CBclient.setSelected(false);
        CBempleat.setSelected(false);
        pagClient.setDisable(true);
        pagEmpleat.setDisable(true);
        afg_mod_per.setText("Afegir");
        persones.setItems(FXCollections.observableArrayList(model.getPersones().values()));// optimitzar
        tip_client.setItems(FXCollections.observableArrayList("Regular", "VIP"));
        if(persona != null){
            // si hi ha una perosna la cargo
            reCargarPerosna();
        }
        
    }
    public void injecta(Model obj) {
        model = obj;
    }
    // per tornar al inici
    @FXML
    private void switchToInici() throws IOException {
        treureSeleccio();
        App.setRoot("inici");
    }
    // per anar a reserva seleccionada d'un client
    @FXML
    private void switchToReserva() throws IOException {
        Reserva r = (Reserva) reserves.getSelectionModel().getSelectedItem();
        if(r != null){
            ReservaController.setReserva(r);
            App.setRoot("reserva");
        }
    }
    // Per anar a una tasca seleccionada de un treballador
    @FXML
    private void switchToTasca() throws IOException {
        Tasca t = (Tasca) tasques.getSelectionModel().getSelectedItem();
        if(t != null && t.getClass() == Tasca.class){
            TascaController.setTasca(t);
            App.setRoot("tasca");
        }
    }
    // Per ingectar una persona quan vull cargar un element                                            
    public static void setPersona(Object persona) {
        PersonaController.persona = persona;
    }
    //per treure les seleccions netejo tot
    @FXML
    private void treureSeleccio(){
        persona = null;
        persones.getSelectionModel().clearSelection();
        clearAtributsPersona();
        clearAtributsClient();
        clearAtributsPersona();
        afg_mod_per.setText("Afegir");
    }
    // per recargar una persona cargo tots els elements
    private void reCargarPerosna(){
        DNI.setEditable(false);
            afg_mod_per.setText("Modificar");
        
        if(persona.getClass() == ClientEmpleat.class){
            CBclient.setSelected(true);
            CBempleat.setSelected(true);
            recargarPagines();
            ClientEmpleat ce = (ClientEmpleat) persona;
            complearAtributsPersona(ce.getClient());
            complearAtributsClient(ce.getClient());
            complearAtributsEmpleat(ce.getEmpleat());
            tabPane.getSelectionModel().select(0);
            
        } else{
            complearAtributsPersona(((Persona) persona));
            if(persona.getClass() == Client.class){
                
                CBclient.setSelected(true);
                CBempleat.setSelected(false);
                recargarPagines();
                complearAtributsClient((Client) persona);
                clearAtributsEmpleat();
                tabPane.getSelectionModel().select(0);
            }
            if(persona.getClass() == Empleat.class){
                CBempleat.setSelected(true);
                CBclient.setSelected(false);
                recargarPagines();
                complearAtributsEmpleat((Empleat) persona);
                clearAtributsClient();
                tabPane.getSelectionModel().select(1);
            }

        }
        persones.getSelectionModel().select(persona);
        
    }
    // La mecanica de CeckBox del empleat i del client
    @FXML
    private void selectCBEmpleat(){
        if (!CBempleat.isSelected()) {
            if (!CBclient.isSelected()) {
                // alerta?
                CBempleat.setSelected(true);
            } else{
                pagEmpleat.setDisable(true);
                tabPane.getSelectionModel().select(0);
            }
        } else{
            pagEmpleat.setDisable(false);
        }
    }

    @FXML
    private void selectCBClient(){
        if (!CBclient.isSelected()) {
            if (!CBempleat.isSelected()) {
                // alerta?
                CBclient.setSelected(true);
            } else{
                pagClient.setDisable(true);
                tabPane.getSelectionModel().select(1);
            }
        } else{
            pagClient.setDisable(false);
        }
        //pagClient.setDisable(!CBclient.isSelected());
    }
    
    // per quan selecciones una persona es carregui
    @FXML
    private void touchLVPersones(){
        Object p = persones.getSelectionModel().getSelectedItem();
        if(p != null){
            persona = p;
            reCargarPerosna();
        }
    }
    //per recargar les pagines
    private void recargarPagines(){
        pagClient.setDisable(!CBclient.isSelected());
        pagEmpleat.setDisable(!CBempleat.isSelected());
    }
    // per compleatar els atributs de una persona
    private void complearAtributsPersona(Persona p){
        ID.setText(""+p.getId_persona()); 
        nom.setText(p.getNom());
        cognom.setText(p.getCognom());
        adresa.setText(p.getAdresa());
        DNI.setText(p.getDNI());
        telefon.setText(""+p.getTelefon());
        //LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        data_naixement.setValue(p.getData_naixement().toLocalDate());
        email.setText(p.getEmail());
    }
    // i per netejar els seus camps
    private void clearAtributsPersona(){
        DNI.setEditable(true);
        ID.setText(""+Persona.getNextId()); 
        nom.clear();
        cognom.clear();
        adresa.clear();
        DNI.clear();
        telefon.clear();
        data_naixement.setValue(null);
        email.clear();
    }
    // per compleatar els atributs d'un client
    private void complearAtributsClient(Client c){
        id_client.setText(""+c.getId_client());
        data_registre.setValue(c.getData_registre().toLocalDate());
        tip_client.getSelectionModel().select(c.getTipus_client());
        targeta_credit.setText(c.getTargeta_credit());
        reserves.setItems(FXCollections.observableArrayList(c.getReserves()));
        
    }
    // per netejar els seus camps
    private void clearAtributsClient(){
        id_client.setText(""+Client.getNextIdC());
        data_registre.setValue(LocalDate.now());
        tip_client.getSelectionModel().select(null);
        targeta_credit.clear();
        reserves.setItems(null);
        
    }
    // per compleatar els atributs d'un empleat
    private void complearAtributsEmpleat(Empleat e){
        id_empleat.setText(""+e.getId_empleat());
        lloc_feina.setText(e.getLlocFeina());
        data_contratacio.setValue(e.getData_contratacio().toLocalDate());
        salariBrut.setText(""+e.getSalari_brut());
        estatLaboral.setText(e.getEstat_laboral());
        tasques.setItems(e.getTascaFiltObsList());
        tasquesNoSel.setItems(FXCollections.observableArrayList(e.tasquesNoSeleccionades()));
        
    }
    // per netejar els seus camps
    private void clearAtributsEmpleat(){
        id_empleat.setText(""+Empleat.getNextIdE());
        lloc_feina.clear();
        data_contratacio.setValue(null);
        salariBrut.clear();
        estatLaboral.clear();
        tasquesNoSel.setItems(model.getTasquesListF());
        
    }
    // el seguit de linees es quan modifiques / crees una persona ja sigui client o empleat es crea la classe i es puja a la base de dades
    @FXML
    private void crearModificarPersona() throws SQLException{
            if(validarDadesPersona()){
                if(CBclient.isSelected() && CBempleat.isSelected()){
                    ArrayList<Reserva> arr;
                    if(persones.getSelectionModel().getSelectedIndex() == -1){
                        arr = new ArrayList<>();
                    } else{
                        Client c = model.convertClient(persones.getSelectionModel().getSelectedItem());
                        if(c != null){
                            arr = c.getReserves();
                        }else {
                            arr = new ArrayList<>();
                        }
                    }
                    
                    Client c = new Client(Integer.parseInt(id_client.getText()), Date.valueOf(data_registre.getValue()), tip_client.getSelectionModel().getSelectedItem().toString(), targeta_credit.getText(), Integer.parseInt(ID.getText()), nom.getText(), cognom.getText(), adresa.getText(), DNI.getText(), Integer.parseInt(telefon.getText()), Date.valueOf(data_naixement.getValue()), email.getText(), arr);
                    Empleat e = new Empleat(Integer.parseInt(id_empleat.getText()), lloc_feina.getText(), Date.valueOf(data_contratacio.getValue()), Integer.parseInt(salariBrut.getText()), estatLaboral.getText(), Integer.parseInt(ID.getText()), nom.getText(), cognom.getText(), adresa.getText(), DNI.getText(), Integer.parseInt(telefon.getText()), Date.valueOf(data_naixement.getValue()), email.getText());
                    ClientEmpleat ce = new ClientEmpleat(c, e);

                    if(persones.getSelectionModel().getSelectedIndex() == -1){
                        if(model.getPersones().containsValue(ce)){
                            return;
                        }
                        gd.afegeixPersona(c);
                        gd.afegeixClients(c);
                        gd.afegeixEmpleats(e);
                        
                    } else{
                        gd.modificarPersona(e);
                        if(model.getPersones().get(e.getId_persona()).getClass() == ClientEmpleat.class){
                            gd.modificarClient(c);
                            gd.modificarEmpleat(e);
                        } else if(model.getPersones().get(e.getId_persona()).getClass() == Client.class){
                            gd.modificarClient(c);
                            gd.afegeixEmpleats(e);
                        } else if(model.getPersones().get(e.getId_persona()).getClass() == Empleat.class){
                            gd.modificarEmpleat(e);
                            gd.afegeixClients(c);
                        }

                    }
                    model.getPersones().put(e.getId_persona(), ce);
                    persones.setItems(FXCollections.observableArrayList(model.getPersones().values()));// optimitzar
                    model.getClient().put(c.getId_client(), ce);
                    model.getEmpleat().put(e.getId_empleat(), ce);
                    
                }
                else if(CBclient.isSelected()){
                    ArrayList<Reserva> arr;
                    if(persones.getSelectionModel().getSelectedIndex() == -1){
                        arr = new ArrayList<>();
                    } else{
                        Client c = model.convertClient(persones.getSelectionModel().getSelectedItem());
                        if(c != null){
                            arr = c.getReserves();
                        }else {
                            arr = new ArrayList<>();
                        }
                        //arr = ((ClientEmpleat) persones.getSelectionModel().getSelectedItem()).getClient().getReserves();
                    }
                    Client c = new Client(Integer.parseInt(id_client.getText()), Date.valueOf(data_registre.getValue()), tip_client.getSelectionModel().getSelectedItem().toString(), targeta_credit.getText(), Integer.parseInt(ID.getText()), nom.getText(), cognom.getText(), adresa.getText(), DNI.getText(), Integer.parseInt(telefon.getText()), Date.valueOf(data_naixement.getValue()), email.getText(), arr);
                    //if(!model.getPersones().containsValue(c)) {
                        //model.getPersones().re

                        if(persones.getSelectionModel().getSelectedIndex() == -1){
                            if(model.getPersones().containsValue(c)){
                                return;
                            }
                            gd.afegeixPersona(c);
                            gd.afegeixClients(c);
                        }else {
                        gd.modificarPersona(c);
                        if(model.getPersones().get(c.getId_persona()).getClass() == ClientEmpleat.class){
                            gd.modificarClient(c);
                            // eliminar ClientEmpleat i empleat
                            ClientEmpleat clEmp = (ClientEmpleat) model.getPersones().get(c.getId_persona());
                            model.getEmpleat().remove(clEmp.getEmpleat().getId_empleat());
                            gd.eliminarEmpleat(clEmp.getEmpleat());
                        } else if(model.getPersones().get(c.getId_persona()).getClass() == Client.class){
                            gd.modificarPersona(c);
                            gd.modificarClient(c);
                        } else if(model.getPersones().get(c.getId_persona()).getClass() == Empleat.class){
                            Empleat e = (Empleat) model.getPersones().get(c.getId_persona());
                            model.getEmpleat().remove(e.getId_empleat());
                            gd.eliminarEmpleat(e);
                            gd.afegeixClients(c);
                        }
                        //gd.modificarPersona(c);
                        gd.modificarClient(c);
                        }
                        model.getPersones().put(c.getId_persona(), c);
                        persones.setItems(FXCollections.observableArrayList(model.getPersones().values()));// optimitzar
                        model.getClient().put(c.getId_client(), c);
                    //}
                }
                else if(CBempleat.isSelected()){
                    Empleat e = new Empleat(Integer.parseInt(id_empleat.getText()), lloc_feina.getText(), Date.valueOf(data_contratacio.getValue()), Integer.parseInt(salariBrut.getText()), estatLaboral.getText(), Integer.parseInt(ID.getText()), nom.getText(), cognom.getText(), adresa.getText(), DNI.getText(), Integer.parseInt(telefon.getText()), Date.valueOf(data_naixement.getValue()), email.getText());
                    

                        if(persones.getSelectionModel().getSelectedIndex() == -1){
                            if(model.getPersones().containsValue(e)){
                                System.out.println("El empleat existeix");
                                return;
                                
                            }
                            gd.afegeixPersona(e);
                            gd.afegeixEmpleats(e);
                        } else {
                        gd.modificarPersona(e);
                        if(model.getPersones().get(e.getId_persona()).getClass() == ClientEmpleat.class){
                            gd.modificarEmpleat(e);
                            // eliminar ClientEmpleat i empleat
                            ClientEmpleat clEmp = (ClientEmpleat) model.getPersones().get(e.getId_persona());
                            model.getClient().remove(clEmp.getEmpleat().getId_empleat());
                            gd.eliminarEmpleat(clEmp.getEmpleat());
                        } else if(model.getPersones().get(e.getId_persona()).getClass() == Empleat.class){
                            gd.modificarPersona(e);
                            gd.modificarEmpleat(e);
                        } else if(model.getPersones().get(e.getId_persona()).getClass() == Client.class){
                            Client c = (Client) model.getPersones().get(e.getId_persona());
                            model.getClient().remove(c.getId_client());
                            gd.eliminarClient(c);
                            gd.afegeixEmpleats(e);
                        }
                        //gd.modificarPersona(c);
                        gd.modificarEmpleat(e);
                        }
                        model.getPersones().put(e.getId_persona(), e);
                        persones.setItems(FXCollections.observableArrayList(model.getPersones().values()));// optimitzarç
                        System.out.println("Empleat afegit");
                        model.getEmpleat().put(e.getId_empleat(), e);
              

                }
            }
        //} else {
            
        //}
    }
    // per quan afegeixes una tasca
    @FXML
    private void afegirTasca() throws SQLException{
        Object t = tasquesNoSel.getSelectionModel().getSelectedItem();
        if(persona != null && persona.getClass() != Client.class && t != null && t.getClass() == Tasca.class){
            Empleat e = model.convertEmpleat(persona);
            Tasca tas = (Tasca) t;
            //e.getTasca_est().putIfAbsent("Pendent", new LinkedList<>());
            //e.getTasca_est().get("Pendent").add(tas);
            e.afegirTasca(tas, "Pendent", persona);
            tasques.setItems(e.getTascaFiltObsList()); // optimitzar
            tasquesNoSel.setItems(FXCollections.observableArrayList(e.tasquesNoSeleccionades()));
            gd.afegeixEmpleatTasca(e, tas, "Pendent");
            
        }
    }
    // per validar les ades 
    private boolean validarDadesPersona(){// mes endevant ser mes complet i estricte
        if(
            ID.getText().isEmpty() ||
            nom.getText().isEmpty() ||
            cognom.getText().isEmpty() ||
            adresa.getText().isEmpty() ||
            DNI.getText().isEmpty() ||
            telefon.getText().isEmpty() ||
            data_naixement.getValue() == null ||
            email.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error dades!!!");
            alert.setContentText("No s'han introduit/modificat be les dades");
            alert.show(); 
                return false;
        }
        if(!data_naixement.getValue().isBefore(LocalDate.now()) || (DNI.getText().length() != 9)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error dades!!!");
            alert.setContentText("No s'han introduit/modificat be les dades");
            alert.show(); 
            return false;
        }
        if(model.isNotInt(ID.getText()) || model.isNotInt(telefon.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error dades!!!");
            alert.setContentText("No s'han introduit/modificat be les dades");
            alert.show(); 
            return false;
        }
         
        
        return true;
    }
    private boolean validarDadesClient(){
        if(
            id_client.getText().isEmpty() ||
            data_registre.getValue() == null ||
            tip_client.getSelectionModel().getSelectedIndex() == -1 ||
            targeta_credit.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error dades!!!");
            alert.setContentText("No s'han introduit/modificat be les dades");
            alert.show(); 
                return false;
        }
        return true;
    }
    private boolean validarDadesEmpleat(){
        if(
            id_empleat.getText().isEmpty() ||
            lloc_feina.getText().isEmpty() ||
            data_contratacio.getValue() == null ||
            salariBrut.getText().isEmpty() ||
            estatLaboral.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Error dades!!!");
            alert.setContentText("No s'han introduit/modificat be les dades");
            alert.show(); 
                return false;
        }
        return true;
        
    }
    // per pujar una tasca de nivell
    @FXML
    private void pujarTasca(){
        Object o = tasques.getSelectionModel().getSelectedItem();
        System.out.println("Hola1");
        if(o != null && o.getClass() == Tasca.class && (persona.getClass() == Empleat.class || persona.getClass() == ClientEmpleat.class)){
            System.out.println("Hola");
            Tasca t = (Tasca) o;
            String estAct = model.convertEmpleat(persona).pujarTasca(t, persona);
            gd.modificarEmpleatTasca(model.convertEmpleat(persona), t, estAct);
            gd.modificarEstatTasca(t);
        }
        tasques.setItems(model.convertEmpleat(persona).getTascaFiltObsList());
    }

}

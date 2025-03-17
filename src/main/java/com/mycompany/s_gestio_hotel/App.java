package com.mycompany.s_gestio_hotel;

import com.mycompany.s_gestio_hotel.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static javafx.application.Application.launch;

public class App extends Application {

    private static Scene scene;
    private Model model;
    private static IniciController iniciCont;
    private static PersonaController clientCont;
    private static ReservaController reservaCont;
    private static TascaController tascaCont;
    private static FacturaController factCont;

    @Override
    public void start(Stage stage) throws IOException {
        model = new Model();
        model.inicialitzar();
        iniciCont = new IniciController();
        clientCont = new PersonaController();
        reservaCont = new ReservaController();
        tascaCont = new TascaController();
        factCont = new FacturaController();
        //Injectar model en controlador
        iniciCont.injecta(model);
        clientCont.injecta(model);
        reservaCont.injecta(model);
        tascaCont.injecta(model);
        factCont.injecta(model);
        scene = new Scene(loadFXML("inici"), 850, 700);
        
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        
      fxmlLoader.setControllerFactory(controllerType -> {
           if (controllerType==IniciController.class){
               return iniciCont;
           }
            if (controllerType==PersonaController.class){
               return clientCont;
           }
           if (controllerType==ReservaController.class){
               return reservaCont;
           }
            if (controllerType==TascaController.class){
               return tascaCont;
           }
            if (controllerType==FacturaController.class){
                return factCont;
            }
            return null;
        });
        return fxmlLoader.load();
        }

    public static void main(String[] args) {
        launch();
    }

}
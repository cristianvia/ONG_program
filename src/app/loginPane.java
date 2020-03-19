/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import Controladores.fxmlController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Cristian
 */
public class loginPane extends AnchorPane {
    
    public loginPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Label lbl_Titulo;

    @FXML
    private TextField txtF_Usuario;

    @FXML
    private PasswordField psF_Password;

    @FXML
    private Label lbl_Error;

    @FXML
    private Button btn_acceder;

    @FXML
    void Acceder(ActionEvent event) throws IOException {
        fxmlController controller  = new fxmlController();     
        String rol = controller.login(txtF_Usuario.getText(), psF_Password.getText());
             switch (rol) {
         case "usuario":
             lbl_Error.setText("Área restringida para usuarios admin");
             loadView(event, "userView.fxml");
             break;
         case "admin":
             lbl_Error.setText("Bienvenido: " +txtF_Usuario.getText());
             loadView(event, "adminView.fxml");
             break;
         default:
             lbl_Error.setText("Credenciales incorrectas");
             break;
     }
    }
    private void loadView(ActionEvent event, String sceneName) throws IOException {
        Parent adminview_parent = FXMLLoader.load(getClass().getResource(sceneName));
        Scene adminview_scene = new Scene(adminview_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(adminview_scene);
        app_stage.show();
    }
}

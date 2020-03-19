/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import Controladores.fxmlController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Cristian
 */
public class UserViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
         @FXML
    private TableView<?> table;


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button showPC;

    @FXML
    void showPContratados(ActionEvent event) {
        fxmlController controller  = new fxmlController();
        table.getItems().clear();
        table.getColumns().clear();
        controller.buildData(table, "SELECT idPContratado, usuario, rol from pcontratado");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

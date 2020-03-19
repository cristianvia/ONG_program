/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import Controladores.fxmlController;
import DAO.DBContratadoDAO;
import P5.PContratado;
import P5.PContratados;
import P5.Rol;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Cristian
 */
public class AdminViewController implements Initializable {

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
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField dni;

    @FXML
    private TextField user;

    @FXML
    private PasswordField pass;

    @FXML
    private Button addPC;
    
    @FXML
    private Label lbl_Error;

    @FXML
    void addPContratados(ActionEvent event) {
        if (!name.getText().trim().isEmpty() && !surname.getText().trim().isEmpty() && !dni.getText().trim().isEmpty() && !user.getText().trim().isEmpty() && !pass.getText().trim().isEmpty()){
        PContratado pcontratado1 = new PContratado(name.getText(), surname.getText(), dni.getText(), user.getText(), pass.getText(), Rol.usuario);
        PContratados pcontratados = new PContratados();
        pcontratados.add(pcontratado1);
        try {
            //Creamos la variable DBContratadosDAO para poder trabajar con la clase PContratados y la base de datos
            DBContratadoDAO DBContratadosDAO = new DBContratadoDAO();
            //Guardamos los contratados almacenados en la variable pcontratados en la base de datos
            DBContratadosDAO.guardar(pcontratados);
            name.setText("");
            surname.setText("");
            dni.setText("");
            user.setText("");
            pass.setText("");
            lbl_Error.setText("Añadido correctamente");
            } catch (Exception e) {
            System.out.println(e);
        }
        }else{
        lbl_Error.setText("Debes escribir en TODOS los campos");
        }
    }
    
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

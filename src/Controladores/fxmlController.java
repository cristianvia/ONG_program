/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;
//<?import Controladores.*?>
//import graphic.FxmlAppPane;
import Entidades.Pcontratado;
import P5.utilidad; 
import java.util.List;
import javafx.scene.control.TableView;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Cristian
 */
//public class fxmlController extends Application {
public class fxmlController {

    PcontratadoJpaController CContratado = new PcontratadoJpaController();

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;
    //private TableView tableview;

//    //MAIN EXECUTOR
//    public static void main(String[] args) {
//        launch(args);
//    }

    //CONNECTION DATABASE
    public void buildData(TableView tableview, String SQL){
          utilidad u = new utilidad();
          data = FXCollections.observableArrayList();
          try{
            u.conectar();
            //SQL FOR SELECTING ALL OF CUSTOMER
            //String SQL = "SELECT idPContratado, usuario, rol from pcontratado";
            //ResultSet
            ResultSet rs = u.runQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tableview.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
      }

    public String login(String user, String password){
        utilidad u = new utilidad();
        String rol = "nada";
        String SQL = "SELECT rol FROM pcontratado WHERE usuario = '"+user+"' AND password = '"+password+"'";
          data = FXCollections.observableArrayList();
          try{
            u.conectar();
            //SQL FOR SELECTING ALL OF CUSTOMER
            //String SQL = "SELECT idPContratado, usuario, rol from pcontratado";
            //ResultSet
            ResultSet rs = u.runQuery(SQL);
            System.out.println(rs.next());
            if(rs.next()){
                rol = rs.getString("rol"); 
                System.out.println(rol);
            }
//
//      
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error en el login, comprueba que el usuario y la contraseña son correctos.");
              rol = "nada";
          }
          return rol;
    }
    
    //Show pContratado DATA
     /* @Override
      public void start(Stage stage) throws Exception {
        //TableView
        tableview = new TableView();
        buildData("SELECT idPContratado, usuario, rol from pcontratado");

        //Main Scene
        Scene scene = new Scene(tableview);        

        stage.setScene(scene);
        stage.show();
      }*/
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Cristian
 */

public class DBContratadoDAO implements IpContratadoDAO{
    
    public DBContratadoDAO() {
    }
    //sobrescribe la funcion de listar creada en la interfaz
    @Override
    public PContratados listarPContratados() throws JAXBException {
        PContratados pcontratados = new PContratados();
        utilidad u = new utilidad();
        try 
        {
            pcontratados = u.listarTablaPContratados();

            for(PContratado pcontratado : pcontratados.getPContratados())
            {
                System.out.println("---");
                System.out.println("Nombre del empleado: \t" + pcontratado.getNombre());
                System.out.println("DNI del empleado: \t" + pcontratado.getDNI());
                System.out.println("Usuario del empleado: \t" + pcontratado.getUsuario());
                System.out.println("Contraseña del empleado: \t" + pcontratado.getPassword());
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
        return pcontratados;
    }
    //sobrescribe la funcion de guardar creada en la interfaz
    @Override
    public void guardar(PContratados pcontratados) throws JAXBException {

        utilidad u = new utilidad();
        
        try {
            for(PContratado pcontratado : pcontratados.getPContratados())
            {
                u.AddPContratado(pcontratado);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
    }
    
}

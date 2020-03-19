/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
/**
 *
 * @author Cristian
 */
public abstract class DAOFactory {
    //Tipos de DAO soportados por la factoría
 public static final int XML = 1;
 public static final int MYSQL = 2;
 
 /*Debe haber un método para cada DAO que pueda ser creado
    La factoría concreta para cada tipo deberá implementar el metodo
     que le corresponde */
 
 public abstract XmlIPrivadosDAO getIPrivadosDAO() throws JAXBException;
 public abstract XmlIPublicosDAO getIPublicosDAO() throws JAXBException;
 public abstract XmlDelegacionDAO getDelegacionDAO() throws JAXBException;
 public abstract XmlLineaAccionDAO getLineaAccionDAO() throws JAXBException;
 public abstract XmlOngDAO getOngDAO() throws JAXBException;
 public abstract XmlPContratadoDAO getPContratadoDAO() throws JAXBException;
 public abstract XmlPersonaDAO getPersonaDAO() throws JAXBException;
 public abstract XmlProyectoDAO getProyectoDAO() throws JAXBException;
 public abstract XmlSedeCentralDAO getSedeCentralDAO() throws JAXBException;
 public abstract XmlSocioDAO getSocioDAO() throws JAXBException;

 
 
 public static DAOFactory getDAOFactory (int whichFactory){
     switch (whichFactory) {
         case XML:
             return new XmlDAOFactory();
         case MYSQL:
             return new MySqlDAOFactory();
         default:
               return null;
     }
 }
}

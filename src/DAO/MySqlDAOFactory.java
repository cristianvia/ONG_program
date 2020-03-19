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
public class MySqlDAOFactory extends DAOFactory {
    
    //Creamos un constructor vacio
    MySqlDAOFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //Modificamos las variables necesarias a modo que retornen una variable vacia
    public DBDelegacionDAO getDelegacionSQLDAO() throws JAXBException
            {
        return new DBDelegacionDAO();
    }
    public DBContratadoDAO getPContratadoSQLDAO() throws JAXBException
            {
        return new DBContratadoDAO();
    }

    public DBSedeCentralDAO getSedeCentralSQLDAO() throws JAXBException
            {
        return new DBSedeCentralDAO();
    }
    public DBSocioDAO getSocioSQLDAO() throws JAXBException
            {
        return new DBSocioDAO();
    }
    
    //El resto de funciones como no las utilizamos las hemos "anulado"
    @Override
    public XmlIPrivadosDAO getIPrivadosDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlIPublicosDAO getIPublicosDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlDelegacionDAO getDelegacionDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlLineaAccionDAO getLineaAccionDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlOngDAO getOngDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlPContratadoDAO getPContratadoDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlPersonaDAO getPersonaDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlProyectoDAO getProyectoDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlSedeCentralDAO getSedeCentralDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public XmlSocioDAO getSocioDAO() throws JAXBException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import javax.xml.bind.JAXBException;
/**
 *
 * @author Cristian
 */
public class XmlDAOFactory extends DAOFactory {
    public XmlIPrivadosDAO getIPrivadosDAO() throws JAXBException {
        return new XmlIPrivadosDAO();
    }
    public XmlIPublicosDAO getIPublicosDAO() throws JAXBException
            {
        return new XmlIPublicosDAO();
    }
    public XmlDelegacionDAO getDelegacionDAO() throws JAXBException
            {
        return new XmlDelegacionDAO();
    }
    public XmlLineaAccionDAO getLineaAccionDAO() throws JAXBException
            {
        return new XmlLineaAccionDAO();
    }
    public XmlOngDAO getOngDAO() throws JAXBException
            {
        return new XmlOngDAO();
    }
    public XmlPContratadoDAO getPContratadoDAO() throws JAXBException
            {
        return new XmlPContratadoDAO();
    }
    public XmlPersonaDAO getPersonaDAO() throws JAXBException
            {
        return new XmlPersonaDAO();
    }
    public XmlProyectoDAO getProyectoDAO() throws JAXBException
            {
        return new XmlProyectoDAO();
    }
    public XmlSedeCentralDAO getSedeCentralDAO() throws JAXBException
            {
        return new XmlSedeCentralDAO();
    }
    public XmlSocioDAO getSocioDAO() throws JAXBException
            {
        return new XmlSocioDAO();
    }
}

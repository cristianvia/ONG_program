/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.*;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public class XMLtoSQL {

    public boolean Socios() {
        boolean estado = false;
        try {
            Socios socios = new Socios();

            //Realizamos la lectura del fichero XML y lo almacenamos en la variable correspondiente
            DAOFactory XmlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
            XmlSocioDAO SociosDAO = XmlDAOFactory.getSocioDAO();
            socios = SociosDAO.listarSocios();

            //Subimos los datos leidos a la DB
            DBSocioDAO DBSociosDAO = new DBSocioDAO();
            DBSociosDAO.guardar(socios);
            estado = true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return estado;
    }

    public boolean PContratado() {
        boolean estado = false;
        try {
            PContratados pcontratados = new PContratados();

            //Realizamos la lectura del fichero XML y lo almacenamos en la variable correspondiente
            DAOFactory XmlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
            XmlPContratadoDAO PContratadosDAO = XmlDAOFactory.getPContratadoDAO();
            pcontratados = PContratadosDAO.listarPContratados();

            //Subimos los datos leidos a la DB
            DBContratadoDAO DBContratadosDAO = new DBContratadoDAO();
            DBContratadosDAO.guardar(pcontratados);
            estado = true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return estado;
    }
    
    public boolean SedeCentral() {
        boolean estado = false;
        try {
            SedesCentrales sedescentrales = new SedesCentrales();

            //Realizamos la lectura del fichero XML y lo almacenamos en la variable correspondiente
            DAOFactory XmlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
            XmlSedeCentralDAO SedeCentralDAO = XmlDAOFactory.getSedeCentralDAO();
            sedescentrales = SedeCentralDAO.listarSedesCentrales();

            //Subimos los datos leidos a la DB
            DBSedeCentralDAO DBSedeCentralDAO = new DBSedeCentralDAO();
            DBSedeCentralDAO.guardar(sedescentrales);
            estado = true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return estado;
    }

    public boolean Delegacion() {
        boolean estado = false;
        try {
            Delegaciones delegaciones = new Delegaciones();

            //Realizamos la lectura del fichero XML y lo almacenamos en la variable correspondiente
            DAOFactory XmlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
            XmlDelegacionDAO DelegacionDAO = XmlDAOFactory.getDelegacionDAO();
            delegaciones = DelegacionDAO.listarDelegaciones();

            //Subimos los datos leidos a la DB
            DBDelegacionDAO DBDelegacionDAO = new DBDelegacionDAO();
            DBDelegacionDAO.guardar(delegaciones);
            estado = true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return estado;
    }
}

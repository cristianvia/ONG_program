/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.Socios;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface ISocioDAO {
    public void guardar(Socios socios) throws JAXBException;
    public Socios listarSocios() throws JAXBException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.Personas;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface IPersonaDAO {
    public void guardar(Personas personas) throws JAXBException;
    public Personas listarPersonas() throws JAXBException;
}

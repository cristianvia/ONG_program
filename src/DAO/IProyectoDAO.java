/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.Proyectos;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface IProyectoDAO {
    public void guardar(Proyectos proyectos) throws JAXBException;
    public Proyectos listarProyectos() throws JAXBException;
}

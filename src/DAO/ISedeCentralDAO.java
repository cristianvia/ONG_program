/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.SedesCentrales;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface ISedeCentralDAO {
    public void guardar(SedesCentrales sedesCentrales) throws JAXBException;
    public SedesCentrales listarSedesCentrales() throws JAXBException;
}

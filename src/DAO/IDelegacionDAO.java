/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.Delegaciones;
import javax.xml.bind.JAXBException;
/**
 *
 * @author Cristian
 */

public interface IDelegacionDAO {
    public void guardar(Delegaciones delegaciones) throws JAXBException;
    public Delegaciones listarDelegaciones() throws JAXBException;
    
}

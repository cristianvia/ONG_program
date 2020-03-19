/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.Ongs;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface IONGDAO {
    public void guardar(Ongs ongs) throws JAXBException;
    public Ongs listarOngs() throws JAXBException;
}

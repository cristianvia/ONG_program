/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.PContratados;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface IpContratadoDAO {
    public void guardar(PContratados pContratados) throws JAXBException;
    public PContratados listarPContratados() throws JAXBException;
}

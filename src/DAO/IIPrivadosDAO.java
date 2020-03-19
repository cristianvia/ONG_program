/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.IPrivadosList;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface IIPrivadosDAO {
    public void guardar(IPrivadosList iPrivadosList) throws JAXBException;
    public IPrivadosList listarIPrivados() throws JAXBException;
}

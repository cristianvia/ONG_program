/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.IPublicosList;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface IIPublicosDAO {
    public void guardar(IPublicosList iPublicosList) throws JAXBException;
    public IPublicosList listarIPublicos() throws JAXBException;
}

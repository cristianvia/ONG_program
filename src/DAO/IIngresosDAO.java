/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.IngresosList;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface IIngresosDAO {
    public void guardar(IngresosList ingresosList) throws JAXBException;
    public IngresosList listarIngresos() throws JAXBException;
}

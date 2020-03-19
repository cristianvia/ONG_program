/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.LineasAccion;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public interface ILineaAccionDAO {
    public void guardar(LineasAccion lineasAccion) throws JAXBException;
    public LineasAccion listarLineasAccion() throws JAXBException;
}

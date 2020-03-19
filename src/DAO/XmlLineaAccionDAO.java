/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import P5.LineaAccion;
import P5.LineasAccion;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlLineaAccionDAO implements ILineaAccionDAO {
    private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlLineaAccionDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(LineasAccion.class);
        this.nombreFichero = "LineasAccion.xml";
    }
    @Override
    public void guardar(LineasAccion lineasAccion) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(lineasAccion, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(lineasAccion, System.out);
    }
    
    @Override
    public LineasAccion listarLineasAccion() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        LineasAccion lineasAccion = (LineasAccion) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estas son las lineas de accion contenidas en el fichero " + nombreFichero);
        
        for(LineaAccion lineaAccion : lineasAccion.getLineasAccion())
        {
            System.out.println("---");
            System.out.println("Nombre de la Linea de Acción: \t" + lineaAccion.getNombre());
        }
        return lineasAccion;
    }
}
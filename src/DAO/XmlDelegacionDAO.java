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

import P5.Delegacion;
import P5.Delegaciones;

import java.io.File;

/**
 *
 * @author Cristian
 */
public class XmlDelegacionDAO implements IDelegacionDAO{
    private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlDelegacionDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(Delegaciones.class);
        this.nombreFichero = "Delegaciones.xml";
    }
    @Override
    public void guardar(Delegaciones delegaciones) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(delegaciones, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(delegaciones, System.out);
    }
    
    @Override
    public Delegaciones listarDelegaciones() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Delegaciones delegaciones = (Delegaciones) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estas son las delegaciones contenidas en el fichero " + nombreFichero);
        
        for(Delegacion delegacion : delegaciones.getDelegaciones())
        {
            System.out.println("---");
            System.out.println("Nombre de la delegacion: \t" + delegacion.getNombre());
            System.out.println("Direccion de la delegocion: \t" + delegacion.getDireccion());
        }
        return delegaciones;
    }
}

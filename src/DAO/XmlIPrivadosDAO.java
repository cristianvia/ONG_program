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

import P5.IPrivados;
import P5.IPrivadosList;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlIPrivadosDAO implements IIPrivadosDAO{
    private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlIPrivadosDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(IPrivadosList.class);
        this.nombreFichero = "IPrivadosList.xml";
    }
    @Override
    public void guardar(IPrivadosList iPrivadosList) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(iPrivadosList, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(iPrivadosList, System.out);
    }
    
    @Override
    public IPrivadosList listarIPrivados() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        IPrivadosList iPrivadosList = (IPrivadosList) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estos son los ingresos privados contenidos en el fichero " + nombreFichero);
        
        for(IPrivados iPrivados : iPrivadosList.getIPrivados())
        {
            System.out.println("---");
            System.out.println("Nombre de la aportación: \t" + iPrivados.getNombre());
            System.out.println("Id de la aportación: \t" + iPrivados.getId());
            System.out.println("Tipo de aportación: \t" + iPrivados.getAportacion());
            System.out.println("Cantidad de la aportación: \t" + iPrivados.getCantidad());
        }
        return iPrivadosList;
    }
}

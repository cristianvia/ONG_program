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

import P5.IPublicos;
import P5.IPublicosList;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlIPublicosDAO implements IIPublicosDAO {
 private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlIPublicosDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(IPublicos.class);
        this.nombreFichero = "IPublicosList.xml";
    }
    @Override
    public void guardar(IPublicosList iPublicosList) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(iPublicosList, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(iPublicosList, System.out);
    }
    
    @Override
    public IPublicosList listarIPublicos() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        IPublicosList iPublicosList = (IPublicosList) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estos son los ingresos publicos listados en el fichero " + nombreFichero);
        
        for(IPublicos iPublicos : iPublicosList.getIPublicos())
        {
            System.out.println("---");
            System.out.println("Nombre de la aportación: \t" + iPublicos.getNombre());
            System.out.println("Id de la aportación: \t" + iPublicos.getId());
            System.out.println("Tipo de aportación: \t" + iPublicos.getAdministracion());
            System.out.println("Cantidad de la aportación: \t" + iPublicos.getCantidad());
        }
        return iPublicosList;
    }
}

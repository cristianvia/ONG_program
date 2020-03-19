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

import P5.SedeCentral;
import P5.SedesCentrales;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlSedeCentralDAO implements ISedeCentralDAO {
private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlSedeCentralDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(SedesCentrales.class);
        this.nombreFichero = "SedesCentrales.xml";
    }
    @Override
    public void guardar(SedesCentrales sedesCentrales) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(sedesCentrales, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(sedesCentrales, System.out);
    }
    
    @Override
    public SedesCentrales listarSedesCentrales() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SedesCentrales sedesCentrales = (SedesCentrales) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Esta es la sede central contenida en el fichero " + nombreFichero);
        
        for(SedeCentral sedeCentral : sedesCentrales.getSedesCentrales())
        {
            System.out.println("---");
            System.out.println("Nombre de la Sede Central: \t" + sedeCentral.getNombre());
            System.out.println("Direccion de la Sede Central: \t" + sedeCentral.getDireccion());
            System.out.println("Telefono de la Sede Central: \t" + sedeCentral.getTelefono());

        }
        return sedesCentrales;
    }
}


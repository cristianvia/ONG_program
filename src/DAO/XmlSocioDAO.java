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

import P5.Socio;
import P5.Socios;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlSocioDAO implements ISocioDAO {
    private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlSocioDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(Socios.class);
        this.nombreFichero = "Socios.xml";
    }
    @Override
    public void guardar(Socios socios) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(socios, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(socios, System.out);
    }
    
    @Override
    public Socios listarSocios() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Socios socios = (Socios) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estos son los socios contenidas en el fichero " + nombreFichero);
        
        for(Socio socio : socios.getSocios())
        {
            System.out.println("---");
            System.out.println("Nombre del Socio: \t" + socio.getNombre());
            System.out.println("DNI del Socio: \t" + socio.getDNI());
            System.out.println("Tipo de cuota del Socio: \t" + socio.getCuota());
            System.out.println("Cantidad aportada por el Socio: \t" + socio.getImporte());
        }
        return socios;
    }
}

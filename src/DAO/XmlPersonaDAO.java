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

import P5.Persona;
import P5.Personas;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlPersonaDAO implements IPersonaDAO {
    private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlPersonaDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(Personas.class);
        this.nombreFichero = "Personas.xml";
    }
    @Override
    public void guardar(Personas personas) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(personas, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(personas, System.out);
    }
    
    @Override
    public Personas listarPersonas() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Personas personas = (Personas) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estas son las personas contenidas en el fichero " + nombreFichero);
        
        for(Persona persona : personas.getPersonas())
        {
            System.out.println("---");
            System.out.println("Nombre de la persona: \t" + persona.getNombre());
            System.out.println("Apellidos de la persona: \t" + persona.getApellidos());
            System.out.println("DNI de la persona: \t" + persona.getDNI());
        }
        return personas;
    }
}
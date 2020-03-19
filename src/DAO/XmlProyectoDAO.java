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

import P5.Proyecto;
import P5.Proyectos;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlProyectoDAO implements IProyectoDAO{
    private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlProyectoDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(Proyectos.class);
        this.nombreFichero = "Proyectos.xml";
    }
    @Override
    public void guardar(Proyectos proyectos) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(proyectos, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(proyectos, System.out);
    }
    
    @Override
    public Proyectos listarProyectos() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Proyectos proyectos = (Proyectos) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estos son los proyectos contenidas en el fichero " + nombreFichero);
        
        for(Proyecto proyecto : proyectos.getProyectos())
        {
            System.out.println("---");
            System.out.println("Pais del proyecto: \t" + proyecto.getPais());
            System.out.println("Localizacion del proyecto: \t" + proyecto.getLocalizacion());
            System.out.println("Linea de accion del proyecto: \t" + proyecto.getLineaAccion());
        }
        return proyectos;
    }
}

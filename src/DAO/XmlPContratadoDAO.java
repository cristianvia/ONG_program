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

import P5.PContratado;
import P5.PContratados;

import java.io.File;
/**
 *
 * @author josep
 */
public class XmlPContratadoDAO implements IpContratadoDAO {
private JAXBContext jaxbContext = null;
    private String nombreFichero = null;
    
    public XmlPContratadoDAO() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(PContratados.class);
        this.nombreFichero = "pContratados.xml";
    }
    @Override
    public void guardar(PContratados pContratados) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(pContratados, new File(nombreFichero));
        System.out.println();
        System.out.println("Se ha escrito el fichero " + nombreFichero + " con el siguiente contenido:");
        System.out.println();
        marshaller.marshal(pContratados, System.out);
    }
    
    @Override
    public PContratados listarPContratados() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        PContratados pContratados = (PContratados) unmarshaller.unmarshal (new File(nombreFichero));
        
        System.out.println();
        System.out.println("Estos son los contratados contenidos en el fichero " + nombreFichero);
        
        for(PContratado pContratado : pContratados.getPContratados())
        {
            System.out.println("---");
            System.out.println("Nombre del Contratado: \t" + pContratado.getNombre());
            System.out.println("DNI del Contratado: \t" + pContratado.getDNI());
            System.out.println("Usuario del Contratado: \t" + pContratado.getUsuario());
            System.out.println("Rol del usuario del Contratado: \t" + pContratado.getRol());
        }
        return pContratados;
    }
}


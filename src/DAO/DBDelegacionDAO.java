/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.Delegaciones;
import P5.*;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Josep
 */
public class DBDelegacionDAO implements IDelegacionDAO{
    
    public DBDelegacionDAO() {
    }
    //sobrescribe la funcion de listar creada en la interfaz
    @Override
    public Delegaciones listarDelegaciones() throws JAXBException {
        Delegaciones delegaciones = new Delegaciones();
        utilidad u = new utilidad();
        try 
        {
            delegaciones = u.listarTablaDelegaciones();

            for(Delegacion delegacion : delegaciones.getDelegaciones())
            {
                System.out.println("---");
                System.out.println("Nombre de la delegación: \t" + delegacion.getNombre());
                System.out.println("Dirección: \t" + delegacion.getDireccion());
                System.out.println("Ciudad: \t" + delegacion.getCiudad());
                System.out.println("Telefono: \t" + delegacion.getTelefono());
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
        return delegaciones;
    }
    //sobrescribe la funcion de guardar creada en la interfaz
    @Override
    public void guardar(Delegaciones delegaciones) throws JAXBException {

        utilidad u = new utilidad();
        
        try {
            for(Delegacion delegacion : delegaciones.getDelegaciones())
            {
                u.AddDelegacion(delegacion);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.Cuota;
import P5.Socio;
import P5.Socios;
import P5.utilidad;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Josep
 */
public class DBSocioDAO implements ISocioDAO{
    
    public DBSocioDAO() {
    }
    //sobrescribe la funcion de listar creada en la interfaz
    @Override
    public Socios listarSocios() throws JAXBException {
        Socios socios = new Socios();
        utilidad u = new utilidad();
        try 
        {
            socios = u.listarTablaSocios();

            for(Socio socio : socios.getSocios())
            {
                System.out.println("---");
                System.out.println("Nombre del Socio: \t" + socio.getNombre());
                System.out.println("DNI del Socio: \t" + socio.getDNI());
                System.out.println("Tipo de cuota del Socio: \t" + socio.getCuota());
                System.out.println("Cantidad aportada por el Socio: \t" + socio.getImporte());
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
        return socios;
    }
    //sobrescribe la funcion de guardar creada en la interfaz
    @Override
    public void guardar(Socios socios) throws JAXBException {

        utilidad u = new utilidad();
        
        try {
            for(Socio socio : socios.getSocios())
            {
                u.AddSocio(socio);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
    }

}
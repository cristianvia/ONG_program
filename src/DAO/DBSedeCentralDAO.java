/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import P5.SedeCentral;
import P5.*;
import javax.xml.bind.JAXBException;
/**
 *
 * @author Cristian
 */
public class DBSedeCentralDAO implements ISedeCentralDAO{
  public DBSedeCentralDAO() {
    }
    //sobrescribe la funcion de listar creada en la interfaz
    @Override
    public SedesCentrales listarSedesCentrales() throws JAXBException {
        SedesCentrales sedescentrales = new SedesCentrales();
        utilidad u = new utilidad();
        try 
        {
            sedescentrales = u.listarTablaSedeCentral();

            for(SedeCentral sedecentral : sedescentrales.getSedesCentrales())
            {
                System.out.println("---");
                System.out.println("Nombre de la Sede Central: \t" + sedecentral.getNombre());
                System.out.println("Dirección: \t" + sedecentral.getDireccion());
                System.out.println("Ciudad: \t" + sedecentral.getCiudad());
                System.out.println("Telefono: \t" + sedecentral.getTelefono());
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
        return sedescentrales;
    }
    //sobrescribe la funcion de guardar creada en la interfaz
    @Override
    public void guardar(SedesCentrales sedescentrales) throws JAXBException {

        utilidad u = new utilidad();
        
        try {
            for(SedeCentral sedecentral : sedescentrales.getSedesCentrales())
            {
                u.AddSedeCentral(sedecentral);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        u.desconectar();
    }
    
}

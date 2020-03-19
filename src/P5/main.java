/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;

import DAO.*;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Cristian
 */
public class main {

    private static PContratados pcontratados;
    private static Socios socios;

    private static PContratados inicializaPContratados() {
    Rol user = Rol.usuario;
    Rol admin = Rol.admin;
    PContratado pContratado1 = new PContratado("Juan", "Vileta", "47859685Z", "Juanito", "1234", user);
    PContratado pContratado2 = new PContratado("Greg", "House", "48753185Z", "House", "1234567", admin);
    PContratados pcontratados = new PContratados();
    pcontratados.add(pContratado1);
    pcontratados.add(pContratado2);
    return pcontratados;
    }

    private static Socios inicializaSocios(){
        Cuota mensual = Cuota.mensual;
        Cuota anual = Cuota.anual;
        Socio socio1 = new Socio("Lluc", "Caset", "12345678Z", mensual, 100);
        Socio socio2 = new Socio("Jana", "Garli", "12874678Z", anual, 1300);
        Socios socios = new Socios();
        socios.add(socio1);
        socios.add(socio2);
        return socios;
    }
    
    public static boolean main(String[] args) throws JAXBException {
        boolean result = false;
        try {
            pcontratados = inicializaPContratados();
            DAOFactory XmlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
            XmlPContratadoDAO PContratadoDAO = XmlDAOFactory.getPContratadoDAO();
            PContratadoDAO.guardar(pcontratados);
            PContratadoDAO.listarPContratados();

            socios = inicializaSocios();
            XmlSocioDAO SocioDAO = XmlDAOFactory.getSocioDAO();
            SocioDAO.guardar(socios);
            SocioDAO.listarSocios();
            result = true;
        }
        
        catch(Exception e){
            System.out.println(e);
        }
        return result;
        
    }
    
//        public static boolean main(String[] args) throws JAXBException {
//        boolean result = false;
//        try {
//           DBOngDAO DBSociosDAO = new DBOngDAO();
//           Socios socios = DBSociosDAO.listarSocios();
//            result = true;
//        }
//        
//        catch(Exception e){
//            System.out.println(e);
//        }
//        return result;
//        
//    }
}

/*
// * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;

import DAO.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cristian
 */
public class mainTest {

    public mainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class main.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        boolean result = main.main(args);
        boolean expected = true;
        assertEquals(expected, result);
    }

    @Test
    public void testContratadosXML() throws Exception {
        System.out.println("Contratados XML");
        
        //Creamos las variables
        Rol user = Rol.usuario;
        Rol admin = Rol.admin;
        PContratado pContratado1 = new PContratado("Juan", "Vileta", "47859685Z", "Juanito", "1234", user);
        PContratado pContratado2 = new PContratado("Greg", "House", "48753185Z", "House", "1234567", admin);

        boolean result = false;
        boolean expected = true;

        PContratados pcontratados = new PContratados();
        PContratados pcontratadosRead = new PContratados();
        pcontratados.add(pContratado1);
        pcontratados.add(pContratado2);
        
        //Ejecutamos los comandos de lectura
        DAOFactory XmlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
        XmlPContratadoDAO PContratadoDAO = XmlDAOFactory.getPContratadoDAO();
        pcontratadosRead = PContratadoDAO.listarPContratados();
        //Comprobamos si los datos leidos son iguales a los que deberian haber en el fichero xml
        if (pcontratados.getPContratados().get(0).getNombre().equals(pcontratadosRead.getPContratados().get(0).getNombre())
                && pcontratados.getPContratados().get(1).getNombre().equals(pcontratadosRead.getPContratados().get(1).getNombre())) {
            result = true;
        }
        assertEquals(expected, result);
    }

    @Test
    public void testSociosXML() throws Exception {
        System.out.println("Socios XML");
        //Creamos las variables
        Cuota mensual = Cuota.mensual;
        Cuota anual = Cuota.anual;
        Socio socio1 = new Socio("Lluc", "Caset", "12345678Z", mensual, 100);
        Socio socio2 = new Socio("Jana", "Garli", "12874678Z", anual, 1300);

        boolean result = false;
        boolean expected = true;
        
        Socios socios = new Socios();
        Socios sociosRead = new Socios();
        socios.add(socio1);
        socios.add(socio2);
        //Ejecutamos los comandos de lectura
        DAOFactory XmlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
        XmlSocioDAO SociosDAO = XmlDAOFactory.getSocioDAO();
        sociosRead = SociosDAO.listarSocios();
        //Comprobamos si los datos leidos son iguales a los que deberian haber en el fichero xml
        if (socios.getSocios().get(0).getNombre().equals(sociosRead.getSocios().get(0).getNombre())
                && socios.getSocios().get(1).getNombre().equals(sociosRead.getSocios().get(1).getNombre())) {
            result = true;
        }
        assertEquals(expected, result);
    }

    @Test
    public void testSociosSQL() throws Exception {
        System.out.println("Socios SQL");

        //Creamos las variables
        Socio socio1 = new Socio("SocioPrueba55", null, "99999999J", Cuota.mensual, 55);
        Socio socio2 = new Socio("SocioPrueba33", null, "88888888Z", Cuota.anual, 33);

        boolean result = false;
        boolean expected = true;

        Socios socios = new Socios();
        Socios sociosRead = new Socios();
        socios.add(socio1);
        socios.add(socio2);

        try {
            //Creamos la variable DBSociosDao para poder trabajar con la clase socios dao y la base de datos
            DBSocioDAO DBSociosDAO = new DBSocioDAO();
            //Guardamos los socios almacenados en la variable socios en la base de datos
            DBSociosDAO.guardar(socios);
            //Leemos la base de datos y almacenamos sus socios en sociosRead
            sociosRead = DBSociosDAO.listarSocios();
            //Almacenamos la cantidad de socios que hay
            int lastId = 0;
            utilidad u = new utilidad();
            Connection conn = u.getConnection();
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT COUNT(idsocio) FROM socio");
            
            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("COUNT(idsocio)");
            }
            lastId--;
            //Comprobamos que los dos ultimos socios añadidos coincidan con los que hemos creado nosotros
            if (socios.getSocios().get(1).getNombre().equals(sociosRead.getSocios().get(lastId).getNombre())
                    && socios.getSocios().get(0).getNombre().equals(sociosRead.getSocios().get(lastId - 1).getNombre())) {
                result = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(expected, result);
    }

    @Test
    public void testDelegacionSQL() throws Exception {
        System.out.println("Delegacion SQL");
        //Creamos las variables
        ArrayList<Personal> p = new ArrayList<Personal>();
        Delegacion delegacion1 = new Delegacion("Delegacion1", "Carrer Narcis 39", 934445566, "Barcelona", p);
        Delegacion delegacion2 = new Delegacion("Delegacion2", "Carrer Roger de Flor 2", 935556688, "Barcelona", p);

        boolean result = false;
        boolean expected = true;

        Delegaciones delegaciones = new Delegaciones();
        Delegaciones delegacionesRead = new Delegaciones();

        delegaciones.add(delegacion1);
        delegaciones.add(delegacion2);

        try {
            //Creamos la variable DBDelegacionDAO para poder trabajar con la clase delegacion dao y la base de datos
            DBDelegacionDAO DBDelegacionDAO = new DBDelegacionDAO();
            //Guardamos las delegaciones almacenadas en la variable delegaciones en la base de datos
            DBDelegacionDAO.guardar(delegaciones);
            //Leemos la base de datos y almacenamos sus delegaiones en delegacionesRead
            delegacionesRead = DBDelegacionDAO.listarDelegaciones();
            //Almacenamos la cantidad de delegaciones que hay
            int lastId = 0;
            utilidad u = new utilidad();
            Connection conn = u.getConnection();
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT iddelegacion FROM delegacion ORDER BY iddelegacion DESC LIMIT 1");

            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("iddelegacion");
            }
            lastId--;
            //Comprobamos que las dos ultimas delegaciones añadidas coincidan con las que hemos creado nosotros
            if (delegaciones.getDelegaciones().get(1).getNombre().equals(delegacionesRead.getDelegaciones().get(lastId).getNombre())
                    && delegaciones.getDelegaciones().get(0).getNombre().equals(delegacionesRead.getDelegaciones().get(lastId - 1).getNombre())) {
                result = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals(expected, result);
    }

    //Test Sede Central
    @Test
    public void testSedeCentralSQL() throws Exception {
        System.out.println("SedeCentral SQL");
        //Creamos las variables
        ArrayList<Personal> p = new ArrayList<Personal>();
        SedeCentral sedecentral1 = new SedeCentral("SedeCentral1", "Carrer Narcis 39", 934445566, "Barcelona", p);

        boolean result = false;
        boolean expected = true;

        SedesCentrales sedescentrales = new SedesCentrales();
        SedesCentrales sedescentralesRead = new SedesCentrales();

        sedescentrales.add(sedecentral1);

        try {
            //Para seguir la misma estructura leemos cuantas sedes centrales hay (solo deberia haber una)
            int lastId = 0;
            utilidad u = new utilidad();
            Connection conn = u.getConnection();
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT idsedecentral FROM sedecentral ORDER BY idsedecentral DESC LIMIT 1");

            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("idsedecentral");
            }
            //Si no hay ninguna almacenamos la sede central almacenada en sedescentrales a la base de datos
            DBSedeCentralDAO DBSedeCentralDAO = new DBSedeCentralDAO();
            if (lastId < 1) {
                DBSedeCentralDAO.guardar(sedescentrales);
            }
            //Leemos la sedecentral que hay en la base de datos y la almacenamos en sedescentralesRead
            sedescentralesRead = DBSedeCentralDAO.listarSedesCentrales();
            //Comprobamos que coinciden las variables sedescentrales y sedescentralesRead
            if (sedescentrales.getSedesCentrales().get(0).getNombre().equals(sedescentralesRead.getSedesCentrales().get(0).getNombre()))
            {
                result = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals(expected, result);
    }
    
    //Test pcontratado
    @Test
    public void testPcontratadoSQL() throws Exception {
        System.out.println("PContratados SQL");
        //Creamos las variables
        PContratado pcontratado1 = new PContratado("PContratadoPrueba55", null, "99999999J", "user1", "pass", Rol.usuario);
        PContratado pcontratado2 = new PContratado("PContratadoPrueba33", "Franky", "88888888Z", "admin1", "pass", Rol.admin);

        boolean result = false;
        boolean expected = true;

        PContratados pcontratados = new PContratados();
        PContratados pcontratadosRead = new PContratados();
        pcontratados.add(pcontratado1);
        pcontratados.add(pcontratado2);

        try {
            //Creamos la variable DBContratadosDAO para poder trabajar con la clase PContratados y la base de datos
            DBContratadoDAO DBContratadosDAO = new DBContratadoDAO();
            //Guardamos los contratados almacenados en la variable pcontratados en la base de datos
            DBContratadosDAO.guardar(pcontratados);
            //Leemos la base de datos y almacenamos sus pcontratados en pcontratadosRead
            pcontratadosRead = DBContratadosDAO.listarPContratados();
            //Almacenamos la cantidad de pcontratados que hay
            int lastId = 0;
            utilidad u = new utilidad();
            Connection conn = u.getConnection();
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT COUNT(idpcontratado) FROM pcontratado");

            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt("COUNT(idpcontratado)");
            }
            lastId--;
            //Comprobamos que los dos ultimos pcontratados añadidos coincidan con los que hemos creado nosotros
            if (pcontratados.getPContratados().get(1).getNombre().equals(pcontratadosRead.getPContratados().get(lastId).getNombre())
                    && pcontratados.getPContratados().get(0).getNombre().equals(pcontratadosRead.getPContratados().get(lastId - 1).getNombre())) {
                result = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(expected, result);
    }
    
    //XML to SQL test
    @Test
    public void XMLtoSQL() throws Exception {
        System.out.println("XML to SQL");
        //Creamos las variables
        boolean result = false;
        boolean expected = true;
              
        try {
            
            XMLtoSQL xmltosql = new XMLtoSQL();
            //Ejecutamos el comando para pasar del fichero XML que almacena los socios a la base de datos y lo almacenamos en una variable
            result = xmltosql.Socios();
            //Si ha funcionado hacemos lo mismo con PContratados
            if(result){               
                result = xmltosql.PContratado();
            }
        }
        catch(Exception e){
            System.out.println(e);
            result = false;
        }
        assertEquals(expected, result);
    }
}

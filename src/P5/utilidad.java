/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;

/**
 *
 * @author Cristian
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;

/**
 * objeto db para cargar driver y hacer queries
 * 
 */
public class utilidad {

    private static Connection conn;
//    private static final String Driver = "oracle.jdbc.driver.OracleDriver";
    private static final String driver = "com.mysql.jdbc.Driver";
//    private static final String ConnectionString = "localhost";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/ongbaltico";

    /**
     * crear objeto BBDD
     */
    public utilidad() {
        conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null){
                System.out.println("Conexión establecida");
            }
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar: " + e);
        }
    }
    //Devuelve la conexión creada, esto es util para cerrarla o para acceder a ella
    public Connection getConnection(){
        return conn;
    }
    //Crea la conexión a la base de datos
    public void conectar(){
        if(conn == null){
            try{
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password);
                if (conn != null){
                    System.out.println("Conexión establecida");
                }
            } catch (ClassNotFoundException | SQLException e){
                System.out.println("Error al conectar: " + e);
            }
        }
    }
    //Desconecta de la base de datos
    public void desconectar(){
        conn = null;
        if(conn == null){
            System.out.println("Conexión terminada.");
        }
    }
    /**
     * para hacer una actualizacion de query como update, delete
     * @param query custom query
     * @throws SQLException throws an exception if an error occurs
     */
    public ResultSet runQuery(String query) throws SQLException {
        ResultSet rs;
        PreparedStatement st = conn.prepareStatement(query);
        rs = st.executeQuery();
        return rs;
    }
    
    //Query para prepared statments
    //DB Execute Query Operation
    public ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            conectar();
            System.out.println("Select statement: " + queryStmt + "\n");
 
            //Create statement
            stmt = conn.createStatement();
 
            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);
 
            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            desconectar();
        }
        //Return CachedRowSet
        return crs;
    }
    
    //Accede a la base de datos y devuelve los socios que encuentra en ella
    public Socios listarTablaSocios() throws SQLException{
        Socios socios = new Socios();
        
        ResultSet res = runQuery("SELECT * FROM Socio JOIN persona ON idSocio = idpersona");
        while (res.next()) {
            Socio socio = new Socio();
            socio.setApellidos(res.getString("apellidos"));
            socio.setDNI(res.getString("dni"));
            socio.setImporte(res.getInt("cuota"));
            socio.setNombre(res.getString("nombre"));

            switch (res.getString("tipoCuota")){
                case "mensual":
                    socio.setCuota(Cuota.mensual);
                    break;
                case "trimestral":
                    socio.setCuota(Cuota.trimestral);
                    break;
                case "anual":
                    socio.setCuota(Cuota.anual);
                    break;
            }
            socios.add(socio);
        }
        return socios; 
    }
    //Accede a la base de datos y añade los socios que pongamos en los argumentos, devuelve un true si ha podido almacenarlos y un false si no ha podido
    public boolean AddSocio(Socio socio) throws SQLException{

        try{
            //Assume a valid connection object conn
            conn.setAutoCommit(false);
            //Saber qual es el ultimo id
            int insertId = 0;
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT idpersona FROM persona ORDER BY idpersona DESC LIMIT 1");
            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next())
            {
             insertId = rs.getInt("idpersona");            
            }
            insertId++;


            PreparedStatement ps = conn.prepareStatement("INSERT INTO persona (idpersona, nombre, apellidos, dni) VALUES (?, ?, ?, ?)");
            ps.setInt(1, insertId);
            ps.setString(2, socio.getNombre());
            ps.setString(3, socio.getApellidos());
            ps.setString(4, socio.getDNI());

            //si respuesta1 > 0 se ha ejecutado correctamente
            int respuesta1 = ps.executeUpdate();

            ps = conn.prepareStatement("INSERT INTO socio (idSocio, cuota, tipoCuota) VALUES (?, ?, ?)");
            ps.setInt(1, insertId);
            ps.setFloat(2, socio.getImporte());
            ps.setString(3, socio.getCuota().toString());

            int respuesta2 = ps.executeUpdate();
            if(respuesta1 > 0 && respuesta2 > 0){
                // If there is no error.
                conn.commit();
                return true;
            }else{
                // If there is no error.
                conn.rollback();
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            // If there is no error.
            conn.rollback();
            return false;
        }
    }
    //Accede a la base de datos y devuelve las delegaciones que encuentra en ella
    public Delegaciones listarTablaDelegaciones() throws SQLException{
        Delegaciones delegaciones = new Delegaciones();
        ResultSet res = runQuery("SELECT * FROM delegacion");
        
        while (res.next()) {
            Delegacion delegacion = new Delegacion();
            delegacion.setNombre(res.getString("nombre"));
            delegacion.setDireccion(res.getString("direccion"));
            delegacion.setTelefono(res.getInt("telefono"));
            delegacion.setCiudad(res.getString("ciudad"));

            delegaciones.add(delegacion);
        }
        return delegaciones; 
    }
    //Accede a la base de datos y añade las delegaciones que pongamos en los argumentos,
    //devuelve un true si ha podido almacenarlas y un false si no ha podido
    public boolean AddDelegacion(Delegacion delegacion) throws SQLException{
         try{
            //Assume a valid connection object conn
            conn.setAutoCommit(false);
            //Saber qual es el ultimo id
            int insertId = 0;
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT iddelegacion FROM delegacion ORDER BY iddelegacion DESC LIMIT 1");
            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next())
            {
             insertId = rs.getInt("iddelegacion");            
            }
            insertId++;

            PreparedStatement ps = conn.prepareStatement("INSERT INTO delegacion (iddelegacion, nombre, direccion, telefono, ciudad) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, insertId);
            ps.setString(2, delegacion.getNombre());
            ps.setString(3, delegacion.getDireccion());
            ps.setInt(4, delegacion.getTelefono());
            ps.setString(5, delegacion.getCiudad());

            //si respuesta1 > 0 se ha ejecutado correctamente
            int respuesta1 = ps.executeUpdate();
            if(respuesta1 > 0){
                // If there is no error.
                conn.commit();
                return true;
            }else{
                // If there is no error.
                conn.rollback();
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            // If there is no error.
            conn.rollback();
            return false;
        }
    }

    //Accede a la base de datos y devuelve las sedescentrales que encuentra en ella
     public SedesCentrales listarTablaSedeCentral() throws SQLException{
        SedesCentrales sedescentrales = new SedesCentrales();
        ResultSet res = runQuery("SELECT * FROM sedeCentral");
        
        while (res.next()) {
            SedeCentral sedecentral = new SedeCentral();
            sedecentral.setNombre(res.getString("nombre"));
            sedecentral.setDireccion(res.getString("direccion"));
            sedecentral.setTelefono(res.getInt("telefono"));
            sedecentral.setCiudad(res.getString("ciudad"));

            sedescentrales.add(sedecentral);
        }
        return sedescentrales; 
    }
     //Accede a la base de datos y añade las sedescentrales que pongamos en los argumentos
     //devuelve un true si ha podido almacenarlos y un false si no ha podido
    public boolean AddSedeCentral(SedeCentral sedecentral) throws SQLException{
         try{
            //Assume a valid connection object conn
            conn.setAutoCommit(false);
            //Saber qual es el ultimo id
            int insertId = 0;
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT idsedecentral FROM sedecentral ORDER BY idsedecentral DESC LIMIT 1");
            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next())
            {
             insertId = rs.getInt("idsedecentral");            
            }
            insertId++;

            PreparedStatement ps = conn.prepareStatement("INSERT INTO sedecentral (idsedecentral, nombre, direccion, telefono, ciudad) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, insertId);
            ps.setString(2, sedecentral.getNombre());
            ps.setString(3, sedecentral.getDireccion());
            ps.setInt(4, sedecentral.getTelefono());
            ps.setString(5, sedecentral.getCiudad());

            //si respuesta1 > 0 se ha ejecutado correctamente
            int respuesta1 = ps.executeUpdate();
            if(respuesta1 > 0){
                // If there is no error.
                conn.commit();
                return true;
            }else{
                // If there is no error.
                conn.rollback();
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            // If there is no error.
            conn.rollback();
            return false;
        }
    }
    
    //Accede a la base de datos y devuelve los PContratados que encuentra en ella
    public PContratados listarTablaPContratados() throws SQLException{
        PContratados pcontratados = new PContratados();
        
        ResultSet res = runQuery("SELECT * FROM pContratado JOIN persona ON idPContratado = idpersona");
        while (res.next()) {
            PContratado pcontratado = new PContratado();
            pcontratado.setNombre(res.getString("nombre"));
            pcontratado.setApellidos(res.getString("apellidos"));
            pcontratado.setDNI(res.getString("dni"));
            pcontratado.setUsuario(res.getString("usuario"));
            pcontratado.setPassword(res.getString("password"));
            
            switch (res.getString("Rol")){
                case "usuario":
                    pcontratado.setRol(Rol.usuario);
                    break;
                case "admin":
                    pcontratado.setRol(Rol.admin);
                    break;
            }
            pcontratados.add(pcontratado);
        }
        return pcontratados; 
    }

    //Accede a la base de datos y añade los pcontratados que pongamos en los argumentos, devuelve un true si ha podido almacenarlos y un false si no ha podido
    public boolean AddPContratado(PContratado pcontratado) throws SQLException{

        try{
            //Assume a valid connection object conn
            conn.setAutoCommit(false);
            //Saber qual es el ultimo id
            int insertId = 0;
            PreparedStatement getLastInsertId = conn.prepareStatement("SELECT idpersona FROM persona ORDER BY idpersona DESC LIMIT 1");
            ResultSet rs = getLastInsertId.executeQuery();
            if (rs.next())
            {
             insertId = rs.getInt("idpersona");            
            }
            insertId++;


            PreparedStatement ps = conn.prepareStatement("INSERT INTO persona (idpersona, nombre, apellidos, dni) VALUES (?, ?, ?, ?)");
            ps.setInt(1, insertId);
            ps.setString(2, pcontratado.getNombre());
            ps.setString(3, pcontratado.getApellidos());
            ps.setString(4, pcontratado.getDNI());

            //si respuesta1 > 0 se ha ejecutado correctamente
            int respuesta1 = ps.executeUpdate();

            ps = conn.prepareStatement("INSERT INTO pcontratado (idPContratado, usuario, password, rol) VALUES (?, ?, ?, ?)");
            ps.setInt(1, insertId);
            ps.setString(2, pcontratado.getUsuario());
            ps.setString(3, pcontratado.getPassword());
            ps.setString(4, pcontratado.getRol().toString());

            int respuesta2 = ps.executeUpdate();
            if(respuesta1 > 0 && respuesta2 > 0){
                // If there is no error.
                conn.commit();
                return true;
            }else{
                // If there is no error.
                conn.rollback();
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            // If there is no error.
            conn.rollback();
            return false;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Josep
 */
@XmlRootElement(name ="sedeCentral")
@XmlAccessorType(XmlAccessType.FIELD)
public class SedeCentral {
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "direccion")
    private String direccion;
    @XmlElement(name = "telefono")
    private int telefono;
    @XmlElement(name = "ciudad")
    private String ciudad;
    @XmlElement(name = "personal")
    private ArrayList<Personal> personal;
    
    public SedeCentral(String pNombre, String pDireccion, int pTelefono, String pCiudad, ArrayList<Personal> pPersonal) {
        this.nombre = pNombre;
        this.direccion = pDireccion;
        this. telefono = pTelefono;
        this.ciudad = pCiudad;
        this.personal = pPersonal;
    }
    
        //Constructor vacío
    public SedeCentral(){}
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String n){
        nombre = n;
    }
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String d){
        direccion = d;
    }
    public int getTelefono(){
        return telefono;
    }
    public void setTelefono(int t){
        telefono = t;
    }
    public String getCiudad(){
        return ciudad;
    }
    public void setCiudad(String c){
            ciudad = c;
    }
    public ArrayList<Personal> getPersonal(){
        return personal;
    }
    public Persona getPersona(int i){
        return personal.get(i);
    }
    public boolean addPersonal(Personal p){
        return personal.add(p);
    }
    public void removePersonal(Persona p){
        personal.remove(p);
    }
}

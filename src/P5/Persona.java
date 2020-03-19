/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;
import javax.xml.bind.annotation.*;
/**
 *
 * @author Josep
 */
@XmlRootElement(name ="persona")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Persona {
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "apellidos")
    private String apellidos;
    @XmlElement(name = "dni")
    private String dni;
    
    //A l'esquema es INT
    void setDNI(String d){
        dni = d;
    }    
    public String getDNI(){
        return dni;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String n){
        nombre = n;
    }    
    public String getApellidos(){
        return apellidos;
    }
    public void setApellidos(String a){
        apellidos = a;
    } 
}

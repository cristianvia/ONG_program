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
@XmlRootElement(name ="ingresos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ingresos {
    @XmlAttribute(name = "id")
    private int id;
    @XmlElement(name = "cantidad")
    private int cantidad;
    @XmlElement(name = "nombre")
    private String nombre;

    //Si el descomentem no deixa crear el constructor de IPrivados
//    public Ingresos(int id, String nombre){
//        this.id = id;
//        this.nombre = nombre;
//    }
    
    public int getCantidad(){
        return cantidad;
    }

    public void addCantidad(int i){
        cantidad += i;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String n){
        nombre = n;
    }
    
    public int getId(){
        return id;
    }
    //no posem public, private o protected per tal de que s'hi pugui acceder des
    //una clase filla -> https://stackoverflow.com/questions/215497/what-is-the-difference-between-public-protected-package-private-and-private-in
    void setId(int i){
        if (id == 0){
            this.id = i;
        }
    }
}

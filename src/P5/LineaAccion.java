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
@XmlRootElement(name ="lineaAccion")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class LineaAccion {
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "subLineas")
    private ArrayList<String> subLineas;
        
        
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String n){
        nombre = n;
    }
    
    public ArrayList<String> getSubLineas(){
        return subLineas;
    }
    public boolean addSubLinea(String s){
        return subLineas.add(s);
    }
    public void removeSubLinea(String s){
        subLineas.remove(s);
    }
}

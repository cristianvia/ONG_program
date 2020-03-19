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
@XmlRootElement(name ="ong")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ong {
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "sedeCentral")
    private SedeCentral sedeCentral;
    @XmlElement(name = "delegacion")
    private ArrayList<Delegacion> delegacion;
    @XmlElement(name = "ingresos")
    private float ingresos;
    
    
    public Ong(String pNombre, SedeCentral pSedeCentral, ArrayList<Delegacion> pDelegacion){
        this.nombre = pNombre;
        this.sedeCentral = pSedeCentral;
        this.delegacion = pDelegacion;
        this.ingresos = 0f;
    }
        //Constructor vacío
    public Ong(){}
    
    
    public String getNombre(){
        return nombre;
    }
    public ArrayList<Delegacion> getDelegaciones(){
        return delegacion;
    }
    public boolean addDelegacion(Delegacion d){
        return delegacion.add(d);
    }
    public void removeDelegacion(Delegacion d){
        delegacion.remove(d);
    }
    public float getIngresos(){
        return ingresos;
    }
    public void addIngresos(float i){
        ingresos += i;
    }
}

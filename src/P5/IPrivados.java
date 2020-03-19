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
@XmlRootElement(name ="iPublicos")
@XmlAccessorType(XmlAccessType.FIELD)
public class IPrivados extends Ingresos {
    @XmlElement(name = "aportacion")
    private Aportaciones aportacion;
    
    public IPrivados(int id, String nombre, Aportaciones aportacion){
        
        this.setId(id);
        this.setNombre(nombre);
        this.aportacion = aportacion;
    }
        //Constructor vacío
    public IPrivados(){}
    
    public Aportaciones getAportacion(){
        return aportacion;
    }

    public void setAportacion(Aportaciones a){
        aportacion = a;
    }
}

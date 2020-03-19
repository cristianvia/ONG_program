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
public class IPublicos extends Ingresos {
    @XmlElement(name = "administracion")
    private Administracion administracion;
    
    public IPublicos(int id, String nombre, Administracion administracion){
        this.setId(id);
        this.setNombre(nombre);
        this.administracion = administracion;
    }

        //Constructor vacío
    public IPublicos(){}
    
    public Administracion getAdministracion(){
        return administracion;
    }

    public void setAdministracion(Administracion a){
        administracion = a;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author josep
 */
@XmlRootElement(name = "ingresos")
public class IngresosList {
    List<Ingresos> ingresos;
    
    public List<Ingresos> getIngresos(){
        return ingresos;
    }
    
    @XmlElement(name = "ingresos")
    public void setIngresos(List<Ingresos> ingresos){
        this.ingresos = ingresos;
    }
    public void add(Ingresos ingresos){
        if (this.ingresos == null){
            this.ingresos = new ArrayList<Ingresos>();
        }
        this.ingresos.add(ingresos);
    }
}

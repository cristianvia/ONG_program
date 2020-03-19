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
@XmlRootElement(name = "delegaciones")
public class Delegaciones {
    List<Delegacion> delegaciones;
    
    public List<Delegacion> getDelegaciones(){
        return delegaciones;
    }
    
    @XmlElement(name = "delegacion")
    public void setDelegaciones(List<Delegacion> delegaciones){
        this.delegaciones = delegaciones;
    }
    public void add(Delegacion delegacion){
        if (this.delegaciones == null){
            this.delegaciones = new ArrayList<Delegacion>();
        }
        this.delegaciones.add(delegacion);
    }
}

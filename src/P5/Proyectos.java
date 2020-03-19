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
@XmlRootElement(name = "proyectos")
public class Proyectos {
    List<Proyecto> proyectos;
    
    public List<Proyecto> getProyectos(){
        return proyectos;
    }
    
    @XmlElement(name = "proyecto")
    public void setProyectos(List<Proyecto> proyectos){
        this.proyectos = proyectos;
    }
    public void add(Proyecto proyecto){
        if (this.proyectos == null){
            this.proyectos = new ArrayList<Proyecto>();
        }
        this.proyectos.add(proyecto);
    }
}

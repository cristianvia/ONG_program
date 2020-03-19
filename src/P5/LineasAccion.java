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
@XmlRootElement(name = "lineaAccion")
public class LineasAccion {
    List<LineaAccion> lineaAccion;
    
    public List<LineaAccion> getLineasAccion(){
        return lineaAccion;
    }
    
    @XmlElement(name = "lineaAccion")
    public void setLineasAccion(List<LineaAccion> lineaAccion){
        this.lineaAccion = lineaAccion;
    }
    public void add(LineaAccion lineaAccion){
        if (this.lineaAccion == null){
            this.lineaAccion = new ArrayList<LineaAccion>();
        }
        this.lineaAccion.add(lineaAccion);
    }
}

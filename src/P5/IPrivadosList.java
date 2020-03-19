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
@XmlRootElement(name = "iPrivados")
public class IPrivadosList {
     List<IPrivados> iPrivados;
    
    public List<IPrivados> getIPrivados(){
        return iPrivados;
    }
    
    @XmlElement(name = "iPrivados")
    public void setIPrivados(List<IPrivados> iPrivados){
        this.iPrivados = iPrivados;
    }
    public void add(IPrivados iPrivados){
        if (this.iPrivados == null){
            this.iPrivados = new ArrayList<IPrivados>();
        }
        this.iPrivados.add(iPrivados);
    }
}

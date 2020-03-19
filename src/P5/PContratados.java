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
@XmlRootElement(name = "pContratados")
public class PContratados {
    List<PContratado> pContratados;
    
    public List<PContratado> getPContratados(){
        return pContratados;
    }
    
    @XmlElement(name = "pContratado")
    public void setPContratados(List<PContratado> pContratados){
        this.pContratados = pContratados;
    }
    public void add(PContratado pContratado){
        if (this.pContratados == null){
            this.pContratados = new ArrayList<PContratado>();
        }
        this.pContratados.add(pContratado);
    }
}
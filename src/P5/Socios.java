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
@XmlRootElement(name = "socios")
public class Socios {
List<Socio> socios;
    
    public List<Socio> getSocios(){
        return socios;
    }
    
    @XmlElement(name = "socio")
    public void setSocios(List<Socio> socios){
        this.socios = socios;
    }
    public void add(Socio socios){
        if (this.socios == null){
            this.socios = new ArrayList<Socio>();
        }
        this.socios.add(socios);
    }
}
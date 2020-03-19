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
@XmlRootElement(name = "ongs")
public class Ongs {
    List<Ong> ongs;
    
    public List<Ong> getOngs(){
        return ongs;
    }
    
    @XmlElement(name = "ong")
    public void setOngs(List<Ong> ongs){
        this.ongs = ongs;
    }
    public void add(Ong ong){
        if (this.ongs == null){
            this.ongs = new ArrayList<Ong>();
        }
        this.ongs.add(ong);
    }
}

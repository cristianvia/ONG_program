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
@XmlRootElement(name = "sedesCentrales")
public class SedesCentrales {
    List<SedeCentral> sedesCentrales;
    
    public List<SedeCentral> getSedesCentrales(){
        return sedesCentrales;
    }
    
    @XmlElement(name = "sedeCentral")
    public void setSedesCentrales(List<SedeCentral> sedesCentrales){
        this.sedesCentrales = sedesCentrales;
    }
    public void add(SedeCentral sedeCentral){
        if (this.sedesCentrales == null){
            this.sedesCentrales = new ArrayList<SedeCentral>();
        }
        this.sedesCentrales.add(sedeCentral);
    }
}
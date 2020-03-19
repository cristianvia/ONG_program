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
@XmlRootElement(name = "iPublicos")
public class IPublicosList {
     List<IPublicos> iPublicos;
    
    public List<IPublicos> getIPublicos(){
        return iPublicos;
    }
    
    @XmlElement(name = "iPublicos")
    public void setIPublicos(List<IPublicos> iPublicos){
        this.iPublicos = iPublicos;
    }
    public void add(IPublicos iPublicos){
        if (this.iPublicos == null){
            this.iPublicos = new ArrayList<IPublicos>();
        }
        this.iPublicos.add(iPublicos);
    }
}

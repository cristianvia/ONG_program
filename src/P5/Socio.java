/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;
import javax.xml.bind.annotation.*;
/**
 *
 * @author Josep
 */
@XmlRootElement(name ="socio")
@XmlAccessorType(XmlAccessType.FIELD)

public class Socio extends Persona {
    @XmlElement(name = "cuota")
    private Cuota cuota;
    @XmlElement(name = "importe")
    private int importe;
    
    public Socio(String pNombre, String pApellidos, String pDni, Cuota pCuota, int pImporte){
        this.cuota = pCuota;
        this.importe = pImporte;
        this.setDNI(pDni);
        this.setApellidos(pApellidos);
        this.setNombre(pNombre);
    }
        //Constructor vacío
    public Socio(){}
    
    
    public Cuota getCuota(){
        return cuota;
    }
    public void setCuota(Cuota c){
        cuota = c;
    }
    public int getImporte(){
        return importe;
    }
    public void setImporte(int i){
        importe = i;
    }
}

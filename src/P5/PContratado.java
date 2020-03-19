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
@XmlRootElement(name ="pContratado")
@XmlAccessorType(XmlAccessType.FIELD)
public class PContratado extends Personal {
    @XmlElement(name = "usuario")
    private String usuario;
    @XmlElement(name = "password")
    private String password;
    @XmlElement(name = "rol")
    private Rol rol;
    
    public PContratado(String pNombre, String pApellidos, String pDNI, String usuario, String password, Rol rol){
        this.setApellidos(pApellidos);
        this.setNombre(pNombre);
        this.setDNI(pDNI);
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }
    //Constructor vacío
    public PContratado(){}
    
    public String getUsuario(){
        return usuario;
    }
    public String getPassword(){
        return password;
    }
    public boolean setPassword(String p){
        if(p!= ""){
            password = p;
            return true;
        }
        return false;
    }
    public Rol getRol(){
        return rol;
    }
    public void setRol(Rol r){
        rol = r;
    }
    public void setUsuario(String u){
        usuario = u;
    }
}

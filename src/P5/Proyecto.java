/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package P5;

import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.*;
/**
 *
 * @author Josep
 */
@XmlRootElement(name ="proyecto")
@XmlAccessorType(XmlAccessType.FIELD)
public class Proyecto {
    @XmlElement(name = "pais")
    private String pais;
    @XmlElement(name = "localizacion")
    private String localizacion;
    @XmlElement(name = "lineaAccion")
    private ArrayList<LineaAccion> lineaAccion;
    @XmlElement(name = "fechaInicio")
    private Date fechaI;
    @XmlElement(name = "fechaFin")
    private Date fechaF;
    @XmlElement(name = "socioLocal")
    private Socio socioLocal;
    @XmlElement(name = "financiador")
    private String financiador;
    @XmlElement(name = "financiacionAportacion")
    private float financiacionApor;
    @XmlElement(name = "voluntarios")
    private ArrayList<PVoluntario> voluntarios;
    
    
    public Proyecto(String pPais, String pLocalizacion, ArrayList<LineaAccion> lineaAccion, 
            Date pFechaI, Date pFechaF, Socio pSocioLocal, String pFinanciador, float pFinanciacionApor, 
            ArrayList<PVoluntario> pVoluntarios){
        this.pais = pPais;
        this.localizacion = pLocalizacion;
        this.lineaAccion = lineaAccion;
        this.fechaI = pFechaI;
        this.fechaF = pFechaF;
        this.socioLocal = pSocioLocal;
        this.financiador = pFinanciador;
        this.financiacionApor = pFinanciacionApor;
        this.voluntarios = pVoluntarios;
    }
    
        //Constructor vacío
    public Proyecto(){}
    
    public String getPais(){
        return pais;
    }
    public void setPais(String p){
        pais = p;
    }
    public String getLocalizacion(){
        return localizacion;
    }
    public void setLocalizacion(String l){
        localizacion = l;
    }
    public ArrayList<LineaAccion> getLineaAccion(){
        return lineaAccion;
    }
    public boolean addLineaAccion(LineaAccion l){
        return lineaAccion.add(l);
    }
    public void removeLineaAccion(LineaAccion l){
        lineaAccion.remove(l);
    }
    public Date getFechaInicio(){
        return fechaI;
    }
    public void setFechaInicio(Date f){
        fechaI = f;
    }
    public Date getFechaFinal(){
        return fechaF;
    }
    public void setFechaFinal(Date f){
        fechaF = f;
    }
    public Socio getSocioLocal(){
        return socioLocal;
    }
    public void setSocioLocal(Socio s){
        socioLocal = s;
    }
    public String getFinanciador(){
        return financiador;
    }
    public void setFinanciador(String f){
        financiador = f;
    }
    public float getFinanciacion(){
        return financiacionApor;
    }
    public void setFinanciacion(float f){
        financiacionApor = f;
    }
    public ArrayList<PVoluntario> getVoluntarios(){
        return voluntarios;
    }
    public boolean addVoluntario(PVoluntario v){
        return voluntarios.add(v);
    }
    public void removeVoluntario(PVoluntario v){
        voluntarios.remove(v);
    }
}

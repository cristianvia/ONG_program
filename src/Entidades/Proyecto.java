/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
    , @NamedQuery(name = "Proyecto.findByIdproyecto", query = "SELECT p FROM Proyecto p WHERE p.idproyecto = :idproyecto")
    , @NamedQuery(name = "Proyecto.findByPais", query = "SELECT p FROM Proyecto p WHERE p.pais = :pais")
    , @NamedQuery(name = "Proyecto.findByLocalizacion", query = "SELECT p FROM Proyecto p WHERE p.localizacion = :localizacion")
    , @NamedQuery(name = "Proyecto.findBySubLineaA", query = "SELECT p FROM Proyecto p WHERE p.subLineaA = :subLineaA")
    , @NamedQuery(name = "Proyecto.findByFechaI", query = "SELECT p FROM Proyecto p WHERE p.fechaI = :fechaI")
    , @NamedQuery(name = "Proyecto.findByFechaF", query = "SELECT p FROM Proyecto p WHERE p.fechaF = :fechaF")
    , @NamedQuery(name = "Proyecto.findByFinanciador", query = "SELECT p FROM Proyecto p WHERE p.financiador = :financiador")
    , @NamedQuery(name = "Proyecto.findByFinanciacionApor", query = "SELECT p FROM Proyecto p WHERE p.financiacionApor = :financiacionApor")
    , @NamedQuery(name = "Proyecto.findByVoluntarios", query = "SELECT p FROM Proyecto p WHERE p.voluntarios = :voluntarios")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproyecto")
    private Integer idproyecto;
    @Column(name = "pais")
    private String pais;
    @Column(name = "localizacion")
    private String localizacion;
    @Column(name = "subLineaA")
    private String subLineaA;
    @Column(name = "fechaI")
    @Temporal(TemporalType.DATE)
    private Date fechaI;
    @Column(name = "fechaF")
    @Temporal(TemporalType.DATE)
    private Date fechaF;
    @Column(name = "financiador")
    private String financiador;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "financiacionApor")
    private Float financiacionApor;
    @Column(name = "voluntarios")
    private String voluntarios;
    @JoinColumn(name = "lineaAccion", referencedColumnName = "idLa")
    @ManyToOne(optional = false)
    private Lineaaccion lineaAccion;
    @JoinColumn(name = "socioLocal", referencedColumnName = "idSocio")
    @ManyToOne(optional = false)
    private Socio socioLocal;

    public Proyecto() {
    }

    public Proyecto(Integer idproyecto) {
        this.idproyecto = idproyecto;
    }

    public Integer getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(Integer idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getSubLineaA() {
        return subLineaA;
    }

    public void setSubLineaA(String subLineaA) {
        this.subLineaA = subLineaA;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public String getFinanciador() {
        return financiador;
    }

    public void setFinanciador(String financiador) {
        this.financiador = financiador;
    }

    public Float getFinanciacionApor() {
        return financiacionApor;
    }

    public void setFinanciacionApor(Float financiacionApor) {
        this.financiacionApor = financiacionApor;
    }

    public String getVoluntarios() {
        return voluntarios;
    }

    public void setVoluntarios(String voluntarios) {
        this.voluntarios = voluntarios;
    }

    public Lineaaccion getLineaAccion() {
        return lineaAccion;
    }

    public void setLineaAccion(Lineaaccion lineaAccion) {
        this.lineaAccion = lineaAccion;
    }

    public Socio getSocioLocal() {
        return socioLocal;
    }

    public void setSocioLocal(Socio socioLocal) {
        this.socioLocal = socioLocal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproyecto != null ? idproyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.idproyecto == null && other.idproyecto != null) || (this.idproyecto != null && !this.idproyecto.equals(other.idproyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Proyecto[ idproyecto=" + idproyecto + " ]";
    }
    
}

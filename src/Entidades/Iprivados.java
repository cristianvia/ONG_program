/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "iprivados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iprivados.findAll", query = "SELECT i FROM Iprivados i")
    , @NamedQuery(name = "Iprivados.findByIdiPrivados", query = "SELECT i FROM Iprivados i WHERE i.idiPrivados = :idiPrivados")
    , @NamedQuery(name = "Iprivados.findByPCuota", query = "SELECT i FROM Iprivados i WHERE i.pCuota = :pCuota")
    , @NamedQuery(name = "Iprivados.findByAportParticular", query = "SELECT i FROM Iprivados i WHERE i.aportParticular = :aportParticular")
    , @NamedQuery(name = "Iprivados.findByAportEmpresa", query = "SELECT i FROM Iprivados i WHERE i.aportEmpresa = :aportEmpresa")
    , @NamedQuery(name = "Iprivados.findByAportHerencia", query = "SELECT i FROM Iprivados i WHERE i.aportHerencia = :aportHerencia")
    , @NamedQuery(name = "Iprivados.findByAportInstitucion", query = "SELECT i FROM Iprivados i WHERE i.aportInstitucion = :aportInstitucion")
    , @NamedQuery(name = "Iprivados.findByAportExtraordi", query = "SELECT i FROM Iprivados i WHERE i.aportExtraordi = :aportExtraordi")})
public class Iprivados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idiPrivados")
    private Integer idiPrivados;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pCuota")
    private Float pCuota;
    @Column(name = "aportParticular")
    private Float aportParticular;
    @Column(name = "aportEmpresa")
    private Float aportEmpresa;
    @Column(name = "aportHerencia")
    private Float aportHerencia;
    @Column(name = "aportInstitucion")
    private Float aportInstitucion;
    @Column(name = "aportExtraordi")
    private Float aportExtraordi;
    @JoinTable(name = "ingresos_has_iprivados", joinColumns = {
        @JoinColumn(name = "iPrivados_idiPrivados", referencedColumnName = "idiPrivados")}, inverseJoinColumns = {
        @JoinColumn(name = "ingresos_idIngresos", referencedColumnName = "idIngresos")})
    @ManyToMany
    private List<Ingresos> ingresosList;
    @JoinColumn(name = "idSocio", referencedColumnName = "idSocio")
    @ManyToOne(optional = false)
    private Socio idSocio;

    public Iprivados() {
    }

    public Iprivados(Integer idiPrivados) {
        this.idiPrivados = idiPrivados;
    }

    public Integer getIdiPrivados() {
        return idiPrivados;
    }

    public void setIdiPrivados(Integer idiPrivados) {
        this.idiPrivados = idiPrivados;
    }

    public Float getPCuota() {
        return pCuota;
    }

    public void setPCuota(Float pCuota) {
        this.pCuota = pCuota;
    }

    public Float getAportParticular() {
        return aportParticular;
    }

    public void setAportParticular(Float aportParticular) {
        this.aportParticular = aportParticular;
    }

    public Float getAportEmpresa() {
        return aportEmpresa;
    }

    public void setAportEmpresa(Float aportEmpresa) {
        this.aportEmpresa = aportEmpresa;
    }

    public Float getAportHerencia() {
        return aportHerencia;
    }

    public void setAportHerencia(Float aportHerencia) {
        this.aportHerencia = aportHerencia;
    }

    public Float getAportInstitucion() {
        return aportInstitucion;
    }

    public void setAportInstitucion(Float aportInstitucion) {
        this.aportInstitucion = aportInstitucion;
    }

    public Float getAportExtraordi() {
        return aportExtraordi;
    }

    public void setAportExtraordi(Float aportExtraordi) {
        this.aportExtraordi = aportExtraordi;
    }

    @XmlTransient
    public List<Ingresos> getIngresosList() {
        return ingresosList;
    }

    public void setIngresosList(List<Ingresos> ingresosList) {
        this.ingresosList = ingresosList;
    }

    public Socio getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Socio idSocio) {
        this.idSocio = idSocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idiPrivados != null ? idiPrivados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iprivados)) {
            return false;
        }
        Iprivados other = (Iprivados) object;
        if ((this.idiPrivados == null && other.idiPrivados != null) || (this.idiPrivados != null && !this.idiPrivados.equals(other.idiPrivados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Iprivados[ idiPrivados=" + idiPrivados + " ]";
    }
    
}

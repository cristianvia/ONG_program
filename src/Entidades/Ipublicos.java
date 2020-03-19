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
@Table(name = "ipublicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ipublicos.findAll", query = "SELECT i FROM Ipublicos i")
    , @NamedQuery(name = "Ipublicos.findByIdIPublicos", query = "SELECT i FROM Ipublicos i WHERE i.idIPublicos = :idIPublicos")
    , @NamedQuery(name = "Ipublicos.findBySubEstatal", query = "SELECT i FROM Ipublicos i WHERE i.subEstatal = :subEstatal")
    , @NamedQuery(name = "Ipublicos.findBySubAuton", query = "SELECT i FROM Ipublicos i WHERE i.subAuton = :subAuton")
    , @NamedQuery(name = "Ipublicos.findBySubLocal", query = "SELECT i FROM Ipublicos i WHERE i.subLocal = :subLocal")
    , @NamedQuery(name = "Ipublicos.findBySubEuro", query = "SELECT i FROM Ipublicos i WHERE i.subEuro = :subEuro")})
public class Ipublicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIPublicos")
    private Integer idIPublicos;
    @Column(name = "subEstatal")
    private String subEstatal;
    @Column(name = "subAuton")
    private String subAuton;
    @Column(name = "subLocal")
    private String subLocal;
    @Column(name = "subEuro")
    private String subEuro;
    @JoinTable(name = "ipublicos_has_ingresos", joinColumns = {
        @JoinColumn(name = "iPublicos_idIPublicos", referencedColumnName = "idIPublicos")}, inverseJoinColumns = {
        @JoinColumn(name = "ingresos_idIngresos", referencedColumnName = "idIngresos")})
    @ManyToMany
    private List<Ingresos> ingresosList;

    public Ipublicos() {
    }

    public Ipublicos(Integer idIPublicos) {
        this.idIPublicos = idIPublicos;
    }

    public Integer getIdIPublicos() {
        return idIPublicos;
    }

    public void setIdIPublicos(Integer idIPublicos) {
        this.idIPublicos = idIPublicos;
    }

    public String getSubEstatal() {
        return subEstatal;
    }

    public void setSubEstatal(String subEstatal) {
        this.subEstatal = subEstatal;
    }

    public String getSubAuton() {
        return subAuton;
    }

    public void setSubAuton(String subAuton) {
        this.subAuton = subAuton;
    }

    public String getSubLocal() {
        return subLocal;
    }

    public void setSubLocal(String subLocal) {
        this.subLocal = subLocal;
    }

    public String getSubEuro() {
        return subEuro;
    }

    public void setSubEuro(String subEuro) {
        this.subEuro = subEuro;
    }

    @XmlTransient
    public List<Ingresos> getIngresosList() {
        return ingresosList;
    }

    public void setIngresosList(List<Ingresos> ingresosList) {
        this.ingresosList = ingresosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIPublicos != null ? idIPublicos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ipublicos)) {
            return false;
        }
        Ipublicos other = (Ipublicos) object;
        if ((this.idIPublicos == null && other.idIPublicos != null) || (this.idIPublicos != null && !this.idIPublicos.equals(other.idIPublicos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Ipublicos[ idIPublicos=" + idIPublicos + " ]";
    }
    
}

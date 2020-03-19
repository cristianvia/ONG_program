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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personal.findAll", query = "SELECT p FROM Personal p")
    , @NamedQuery(name = "Personal.findByIdPersonal", query = "SELECT p FROM Personal p WHERE p.idPersonal = :idPersonal")})
public class Personal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPersonal")
    private Integer idPersonal;
    @JoinTable(name = "personal_has_sedecentral", joinColumns = {
        @JoinColumn(name = "personal_idPersonal", referencedColumnName = "idPersonal")}, inverseJoinColumns = {
        @JoinColumn(name = "sedeCentral_idSedeCentral", referencedColumnName = "idSedeCentral")})
    @ManyToMany
    private List<Sedecentral> sedecentralList;
    @ManyToMany(mappedBy = "personalList")
    private List<Delegacion> delegacionList;
    @JoinColumn(name = "idPersonal", referencedColumnName = "idpersona", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public Personal() {
    }

    public Personal(Integer idPersonal) {
        this.idPersonal = idPersonal;
    }

    public Integer getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(Integer idPersonal) {
        this.idPersonal = idPersonal;
    }

    @XmlTransient
    public List<Sedecentral> getSedecentralList() {
        return sedecentralList;
    }

    public void setSedecentralList(List<Sedecentral> sedecentralList) {
        this.sedecentralList = sedecentralList;
    }

    @XmlTransient
    public List<Delegacion> getDelegacionList() {
        return delegacionList;
    }

    public void setDelegacionList(List<Delegacion> delegacionList) {
        this.delegacionList = delegacionList;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonal != null ? idPersonal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personal)) {
            return false;
        }
        Personal other = (Personal) object;
        if ((this.idPersonal == null && other.idPersonal != null) || (this.idPersonal != null && !this.idPersonal.equals(other.idPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Personal[ idPersonal=" + idPersonal + " ]";
    }
    
}

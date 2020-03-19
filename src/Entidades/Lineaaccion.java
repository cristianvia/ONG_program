/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "lineaaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lineaaccion.findAll", query = "SELECT l FROM Lineaaccion l")
    , @NamedQuery(name = "Lineaaccion.findByIdLa", query = "SELECT l FROM Lineaaccion l WHERE l.idLa = :idLa")})
public class Lineaaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLa")
    private Integer idLa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lineaAccion")
    private List<Proyecto> proyectoList;

    public Lineaaccion() {
    }

    public Lineaaccion(Integer idLa) {
        this.idLa = idLa;
    }

    public Integer getIdLa() {
        return idLa;
    }

    public void setIdLa(Integer idLa) {
        this.idLa = idLa;
    }

    @XmlTransient
    public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLa != null ? idLa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lineaaccion)) {
            return false;
        }
        Lineaaccion other = (Lineaaccion) object;
        if ((this.idLa == null && other.idLa != null) || (this.idLa != null && !this.idLa.equals(other.idLa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Lineaaccion[ idLa=" + idLa + " ]";
    }
    
}

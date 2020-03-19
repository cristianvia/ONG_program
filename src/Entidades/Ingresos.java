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
import javax.persistence.ManyToMany;
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
@Table(name = "ingresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingresos.findAll", query = "SELECT i FROM Ingresos i")
    , @NamedQuery(name = "Ingresos.findByIdIngresos", query = "SELECT i FROM Ingresos i WHERE i.idIngresos = :idIngresos")})
public class Ingresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIngresos")
    private Integer idIngresos;
    @ManyToMany(mappedBy = "ingresosList")
    private List<Ipublicos> ipublicosList;
    @ManyToMany(mappedBy = "ingresosList")
    private List<Iprivados> iprivadosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ingresosidIngresos")
    private List<Ong> ongList;

    public Ingresos() {
    }

    public Ingresos(Integer idIngresos) {
        this.idIngresos = idIngresos;
    }

    public Integer getIdIngresos() {
        return idIngresos;
    }

    public void setIdIngresos(Integer idIngresos) {
        this.idIngresos = idIngresos;
    }

    @XmlTransient
    public List<Ipublicos> getIpublicosList() {
        return ipublicosList;
    }

    public void setIpublicosList(List<Ipublicos> ipublicosList) {
        this.ipublicosList = ipublicosList;
    }

    @XmlTransient
    public List<Iprivados> getIprivadosList() {
        return iprivadosList;
    }

    public void setIprivadosList(List<Iprivados> iprivadosList) {
        this.iprivadosList = iprivadosList;
    }

    @XmlTransient
    public List<Ong> getOngList() {
        return ongList;
    }

    public void setOngList(List<Ong> ongList) {
        this.ongList = ongList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIngresos != null ? idIngresos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingresos)) {
            return false;
        }
        Ingresos other = (Ingresos) object;
        if ((this.idIngresos == null && other.idIngresos != null) || (this.idIngresos != null && !this.idIngresos.equals(other.idIngresos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Ingresos[ idIngresos=" + idIngresos + " ]";
    }
    
}

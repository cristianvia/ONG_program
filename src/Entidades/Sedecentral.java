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
@Table(name = "sedecentral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sedecentral.findAll", query = "SELECT s FROM Sedecentral s")
    , @NamedQuery(name = "Sedecentral.findByIdSedeCentral", query = "SELECT s FROM Sedecentral s WHERE s.idSedeCentral = :idSedeCentral")
    , @NamedQuery(name = "Sedecentral.findByNombre", query = "SELECT s FROM Sedecentral s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "Sedecentral.findByDireccion", query = "SELECT s FROM Sedecentral s WHERE s.direccion = :direccion")
    , @NamedQuery(name = "Sedecentral.findByTelefono", query = "SELECT s FROM Sedecentral s WHERE s.telefono = :telefono")
    , @NamedQuery(name = "Sedecentral.findByCiudad", query = "SELECT s FROM Sedecentral s WHERE s.ciudad = :ciudad")})
public class Sedecentral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSedeCentral")
    private Integer idSedeCentral;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "ciudad")
    private String ciudad;
    @ManyToMany(mappedBy = "sedecentralList")
    private List<Personal> personalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sedeCentralidSedeCentral")
    private List<Ong> ongList;

    public Sedecentral() {
    }

    public Sedecentral(Integer idSedeCentral) {
        this.idSedeCentral = idSedeCentral;
    }

    public Integer getIdSedeCentral() {
        return idSedeCentral;
    }

    public void setIdSedeCentral(Integer idSedeCentral) {
        this.idSedeCentral = idSedeCentral;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @XmlTransient
    public List<Personal> getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List<Personal> personalList) {
        this.personalList = personalList;
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
        hash += (idSedeCentral != null ? idSedeCentral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sedecentral)) {
            return false;
        }
        Sedecentral other = (Sedecentral) object;
        if ((this.idSedeCentral == null && other.idSedeCentral != null) || (this.idSedeCentral != null && !this.idSedeCentral.equals(other.idSedeCentral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Sedecentral[ idSedeCentral=" + idSedeCentral + " ]";
    }
    
}

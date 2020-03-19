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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "delegacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delegacion.findAll", query = "SELECT d FROM Delegacion d")
    , @NamedQuery(name = "Delegacion.findByIddelegacion", query = "SELECT d FROM Delegacion d WHERE d.iddelegacion = :iddelegacion")
    , @NamedQuery(name = "Delegacion.findByNombre", query = "SELECT d FROM Delegacion d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Delegacion.findByDireccion", query = "SELECT d FROM Delegacion d WHERE d.direccion = :direccion")
    , @NamedQuery(name = "Delegacion.findByTelefono", query = "SELECT d FROM Delegacion d WHERE d.telefono = :telefono")
    , @NamedQuery(name = "Delegacion.findByCiudad", query = "SELECT d FROM Delegacion d WHERE d.ciudad = :ciudad")})
public class Delegacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddelegacion")
    private Integer iddelegacion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "ciudad")
    private String ciudad;
    @JoinTable(name = "personal_has_delegacion", joinColumns = {
        @JoinColumn(name = "delegacion_iddelegacion", referencedColumnName = "iddelegacion")}, inverseJoinColumns = {
        @JoinColumn(name = "personal_idPersonal", referencedColumnName = "idPersonal")})
    @ManyToMany
    private List<Personal> personalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegacion")
    private List<Ong> ongList;

    public Delegacion() {
    }

    public Delegacion(Integer iddelegacion) {
        this.iddelegacion = iddelegacion;
    }

    public Integer getIddelegacion() {
        return iddelegacion;
    }

    public void setIddelegacion(Integer iddelegacion) {
        this.iddelegacion = iddelegacion;
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
        hash += (iddelegacion != null ? iddelegacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Delegacion)) {
            return false;
        }
        Delegacion other = (Delegacion) object;
        if ((this.iddelegacion == null && other.iddelegacion != null) || (this.iddelegacion != null && !this.iddelegacion.equals(other.iddelegacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Delegacion[ iddelegacion=" + iddelegacion + " ]";
    }
    
}

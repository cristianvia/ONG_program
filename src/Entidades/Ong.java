/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "ong")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ong.findAll", query = "SELECT o FROM Ong o")
    , @NamedQuery(name = "Ong.findByIdONG", query = "SELECT o FROM Ong o WHERE o.ongPK.idONG = :idONG")
    , @NamedQuery(name = "Ong.findByNombre", query = "SELECT o FROM Ong o WHERE o.nombre = :nombre")
    , @NamedQuery(name = "Ong.findBySedeCentral", query = "SELECT o FROM Ong o WHERE o.sedeCentral = :sedeCentral")
    , @NamedQuery(name = "Ong.findByIngresos", query = "SELECT o FROM Ong o WHERE o.ingresos = :ingresos")
    , @NamedQuery(name = "Ong.findByDelegaciones", query = "SELECT o FROM Ong o WHERE o.ongPK.delegaciones = :delegaciones")})
public class Ong implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OngPK ongPK;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "sedeCentral")
    private String sedeCentral;
    @Basic(optional = false)
    @Column(name = "ingresos")
    private float ingresos;
    @JoinColumn(name = "delegaciones", referencedColumnName = "iddelegacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Delegacion delegacion;
    @JoinColumn(name = "ingresos_idIngresos", referencedColumnName = "idIngresos")
    @ManyToOne(optional = false)
    private Ingresos ingresosidIngresos;
    @JoinColumn(name = "sedeCentral_idSedeCentral", referencedColumnName = "idSedeCentral")
    @ManyToOne(optional = false)
    private Sedecentral sedeCentralidSedeCentral;

    public Ong() {
    }

    public Ong(OngPK ongPK) {
        this.ongPK = ongPK;
    }

    public Ong(OngPK ongPK, float ingresos) {
        this.ongPK = ongPK;
        this.ingresos = ingresos;
    }

    public Ong(int idONG, int delegaciones) {
        this.ongPK = new OngPK(idONG, delegaciones);
    }

    public OngPK getOngPK() {
        return ongPK;
    }

    public void setOngPK(OngPK ongPK) {
        this.ongPK = ongPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSedeCentral() {
        return sedeCentral;
    }

    public void setSedeCentral(String sedeCentral) {
        this.sedeCentral = sedeCentral;
    }

    public float getIngresos() {
        return ingresos;
    }

    public void setIngresos(float ingresos) {
        this.ingresos = ingresos;
    }

    public Delegacion getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(Delegacion delegacion) {
        this.delegacion = delegacion;
    }

    public Ingresos getIngresosidIngresos() {
        return ingresosidIngresos;
    }

    public void setIngresosidIngresos(Ingresos ingresosidIngresos) {
        this.ingresosidIngresos = ingresosidIngresos;
    }

    public Sedecentral getSedeCentralidSedeCentral() {
        return sedeCentralidSedeCentral;
    }

    public void setSedeCentralidSedeCentral(Sedecentral sedeCentralidSedeCentral) {
        this.sedeCentralidSedeCentral = sedeCentralidSedeCentral;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ongPK != null ? ongPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ong)) {
            return false;
        }
        Ong other = (Ong) object;
        if ((this.ongPK == null && other.ongPK != null) || (this.ongPK != null && !this.ongPK.equals(other.ongPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Ong[ ongPK=" + ongPK + " ]";
    }
    
}

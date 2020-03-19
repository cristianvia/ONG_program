/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Cristian
 */
@Embeddable
public class OngPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idONG")
    private int idONG;
    @Basic(optional = false)
    @Column(name = "delegaciones")
    private int delegaciones;

    public OngPK() {
    }

    public OngPK(int idONG, int delegaciones) {
        this.idONG = idONG;
        this.delegaciones = delegaciones;
    }

    public int getIdONG() {
        return idONG;
    }

    public void setIdONG(int idONG) {
        this.idONG = idONG;
    }

    public int getDelegaciones() {
        return delegaciones;
    }

    public void setDelegaciones(int delegaciones) {
        this.delegaciones = delegaciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idONG;
        hash += (int) delegaciones;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OngPK)) {
            return false;
        }
        OngPK other = (OngPK) object;
        if (this.idONG != other.idONG) {
            return false;
        }
        if (this.delegaciones != other.delegaciones) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.OngPK[ idONG=" + idONG + ", delegaciones=" + delegaciones + " ]";
    }
    
}

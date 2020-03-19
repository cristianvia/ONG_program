/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "pcontratado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pcontratado.findAll", query = "SELECT p FROM Pcontratado p")
    , @NamedQuery(name = "Pcontratado.findByIdPContratado", query = "SELECT p FROM Pcontratado p WHERE p.idPContratado = :idPContratado")
    , @NamedQuery(name = "Pcontratado.findByUsuario", query = "SELECT p FROM Pcontratado p WHERE p.usuario = :usuario")
    , @NamedQuery(name = "Pcontratado.findByPassword", query = "SELECT p FROM Pcontratado p WHERE p.password = :password")
    , @NamedQuery(name = "Pcontratado.findByRol", query = "SELECT p FROM Pcontratado p WHERE p.rol = :rol")})
public class Pcontratado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPContratado")
    private Integer idPContratado;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;
    @Column(name = "rol")
    private String rol;
    @JoinColumn(name = "idPContratado", referencedColumnName = "idpersona", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public Pcontratado() {
    }

    public Pcontratado(Integer idPContratado) {
        this.idPContratado = idPContratado;
    }

    public Integer getIdPContratado() {
        return idPContratado;
    }

    public void setIdPContratado(Integer idPContratado) {
        this.idPContratado = idPContratado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
        hash += (idPContratado != null ? idPContratado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pcontratado)) {
            return false;
        }
        Pcontratado other = (Pcontratado) object;
        if ((this.idPContratado == null && other.idPContratado != null) || (this.idPContratado != null && !this.idPContratado.equals(other.idPContratado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pcontratado[ idPContratado=" + idPContratado + " ]";
    }
    
}

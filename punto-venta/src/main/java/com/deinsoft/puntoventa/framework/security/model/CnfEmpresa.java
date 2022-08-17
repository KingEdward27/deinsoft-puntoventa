/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.framework.security.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author EDWARD-PC
 */
@Entity(name = "secEmpresa")
@Table(name = "cnf_empresa")
public class CnfEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_empresa_id")
    private Integer id;
    
    @Size(max = 300)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Size(max = 300)
    @Column(name = "nro_documento")
    private String nroDocumento;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Size(max = 13)
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "token")
    @Size(max = 1000)
    private String token;
    
    @Column(name = "estado")
    private Character estado;

    @ManyToOne
    @JoinColumn(name = "cnf_tipo_documento_id")
    private CnfTipoDocumento cnfTipoDocumento;
    
    public CnfEmpresa() {
    }

    public CnfEmpresa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public CnfTipoDocumento getCnfTipoDocumento() {
        return cnfTipoDocumento;
    }

    public void setCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento) {
        this.cnfTipoDocumento = cnfTipoDocumento;
    }


    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CnfEmpresa)) {
            return false;
        }
        CnfEmpresa other = (CnfEmpresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CnfEmpresa{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", nroDocumento=" + nroDocumento + ", direccion=" + direccion + ", telefono=" + telefono + ", token=" + token + ", estado=" + estado + ", cnfTipoDocumento=" + cnfTipoDocumento + '}';
    }

    
    
}

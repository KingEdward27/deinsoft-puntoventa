package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity(name = "segUsuario")
@Table(name = "seg_usuario")
public class SegUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_usuario_id", nullable = false, unique = true)
    private long id;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @Size(max = 255)
    @Column(name = "password", length = 255, nullable = true)
    private String password;

    @NotEmpty
    @Size(max = 10)
    @Column(name = "estado", length = 10, nullable = false)
    private int estado;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;
    
    @Transient
    private List<SegRolUsuario> listSegRolUsuario;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public CnfEmpresa getCnfEmpresa() {
        return cnfEmpresa;
    }

    public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
        this.cnfEmpresa = cnfEmpresa;
    }

    public List<SegRolUsuario> getListSegRolUsuario() {
        return listSegRolUsuario;
    }

    public void setListSegRolUsuario(List<SegRolUsuario> listSegRolUsuario) {
        this.listSegRolUsuario = listSegRolUsuario;
    }

    @Override
    public String toString() {
        return "segUsuario [id=" + id + ", email=" + email + ", nombre=" + nombre + ", password=" + password + ", estado=" + estado + "]";
    }

}

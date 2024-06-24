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
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("1")
    @Column(name = "estado", nullable = false)
    private int estado;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;
    
    @Transient
    private List<SegRolUsuario> listSegRolUsuario;
    
    @Transient
    private String rucEmpresa;
    
    @Transient
    private String nombreEmpresa;
    
    @Column(name = "flag_recover_password", nullable = true)
    private byte flagRecoverPassword;

    @ColumnDefault("0")
    @Transient
    private String tokenRecoverPassword;
    
    @Transient
    private int perfilEmpresa;
     
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

    public String getRucEmpresa() {
        return rucEmpresa;
    }

    public void setRucEmpresa(String rucEmpresa) {
        this.rucEmpresa = rucEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public byte getFlagRecoverPassword() {
        return flagRecoverPassword;
    }

    public void setFlagRecoverPassword(byte flagRecoverPassword) {
        this.flagRecoverPassword = flagRecoverPassword;
    }

    public String getTokenRecoverPassword() {
        return tokenRecoverPassword;
    }

    public void setTokenRecoverPassword(String tokenRecoverPassword) {
        this.tokenRecoverPassword = tokenRecoverPassword;
    }

    public int getPerfilEmpresa() {
        return perfilEmpresa;
    }

    public void setPerfilEmpresa(int perfilEmpresa) {
        this.perfilEmpresa = perfilEmpresa;
    }

}

package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "cnfSubCategoria")
@Table(name = "cnf_sub_categoria")
public class CnfSubCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_sub_categoria_id", nullable = false, unique = true)
    private long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = true)
    private String nombre;

    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_categoria_id")
    private CnfCategoria cnfCategoria;

    @OneToOne
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;

    @Transient
    private long cnfEmpresaId;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public CnfCategoria getCnfCategoria() {
        return cnfCategoria;
    }

    public void setCnfCategoria(CnfCategoria cnfCategoria) {
        this.cnfCategoria = cnfCategoria;
    }

    public CnfEmpresa getCnfEmpresa() {
        return cnfEmpresa;
    }

    public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
        this.cnfEmpresa = cnfEmpresa;
    }

    public long getCnfEmpresaId() {
        return cnfEmpresaId;
    }

    public void setCnfEmpresaId(long cnfEmpresaId) {
        this.cnfEmpresaId = cnfEmpresaId;
    }

    @Override
    public String toString() {
        return "cnfSubCategoria [id=" + id + ", cnfEmpresa=" + (cnfEmpresa != null ? cnfEmpresa : "") + ", cnfCategoria=" + (cnfCategoria != null ? cnfCategoria : "") + ", nombre=" + nombre + ", flagEstado=" + flagEstado + "]";
    }

}

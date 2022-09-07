package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "cnfCategoria")
@Table(name = "cnf_categoria")
public class CnfCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_categoria_id", nullable = false, unique = true)
    private long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = true)
    private String nombre;

    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado;

    @OneToOne
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;

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

    public CnfEmpresa getCnfEmpresa() {
        return cnfEmpresa;
    }

    public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
        this.cnfEmpresa = cnfEmpresa;
    }

    @Override
    public String toString() {
        return "cnfCategoria [id=" + id + ", cnfEmpresa=" + (cnfEmpresa != null ? cnfEmpresa : "") + ", nombre=" + nombre + ", flagEstado=" + flagEstado + "]";
    }

}

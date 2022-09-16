package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "cnfFormaPago")
@Table(name = "cnf_forma_pago")
public class CnfFormaPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_forma_pago_id", nullable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = true)
    private String nombre;

    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado;

    @OneToOne
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;

    @Column(name = "tipo", length = 1, nullable = true)
    private Integer tipo;
    
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "cnfFormaPago [id=" + id + ", cnfEmpresa=" + (cnfEmpresa != null ? cnfEmpresa : "") + ", nombre=" + nombre + ", flagEstado=" + flagEstado + "]";
    }

}

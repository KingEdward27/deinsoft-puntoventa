package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "cnfImpuestoCondicion")
@Table(name = "cnf_impuesto_condicion")
public class CnfImpuestoCondicion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_impuesto_condicion_id", nullable = false, unique = true)
    private long id;

    @Size(max = 200)
    @Column(name = "nombre", length = 200, nullable = true)
    private String nombre;

    @Size(max = 2)
    @Column(name = "codigo_sunat", length = 2, nullable = true)
    private String codigoSunat;

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

    public String getCodigoSunat() {
        return codigoSunat;
    }

    public void setCodigoSunat(String codigoSunat) {
        this.codigoSunat = codigoSunat;
    }

    @Override
    public String toString() {
        return "cnfImpuestoCondicion [id=" + id + ", nombre=" + nombre + ", codigoSunat=" + codigoSunat + "]";
    }

}

package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "invTipoMovAlmacen")
@Table(name = "inv_tipo_mov_almacen")
public class InvTipoMovAlmacen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_tipo_mov_almacen_id", nullable = false, unique = true)
    private long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = true)
    private String nombre;

    @Size(max = 2)
    @Column(name = "codigo_sunat", length = 2, nullable = true)
    private String codigoSunat;

    @Size(max = 2)
    @Column(name = "naturaleza", length = 2, nullable = true)
    private String naturaleza;

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

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    @Override
    public String toString() {
        return "invTipoMovAlmacen [id=" + id + ", nombre=" + nombre + ", codigoSunat=" + codigoSunat + ", naturaleza=" + naturaleza + "]";
    }

}

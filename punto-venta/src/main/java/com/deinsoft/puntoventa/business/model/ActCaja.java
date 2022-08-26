package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "actCaja")
@Table(name = "act_caja")
public class ActCaja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "act_caja_id", nullable = false, unique = true)
    private long id;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Size(max = 1)
    @Column(name = "estado", length = 1, nullable = true)
    private String estado;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "actCaja [id=" + id + ", nombre=" + nombre + ", estado=" + estado + "]";
    }

}

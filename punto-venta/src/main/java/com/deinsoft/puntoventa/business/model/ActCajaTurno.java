package com.deinsoft.puntoventa.business.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "actCajaTurno")
@Table(name = "act_caja_turno")
public class ActCajaTurno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_caja_turno_id", nullable = false, unique = true)
    private long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "fecha_apertura", nullable = true)
    private LocalDateTime fechaApertura;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "fecha_cierre", nullable = true)
    private LocalDateTime fechaCierre;

    @Size(max = 1)
    @Column(name = "estado", length = 1, nullable = true)
    private String estado;

    @OneToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;

    @OneToOne
    @JoinColumn(name = "act_caja_id")
    private ActCaja actCaja;
    
    @Column(name = "monto_apertura", length = 18, nullable = true)
    private BigDecimal montoApertura;
    
    @Column(name = "monto_cierre", length = 18, nullable = true)
    private BigDecimal montoCierre;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    public BigDecimal getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(BigDecimal montoApertura) {
        this.montoApertura = montoApertura;
    }

    public BigDecimal getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(BigDecimal montoCierre) {
        this.montoCierre = montoCierre;
    }

    public ActCaja getActCaja() {
        return actCaja;
    }

    public void setActCaja(ActCaja actCaja) {
        this.actCaja = actCaja;
    }


    @Override
    public String toString() {
        return "actCajaTurno [id=" + id + ", segUsuario=" + (segUsuario != null ? segUsuario : "") + ", fechaApertura=" + fechaApertura + ", fechaCierre=" + fechaCierre + ", estado=" + estado + "]";
    }

}

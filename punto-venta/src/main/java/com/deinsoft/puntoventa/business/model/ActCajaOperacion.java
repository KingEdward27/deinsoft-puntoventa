package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import java.math.BigDecimal;

@Entity(name = "actCajaOperacion")
@Table(name = "act_caja_operacion")
public class ActCajaOperacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_caja_operacion_id", nullable = false, unique = true)
    private long id;

    @Column(name = "monto", length = 18, nullable = true)
    private BigDecimal monto;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @Column(name = "fecha_registro", nullable = true)
    private LocalDateTime fechaRegistro;

    @Size(max = 1)
    @Column(name = "flag_ingreso", length = 1, nullable = true)
    private String flagIngreso;

    @Size(max = 1)
    @Column(name = "estado", length = 1, nullable = true)
    private String estado;

    @OneToOne
    @JoinColumn(name = "act_caja_turno_id")
    private ActCajaTurno actCajaTurno;

    @OneToOne
    @JoinColumn(name = "act_comprobante_id")
    private ActComprobante actComprobante;


    @OneToOne
    @JoinColumn(name = "act_pago_id")
    private ActPago actPago;
    
    @Column(name = "detail", length = 300, nullable = true)
    private String detail;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFlagIngreso() {
        return flagIngreso;
    }

    public void setFlagIngreso(String flagIngreso) {
        this.flagIngreso = flagIngreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ActCajaTurno getActCajaTurno() {
        return actCajaTurno;
    }

    public void setActCajaTurno(ActCajaTurno actCajaTurno) {
        this.actCajaTurno = actCajaTurno;
    }

    public ActComprobante getActComprobante() {
        return actComprobante;
    }

    public void setActComprobante(ActComprobante actComprobante) {
        this.actComprobante = actComprobante;
    }

    public ActPago getActPago() {
        return actPago;
    }

    public void setActPago(ActPago actPago) {
        this.actPago = actPago;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    @Override
    public String toString() {
        return "actCajaOperacion [id=" + id + ", actComprobante=" 
                + (actComprobante != null ? actComprobante : "") + ", actPago=" 
                + (actPago != null ? actPago : "")
                + ", monto=" + monto + ", fecha=" + fecha + ", fechaRegistro=" + fechaRegistro + ", flagIngreso=" + flagIngreso + ", estado=" + estado + "]";
    }

}

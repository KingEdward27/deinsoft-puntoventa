package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "actPagoProgramacion")
@Table(name = "act_pago_programacion")
public class ActPagoProgramacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_pago_programacion_id", nullable = false, unique = true)
    private long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_vencimiento", length = 0, nullable = true)
    private LocalDate fechaVencimiento;

    @Column(name = "monto", length = 18, nullable = true)
    private BigDecimal monto;

    @Column(name = "monto_pendiente", length = 18, nullable = true)
    private BigDecimal montoPendiente;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "act_comprobante_id")
    private ActComprobante actComprobante;

    @Transient
    private SegUsuario segUsuario;
    
    @Transient
    BigDecimal amtToPay;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(BigDecimal montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public ActComprobante getActComprobante() {
        return actComprobante;
    }

    public void setActComprobante(ActComprobante actComprobante) {
        this.actComprobante = actComprobante;
    }

    public BigDecimal getAmtToPay() {
        return amtToPay;
    }

    public void setAmtToPay(BigDecimal amtToPay) {
        this.amtToPay = amtToPay;
    }

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    @Override
    public String toString() {
        return "actPagoProgramacion [id=" + id + ", actComprobante=" + (actComprobante != null ? actComprobante : "") + ", fecha=" + fecha + ", fechaVencimiento=" + fechaVencimiento + ", monto=" + monto + ", montoPendiente=" + montoPendiente + "]";
    }

}

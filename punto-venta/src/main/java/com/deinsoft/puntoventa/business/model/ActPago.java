package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "actPago")
@Table(name = "act_pago")
public class ActPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "act_pago_id", nullable = false, unique = true)
    private long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @Column(name = "monto", length = 18, nullable = true)
    private BigDecimal monto;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "act_pago_programacion_id")
    private ActPagoProgramacion actPagoProgramacion;

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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public ActPagoProgramacion getActPagoProgramacion() {
        return actPagoProgramacion;
    }

    public void setActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {
        this.actPagoProgramacion = actPagoProgramacion;
    }

    @Override
    public String toString() {
        return "actPago [id=" + id + ", actPagoProgramacion=" + (actPagoProgramacion != null ? actPagoProgramacion : "") + ", fecha=" + fecha + ", monto=" + monto + "]";
    }

}

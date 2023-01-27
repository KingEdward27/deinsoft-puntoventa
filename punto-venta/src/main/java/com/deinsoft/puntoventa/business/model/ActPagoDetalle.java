package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "actPagoDetalle")
@Table(name = "act_pago_Detalle")
public class ActPagoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_pago_detalle_id", nullable = false, unique = true)
    private long id;

    @OneToOne
    @JoinColumn(name = "act_pago_id")
    private ActPago actPago;

    @OneToOne
    @JoinColumn(name = "act_pago_programacion_id")
    private ActPagoProgramacion actPagoProgramacion;
    
    @Column(name = "monto", length = 18, nullable = true)
    private BigDecimal monto;

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

    public ActPagoProgramacion getActPagoProgramacion() {
        return actPagoProgramacion;
    }

    public void setActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {
        this.actPagoProgramacion = actPagoProgramacion;
    }

    public ActPago getActPago() {
        return actPago;
    }

    public void setActPago(ActPago actPago) {
        this.actPago = actPago;
    }


}

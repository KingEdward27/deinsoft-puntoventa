package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "cnfFormaPagoDetalle")
@Table(name = "cnf_forma_pago_detalle")
public class CnfFormaPagoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cnf_forma_pago_detalle_id", nullable = false, unique = true)
    private long id;

    @Size(max = 10)
    @Column(name = "modo_dias_intervalo", length = 10, nullable = true)
    private Integer modoDiasIntervalo;

    @Column(name = "modo_porcentaje", length = 16, nullable = true)
    private BigDecimal modoPorcentaje;

    @Column(name = "modo_monto", length = 16, nullable = true)
    private BigDecimal modoMonto;

    @Size(max = 10)
    @Column(name = "modo_dia_vencimiento", length = 10, nullable = true)
    private Integer modoDiaVencimiento;

    @OneToOne
    @JoinColumn(name = "cnf_forma_pago_id")
    private CnfFormaPago cnfFormaPago;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public BigDecimal getModoPorcentaje() {
        return modoPorcentaje;
    }

    public void setModoPorcentaje(BigDecimal modoPorcentaje) {
        this.modoPorcentaje = modoPorcentaje;
    }

    public BigDecimal getModoMonto() {
        return modoMonto;
    }

    public void setModoMonto(BigDecimal modoMonto) {
        this.modoMonto = modoMonto;
    }

    public Integer getModoDiasIntervalo() {
        return modoDiasIntervalo;
    }

    public void setModoDiasIntervalo(Integer modoDiasIntervalo) {
        this.modoDiasIntervalo = modoDiasIntervalo;
    }

    public Integer getModoDiaVencimiento() {
        return modoDiaVencimiento;
    }

    public void setModoDiaVencimiento(Integer modoDiaVencimiento) {
        this.modoDiaVencimiento = modoDiaVencimiento;
    }

    public CnfFormaPago getCnfFormaPago() {
        return cnfFormaPago;
    }

    public void setCnfFormaPago(CnfFormaPago cnfFormaPago) {
        this.cnfFormaPago = cnfFormaPago;
    }

    @Override
    public String toString() {
        return "cnfFormaPagoDetalle [id=" + id + ", cnfFormaPago=" + (cnfFormaPago != null ? cnfFormaPago : "") + ", modoDiasIntervalo=" + modoDiasIntervalo + ", modoPorcentaje=" + modoPorcentaje + ", modoMonto=" + modoMonto + ", modoDiaVencimiento=" + modoDiaVencimiento + "]";
    }

}

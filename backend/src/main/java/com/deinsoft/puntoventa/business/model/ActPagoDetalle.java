package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="actPagoDetalle")
@Table(name="act_pago_detalle")
public class ActPagoDetalle implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_act_pago_detalle_id")
	@SequenceGenerator(name="seq_act_pago_detalle_id", sequenceName = "seq_act_pago_detalle_id",allocationSize=1)
	@Column(name="act_pago_detalle_id",nullable = false, unique = true)
	private long id;
	
	@Column(name="monto", length = 21, nullable = true)
	private BigDecimal monto;
	
	@OneToOne
    @JoinColumn(name="act_pago_id")
	private ActPago actPago;
	
	@OneToOne
    @JoinColumn(name="act_pago_programacion_id")
	private ActPagoProgramacion actPagoProgramacion;
	
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
	public ActPago getActPago() {
		return actPago;
	}

	public void setActPago(ActPago actPago) {
		this.actPago = actPago;
	}
	public ActPagoProgramacion getActPagoProgramacion() {
		return actPagoProgramacion;
	}

	public void setActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {
		this.actPagoProgramacion = actPagoProgramacion;
	}
	@Override
	public String toString() {
		return "actPagoDetalle [id=" + id + ", actPagoProgramacion=" + (actPagoProgramacion!=null?actPagoProgramacion:"") + ", actPago=" + (actPago!=null?actPago:"") + ", monto=" + monto + "]";	}

	
	
	
}

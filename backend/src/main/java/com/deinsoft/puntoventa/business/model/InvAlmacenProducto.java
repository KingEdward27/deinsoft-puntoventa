package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="invAlmacenProducto")
@Table(name="inv_almacen_producto")
public class InvAlmacenProducto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inv_almacen_producto_id")
	@SequenceGenerator(name="seq_inv_almacen_producto_id", sequenceName = "seq_inv_almacen_producto_id",allocationSize=1)
	@Column(name="inv_movimiento_producto_id",nullable = false, unique = true)
	private long id;
	
	@Column(name="cantidad", length = 15, nullable = true)
	private BigDecimal cantidad;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="inv_almacen_id")
	private InvAlmacen invAlmacen;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_producto_id")
	private CnfProducto cnfProducto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public InvAlmacen getInvAlmacen() {
		return invAlmacen;
	}

	public void setInvAlmacen(InvAlmacen invAlmacen) {
		this.invAlmacen = invAlmacen;
	}
	public CnfProducto getCnfProducto() {
		return cnfProducto;
	}

	public void setCnfProducto(CnfProducto cnfProducto) {
		this.cnfProducto = cnfProducto;
	}
	@Override
	public String toString() {
		return "invAlmacenProducto [id=" + id + ", cnfProducto=" + (cnfProducto!=null?cnfProducto:"") + ", invAlmacen=" + (invAlmacen!=null?invAlmacen:"") + ", cantidad=" + cantidad + "]";	}

	
	
	
}

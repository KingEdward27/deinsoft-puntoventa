package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="invAlmacen")
@Table(name="inv_almacen")
public class InvAlmacen implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inv_almacen_id")
	@SequenceGenerator(name="seq_inv_almacen_id", sequenceName = "seq_inv_almacen_id",allocationSize=1)
	@Column(name="inv_almacen_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 100)
	@Column(name="nombre", length = 100, nullable = true)
	private String nombre;
	
	@Size(max = 1)
	@Column(name="flag_estado", length = 1, nullable = true)
	private String flagEstado;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_local_id")
	private CnfLocal cnfLocal;
	
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
	public String getFlagEstado() {
		return flagEstado;
	}
	public void setFlagEstado(String flagEstado) {
		this.flagEstado = flagEstado;
	}
	public CnfLocal getCnfLocal() {
		return cnfLocal;
	}

	public void setCnfLocal(CnfLocal cnfLocal) {
		this.cnfLocal = cnfLocal;
	}
	@Override
	public String toString() {
		return "invAlmacen [id=" + id + ", cnfLocal=" + (cnfLocal!=null?cnfLocal:"") + ", nombre=" + nombre + ", flagEstado=" + flagEstado + "]";	}

	
	
	
}

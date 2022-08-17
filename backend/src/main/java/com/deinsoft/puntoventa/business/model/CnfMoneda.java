package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfMoneda")
@Table(name="cnf_moneda")
public class CnfMoneda implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_moneda_id")
	@SequenceGenerator(name="seq_cnf_moneda_id", sequenceName = "seq_cnf_moneda_id",allocationSize=1)
	@Column(name="cnf_moneda_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 3)
	@Column(name="codigo", length = 3, nullable = true)
	private String codigo;
	
	@Size(max = 50)
	@Column(name="nombre", length = 50, nullable = true)
	private String nombre;
	
	@Size(max = 1)
	@Column(name="flag_estado", length = 1, nullable = true)
	private String flagEstado;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	@Override
	public String toString() {
		return "cnfMoneda [id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", flagEstado=" + flagEstado + "]";	}

	
	
	
}

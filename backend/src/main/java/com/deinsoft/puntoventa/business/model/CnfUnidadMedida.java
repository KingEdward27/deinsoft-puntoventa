package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfUnidadMedida")
@Table(name="cnf_unidad_medida")
public class CnfUnidadMedida implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_unidad_medida_id")
	@SequenceGenerator(name="seq_cnf_unidad_medida_id", sequenceName = "seq_cnf_unidad_medida_id",allocationSize=1)
	@Column(name="cnf_unidad_medida_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 3)
	@Column(name="codigo_sunat", length = 3, nullable = true)
	private String codigoSunat;
	
	@Size(max = 100)
	@Column(name="nombre", length = 100, nullable = true)
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
	public String getCodigoSunat() {
		return codigoSunat;
	}
	public void setCodigoSunat(String codigoSunat) {
		this.codigoSunat = codigoSunat;
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
		return "cnfUnidadMedida [id=" + id + ", codigoSunat=" + codigoSunat + ", nombre=" + nombre + ", flagEstado=" + flagEstado + "]";	}

	
	
	
}

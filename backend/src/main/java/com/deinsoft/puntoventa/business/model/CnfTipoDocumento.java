package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfTipoDocumento")
@Table(name="cnf_tipo_documento")
public class CnfTipoDocumento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_tipo_documento_id")
	@SequenceGenerator(name="seq_cnf_tipo_documento_id", sequenceName = "seq_cnf_tipo_documento_id",allocationSize=1)
	@Column(name="cnf_tipo_documento_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 3)
	@Column(name="abreviatura", length = 3, nullable = true)
	private String abreviatura;
	
	@Size(max = 50)
	@Column(name="nombre", length = 50, nullable = true)
	private String nombre;
	
	@Size(max = 2)
	@Column(name="codigo_sunat", length = 2, nullable = true)
	private String codigoSunat;
	
	@Size(max = 1)
	@Column(name="flag_estado", length = 1, nullable = true)
	private String flagEstado;
	
	@NotEmpty
	@Size(max = 255)
	@Column(name="name", length = 255, nullable = false)
	private String name;
	
	@Size(max = 255)
	@Column(name="value", length = 255, nullable = true)
	private String value;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigoSunat() {
		return codigoSunat;
	}
	public void setCodigoSunat(String codigoSunat) {
		this.codigoSunat = codigoSunat;
	}
	public String getFlagEstado() {
		return flagEstado;
	}
	public void setFlagEstado(String flagEstado) {
		this.flagEstado = flagEstado;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "cnfTipoDocumento [id=" + id + ", abreviatura=" + abreviatura + ", nombre=" + nombre + ", codigoSunat=" + codigoSunat + ", flagEstado=" + flagEstado + ", name=" + name + ", value=" + value + "]";	}

	
	
	
}

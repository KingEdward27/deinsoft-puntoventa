package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfTipoComprobante")
@Table(name="cnf_tipo_comprobante")
public class CnfTipoComprobante implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_tipo_comprobante_id")
	@SequenceGenerator(name="seq_cnf_tipo_comprobante_id", sequenceName = "seq_cnf_tipo_comprobante_id",allocationSize=1)
	@Column(name="cnf_tipo_comprobante_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 100)
	@Column(name="nombre", length = 100, nullable = true)
	private String nombre;
	
	@Size(max = 2)
	@Column(name="codigo_sunat", length = 2, nullable = true)
	private String codigoSunat;
	
	@Size(max = 3)
	@Column(name="codigo", length = 3, nullable = true)
	private String codigo;
	
	@Size(max = 1)
	@Column(name="flag_electronico", length = 1, nullable = true)
	private String flagElectronico;
	
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
	public String getCodigoSunat() {
		return codigoSunat;
	}
	public void setCodigoSunat(String codigoSunat) {
		this.codigoSunat = codigoSunat;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getFlagElectronico() {
		return flagElectronico;
	}
	public void setFlagElectronico(String flagElectronico) {
		this.flagElectronico = flagElectronico;
	}
	@Override
	public String toString() {
		return "cnfTipoComprobante [id=" + id + ", nombre=" + nombre + ", codigoSunat=" + codigoSunat + ", codigo=" + codigo + ", flagElectronico=" + flagElectronico + "]";	}

	
	
	
}

package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfDistrito")
@Table(name="cnf_distrito")
public class CnfDistrito implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cnf_distrito_id",nullable = false, unique = true)
	private long id;
	
	@NotEmpty
	@Size(max = 255)
	@Column(name="nombre", length = 255, nullable = false)
	private String nombre;
	
	@Size(max = 16)
	@Column(name="value", length = 16, nullable = true)
	private String value;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_provincia_id")
	private CnfProvincia cnfProvincia;
	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public CnfProvincia getCnfProvincia() {
		return cnfProvincia;
	}

	public void setCnfProvincia(CnfProvincia cnfProvincia) {
		this.cnfProvincia = cnfProvincia;
	}
	@Override
	public String toString() {
		return "cnfDistrito [id=" + id + ", cnfProvincia=" + (cnfProvincia!=null?cnfProvincia:"") + ", nombre=" + nombre + ", value=" + value + "]";	}

	
	
	
}

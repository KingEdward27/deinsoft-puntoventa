package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfRegion")
@Table(name="cnf_region")
public class CnfRegion implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_region_id")
	@SequenceGenerator(name="seq_cnf_region_id", sequenceName = "seq_cnf_region_id",allocationSize=1)
	@Column(name="cnf_region_id",nullable = false, unique = true)
	private long id;
	
	@NotEmpty
	@Size(max = 255)
	@Column(name="nombre", length = 255, nullable = false)
	private String nombre;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_pais_id")
	private CnfPais cnfPais;
	
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
	public CnfPais getCnfPais() {
		return cnfPais;
	}

	public void setCnfPais(CnfPais cnfPais) {
		this.cnfPais = cnfPais;
	}
	@Override
	public String toString() {
		return "cnfRegion [id=" + id + ", cnfPais=" + (cnfPais!=null?cnfPais:"") + ", nombre=" + nombre + "]";	}

	
	
	
}

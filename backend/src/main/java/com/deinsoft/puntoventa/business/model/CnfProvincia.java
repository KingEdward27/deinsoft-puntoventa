package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfProvincia")
@Table(name="cnf_provincia")
public class CnfProvincia implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_provincia_id")
	@SequenceGenerator(name="seq_cnf_provincia_id", sequenceName = "seq_cnf_provincia_id",allocationSize=1)
	@Column(name="cnf_provincia_id",nullable = false, unique = true)
	private long id;
	
	@NotEmpty
	@Size(max = 255)
	@Column(name="nombre", length = 255, nullable = false)
	private String nombre;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_region_id")
	private CnfRegion cnfRegion;
	
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
	public CnfRegion getCnfRegion() {
		return cnfRegion;
	}

	public void setCnfRegion(CnfRegion cnfRegion) {
		this.cnfRegion = cnfRegion;
	}
	@Override
	public String toString() {
		return "cnfProvincia [id=" + id + ", cnfRegion=" + (cnfRegion!=null?cnfRegion:"") + ", nombre=" + nombre + "]";	}

	
	
	
}

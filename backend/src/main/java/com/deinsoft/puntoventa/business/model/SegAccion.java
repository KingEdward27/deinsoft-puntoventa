package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="segAccion")
@Table(name="seg_accion")
public class SegAccion implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_seg_accion_id")
	@SequenceGenerator(name="seq_seg_accion_id", sequenceName = "seq_seg_accion_id",allocationSize=1)
	@Column(name="seg_accion_id",nullable = false, unique = true)
	private long id;
	
	@NotEmpty
	@Size(max = 255)
	@Column(name="nombre", length = 255, nullable = false)
	private String nombre;
	
	@Size(max = 255)
	@Column(name="descripcion", length = 255, nullable = true)
	private String descripcion;
	
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "segAccion [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";	}

	
	
	
}

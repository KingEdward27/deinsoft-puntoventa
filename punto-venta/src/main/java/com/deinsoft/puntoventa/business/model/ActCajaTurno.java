package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
@Entity(name="actCajaTurno")
@Table(name="act_caja_turno")
public class ActCajaTurno implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="act_caja_turno_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 0)
	@Column(name="fecha_apertura", length = 0, nullable = true)
	private LocalDateTime fechaApertura;
	
	@Size(max = 0)
	@Column(name="fecha_cierre", length = 0, nullable = true)
	private LocalDateTime fechaCierre;
	
	@Size(max = 1)
	@Column(name="estado", length = 1, nullable = true)
	private String estado;
	
	@OneToOne
    @JoinColumn(name="seg_usuario_id")
	private SegUsuario segUsuario;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(LocalDateTime fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(LocalDateTime fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public SegUsuario getSegUsuario() {
		return segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}
	@Override
	public String toString() {
		return "actCajaTurno [id=" + id + ", segUsuario=" + (segUsuario!=null?segUsuario:"") + ", fechaApertura=" + fechaApertura + ", fechaCierre=" + fechaCierre + ", estado=" + estado + "]";	}

	
	
	
}

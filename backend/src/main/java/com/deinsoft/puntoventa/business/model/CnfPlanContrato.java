package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfPlanContrato")
@Table(name="cnf_plan_contrato")
public class CnfPlanContrato implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_plan_contrato_id")
	@SequenceGenerator(name="seq_cnf_plan_contrato_id", sequenceName = "seq_cnf_plan_contrato_id",allocationSize=1)
	@Column(name="cnf_plan_contrato_id",nullable = false, unique = true)
	private long id;
	
	@NotEmpty
	@Size(max = 100)
	@Column(name="nombre", length = 100, nullable = false)
	private String nombre;
	
	@NotNull
	@Column(name="precio", length = 21, nullable = false)
	private BigDecimal precio;
	
	@OneToOne
    @JoinColumn(name="cnf_empresa_id")
	private CnfEmpresa cnfEmpresa;
	
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
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public CnfEmpresa getCnfEmpresa() {
		return cnfEmpresa;
	}

	public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
		this.cnfEmpresa = cnfEmpresa;
	}
	@Override
	public String toString() {
		return "cnfPlanContrato [id=" + id + ", cnfEmpresa=" + (cnfEmpresa!=null?cnfEmpresa:"") + ", nombre=" + nombre + ", precio=" + precio + "]";	}

	
	
	
}

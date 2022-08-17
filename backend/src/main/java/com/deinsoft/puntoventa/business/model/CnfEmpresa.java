package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfEmpresa")
@Table(name="cnf_empresa")
public class CnfEmpresa implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_empresa_id")
	@SequenceGenerator(name="seq_cnf_empresa_id", sequenceName = "seq_cnf_empresa_id",allocationSize=1)
	@Column(name="cnf_empresa_id",nullable = false, unique = true)
	private long id;
	
	@NotEmpty
	@Size(max = 200)
	@Column(name="nombre", length = 200, nullable = false)
	private String nombre;
	
	@Size(max = 300)
	@Column(name="descripcion", length = 300, nullable = true)
	private String descripcion;
	
	@Size(max = 11)
	@Column(name="nro_documento", length = 11, nullable = true)
	private String nroDocumento;
	
	@Size(max = 100)
	@Column(name="direccion", length = 100, nullable = true)
	private String direccion;
	
	@Size(max = 20)
	@Column(name="telefono", length = 20, nullable = true)
	private String telefono;
	
	@Size(max = 45)
	@Column(name="empresacol", length = 45, nullable = true)
	private String empresacol;
	
	@Size(max = 1)
	@Column(name="estado", length = 1, nullable = true)
	private String estado;
	
	@Size(max = 1000)
	@Column(name="token", length = 1000, nullable = true)
	private String token;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_tipo_documento_id")
	private CnfTipoDocumento cnfTipoDocumento;
	
	@OneToOne
    @JoinColumn(name="cnf_distrito_id")
	private CnfDistrito cnfDistrito;
	
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
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmpresacol() {
		return empresacol;
	}
	public void setEmpresacol(String empresacol) {
		this.empresacol = empresacol;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public CnfTipoDocumento getCnfTipoDocumento() {
		return cnfTipoDocumento;
	}

	public void setCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento) {
		this.cnfTipoDocumento = cnfTipoDocumento;
	}
	public CnfDistrito getCnfDistrito() {
		return cnfDistrito;
	}

	public void setCnfDistrito(CnfDistrito cnfDistrito) {
		this.cnfDistrito = cnfDistrito;
	}
	@Override
	public String toString() {
		return "cnfEmpresa [id=" + id + ", cnfDistrito=" + (cnfDistrito!=null?cnfDistrito:"") + ", cnfTipoDocumento=" + (cnfTipoDocumento!=null?cnfTipoDocumento:"") + ", nombre=" + nombre + ", descripcion=" + descripcion + ", nroDocumento=" + nroDocumento + ", direccion=" + direccion + ", telefono=" + telefono + ", empresacol=" + empresacol + ", estado=" + estado + ", token=" + token + "]";	}

	
	
	
}

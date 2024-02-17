package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name="cnfTipoSistema")
@Table(name="cnf_tipo_sistema")
public class CnfTipoSistema implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_tipo_sistema_id")
	@SequenceGenerator(name="seq_cnf_tipo_sistema_id", sequenceName = "seq_cnf_tipo_sistema_id",allocationSize=1)
	@Column(name="cnf_tipo_sistema_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 200)
	@Column(name="nombre", length = 200, nullable = true)
	private String nombre;
	
	@OneToMany(mappedBy = "cnfTipoSistema",fetch=FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonIgnoreProperties(value = { "cnfTipoSistema" }, allowSetters = true)
    private Set<CnfMenuTipoSistema> listCnfMenuTipoSistema;
	
	public void addCnfMenuTipoSistema(CnfMenuTipoSistema item){
	    item.setCnfTipoSistema(this);
	}
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
	public Set<CnfMenuTipoSistema> getListCnfMenuTipoSistema() {
		return listCnfMenuTipoSistema;
	}
	public void setListCnfMenuTipoSistema(Set<CnfMenuTipoSistema> listCnfMenuTipoSistema) {
		this.listCnfMenuTipoSistema = listCnfMenuTipoSistema;
	}
	@Override
	public String toString() {
		return "cnfTipoSistema [id=" + id + ", nombre=" + nombre + "]";	}

	
	
	
}

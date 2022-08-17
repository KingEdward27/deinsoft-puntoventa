package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="cnfNumComprobante")
@Table(name="cnf_num_comprobante")
public class CnfNumComprobante implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cnf_num_comprobante_id")
	@SequenceGenerator(name="seq_cnf_num_comprobante_id", sequenceName = "seq_cnf_num_comprobante_id",allocationSize=1)
	@Column(name="cnf_num_comprobante_id",nullable = false, unique = true)
	private long id;
	
	@Size(max = 4)
	@Column(name="serie", length = 4, nullable = true)
	private String serie;
	
	@Size(max = 10)
	@Column(name="ultimo_nro", length = 10, nullable = true)
	private int ultimoNro;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_tipo_comprobante_id")
	private CnfTipoComprobante cnfTipoComprobante;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_local_id")
	private CnfLocal cnfLocal;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public int getUltimoNro() {
		return ultimoNro;
	}
	public void setUltimoNro(int ultimoNro) {
		this.ultimoNro = ultimoNro;
	}
	public CnfTipoComprobante getCnfTipoComprobante() {
		return cnfTipoComprobante;
	}

	public void setCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante) {
		this.cnfTipoComprobante = cnfTipoComprobante;
	}
	public CnfLocal getCnfLocal() {
		return cnfLocal;
	}

	public void setCnfLocal(CnfLocal cnfLocal) {
		this.cnfLocal = cnfLocal;
	}
	@Override
	public String toString() {
		return "cnfNumComprobante [id=" + id + ", cnfLocal=" + (cnfLocal!=null?cnfLocal:"") + ", cnfTipoComprobante=" + (cnfTipoComprobante!=null?cnfTipoComprobante:"") + ", serie=" + serie + ", ultimoNro=" + ultimoNro + "]";	}

	
	
	
}

package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name="actContrato")
@Table(name="act_contrato")
public class ActContrato implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_act_contrato_id")
	@SequenceGenerator(name="seq_act_contrato_id", sequenceName = "seq_act_contrato_id",allocationSize=1)
	@Column(name="act_contrato_id",nullable = false, unique = true)
	private long id;
	
	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name="fecha", length = 0, nullable = false)
	private LocalDate fecha;
	
	@NotEmpty
	@Size(max = 0)
	@Column(name="fecha_registro", length = 0, nullable = false)
	private LocalDateTime fechaRegistro;
	
	@NotEmpty
	@Size(max = 1)
	@Column(name="flag_estado", length = 1, nullable = false)
	private String flagEstado;
	
	@NotEmpty
	@Size(max = 8)
	@Column(name="numero", length = 8, nullable = false)
	private String numero;
	
	@Size(max = 100)
	@Column(name="observacion", length = 100, nullable = true)
	private String observacion;
	
	@NotEmpty
	@Size(max = 4)
	@Column(name="serie", length = 4, nullable = false)
	private String serie;
	
	@Size(max = 10)
	@Column(name="nro_poste", length = 10, nullable = true)
	private String nroPoste;
	
	@Size(max = 1000)
	@Column(name="url_map", length = 1000, nullable = true)
	private String urlMap;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_maestro_id")
	private CnfMaestro cnfMaestro;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_local_id")
	private CnfLocal cnfLocal;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_tipo_comprobante_id")
	private CnfTipoComprobante cnfTipoComprobante;
	
	@OneToOne
    @JoinColumn(name="cnf_forma_pago_id")
	private CnfFormaPago cnfFormaPago;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_plan_contrato_id")
	private CnfPlanContrato cnfPlanContrato;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getFlagEstado() {
		return flagEstado;
	}
	public void setFlagEstado(String flagEstado) {
		this.flagEstado = flagEstado;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNroPoste() {
		return nroPoste;
	}
	public void setNroPoste(String nroPoste) {
		this.nroPoste = nroPoste;
	}
	public String getUrlMap() {
		return urlMap;
	}
	public void setUrlMap(String urlMap) {
		this.urlMap = urlMap;
	}
	public CnfMaestro getCnfMaestro() {
		return cnfMaestro;
	}

	public void setCnfMaestro(CnfMaestro cnfMaestro) {
		this.cnfMaestro = cnfMaestro;
	}
	public CnfLocal getCnfLocal() {
		return cnfLocal;
	}

	public void setCnfLocal(CnfLocal cnfLocal) {
		this.cnfLocal = cnfLocal;
	}
	public CnfTipoComprobante getCnfTipoComprobante() {
		return cnfTipoComprobante;
	}

	public void setCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante) {
		this.cnfTipoComprobante = cnfTipoComprobante;
	}
	public CnfFormaPago getCnfFormaPago() {
		return cnfFormaPago;
	}

	public void setCnfFormaPago(CnfFormaPago cnfFormaPago) {
		this.cnfFormaPago = cnfFormaPago;
	}
	public CnfPlanContrato getCnfPlanContrato() {
		return cnfPlanContrato;
	}

	public void setCnfPlanContrato(CnfPlanContrato cnfPlanContrato) {
		this.cnfPlanContrato = cnfPlanContrato;
	}
	@Override
	public String toString() {
		return "actContrato [id=" + id + ", cnfPlanContrato=" + (cnfPlanContrato!=null?cnfPlanContrato:"") + ", cnfTipoComprobante=" + (cnfTipoComprobante!=null?cnfTipoComprobante:"") + ", cnfLocal=" + (cnfLocal!=null?cnfLocal:"") + ", cnfFormaPago=" + (cnfFormaPago!=null?cnfFormaPago:"") + ", segUsuario=" + (segUsuario!=null?segUsuario:"") + ", cnfMoneda=" + (cnfMoneda!=null?cnfMoneda:"") + ", cnfMaestro=" + (cnfMaestro!=null?cnfMaestro:"") + ", fecha=" + fecha + ", fechaRegistro=" + fechaRegistro + ", flagEstado=" + flagEstado + ", numero=" + numero + ", observacion=" + observacion + ", serie=" + serie + ", nroPoste=" + nroPoste + ", urlMap=" + urlMap + "]";	}

	
	
	
}

package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name="actPago")
@Table(name="act_pago")
public class ActPago implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_act_pago_id")
	@SequenceGenerator(name="seq_act_pago_id", sequenceName = "seq_act_pago_id",allocationSize=1)
	@Column(name="act_pago_id",nullable = false, unique = true)
	private long id;
	
	@Column(name="billete", length = 21, nullable = true)
	private BigDecimal billete;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name="fecha", length = 0, nullable = true)
	private LocalDate fecha;
	
	@NotEmpty
	@Size(max = 0)
	@Column(name="fecha_registro", length = 0, nullable = false)
	private LocalDateTime fechaRegistro;
	
	@Column(name="igv", length = 21, nullable = true)
	private BigDecimal igv;
	
	@NotEmpty
	@Size(max = 8)
	@Column(name="numero", length = 8, nullable = false)
	private String numero;
	
	@NotEmpty
	@Size(max = 4)
	@Column(name="serie", length = 4, nullable = false)
	private String serie;
	
	@Column(name="subtotal", length = 21, nullable = true)
	private BigDecimal subtotal;
	
	@Column(name="total", length = 21, nullable = true)
	private BigDecimal total;
	
	@Column(name="vuelto", length = 21, nullable = true)
	private BigDecimal vuelto;
	
	@NotNull
	@Valid
	@OneToOne
    @JoinColumn(name="cnf_tipo_comprobante_id")
	private CnfTipoComprobante cnfTipoComprobante;
	
	@OneToMany(mappedBy = "actPago",fetch=FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonIgnoreProperties(value = { "actPago" }, allowSetters = true)
    private Set<ActPagoDetalle> listActPagoDetalle;
	
	public void addActPagoDetalle(ActPagoDetalle item){
	    item.setActPago(this);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BigDecimal getBillete() {
		return billete;
	}
	public void setBillete(BigDecimal billete) {
		this.billete = billete;
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
	public BigDecimal getIgv() {
		return igv;
	}
	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getVuelto() {
		return vuelto;
	}
	public void setVuelto(BigDecimal vuelto) {
		this.vuelto = vuelto;
	}
	public CnfTipoComprobante getCnfTipoComprobante() {
		return cnfTipoComprobante;
	}

	public void setCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante) {
		this.cnfTipoComprobante = cnfTipoComprobante;
	}
	public Set<ActPagoDetalle> getListActPagoDetalle() {
		return listActPagoDetalle;
	}
	public void setListActPagoDetalle(Set<ActPagoDetalle> listActPagoDetalle) {
		this.listActPagoDetalle = listActPagoDetalle;
	}
	@Override
	public String toString() {
		return "actPago [id=" + id + ", segUsuario=" + (segUsuario!=null?segUsuario:"") + ", cnfLocal=" + (cnfLocal!=null?cnfLocal:"") + ", actPagoProgramacion=" + (actPagoProgramacion!=null?actPagoProgramacion:"") + ", cnfTipoComprobante=" + (cnfTipoComprobante!=null?cnfTipoComprobante:"") + ", billete=" + billete + ", fecha=" + fecha + ", fechaRegistro=" + fechaRegistro + ", igv=" + igv + ", numero=" + numero + ", serie=" + serie + ", subtotal=" + subtotal + ", total=" + total + ", vuelto=" + vuelto + "]";	}

	
	
	
}

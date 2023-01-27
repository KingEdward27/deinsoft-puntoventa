package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "actPago")
@Table(name = "act_pago")
public class ActPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_pago_id", nullable = false, unique = true)
    private long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @NotEmpty
    @Size(max = 4)
    @Column(name = "serie", length = 4, nullable = false)
    private String serie;

    @NotEmpty
    @Size(max = 8)
    @Column(name = "numero", length = 8, nullable = false)
    private String numero;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "subtotal", length = 18, nullable = true)
    private BigDecimal subtotal;
    
    @Column(name = "igv", length = 18, nullable = true)
    private BigDecimal igv;
    
    @Column(name = "total", length = 18, nullable = true)
    private BigDecimal total;

    @Column(name = "billete", length = 14, nullable = true)
    private BigDecimal billete;

    @Column(name = "vuelto", length = 14, nullable = true)
    private BigDecimal vuelto;
    
    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "act_pago_programacion_id")
    private ActPagoProgramacion actPagoProgramacion;

    @OneToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;
    
    @OneToMany(mappedBy = "actPago", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"actPago"}, allowSetters = true)
    private Set<ActPagoDetalle> listActPagoDetalle;
     
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

    public ActPagoProgramacion getActPagoProgramacion() {
        return actPagoProgramacion;
    }

    public void setActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {
        this.actPagoProgramacion = actPagoProgramacion;
    }

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBillete() {
        return billete;
    }

    public void setBillete(BigDecimal billete) {
        this.billete = billete;
    }

    public BigDecimal getVuelto() {
        return vuelto;
    }

    public void setVuelto(BigDecimal vuelto) {
        this.vuelto = vuelto;
    }

    public Set<ActPagoDetalle> getListActPagoDetalle() {
        return listActPagoDetalle;
    }

    public void setListActPagoDetalle(Set<ActPagoDetalle> listActPagoDetalle) {
        this.listActPagoDetalle = listActPagoDetalle;
    }


}

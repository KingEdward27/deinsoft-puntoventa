package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "actOrdenDetalle")
@Table(name = "act_orden_detalle")
public class ActOrdenDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_orden_detalle_id", nullable = false, unique = true)
    private long id;

    @Size(max = 100)
    @Column(name = "descripcion", length = 100, nullable = true)
    private String descripcion;

    @NotNull
    @Column(name = "cantidad", length = 14, nullable = false)
    private BigDecimal cantidad;

    @NotNull
    @Column(name = "precio", length = 14, nullable = false)
    private BigDecimal precio;

    @Column(name = "descuento", length = 14, nullable = true)
    private BigDecimal descuento;

    @Column(name = "importe", length = 14, nullable = true)
    private BigDecimal importe;

    @NotNull
    @Column(name = "afectacion_igv", length = 20, nullable = false)
    private BigDecimal afectacionIgv;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "act_orden_id")
    private ActOrden actOrden;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_producto_id")
    private CnfProducto cnfProducto;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_impuesto_condicion_id")
    private CnfImpuestoCondicion cnfImpuestoCondicion;
    
    @Transient
    private Float porcentajeGanancia;
    
    //al comprar
    @Transient
    private BigDecimal precioVenta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getAfectacionIgv() {
        return afectacionIgv;
    }

    public void setAfectacionIgv(BigDecimal afectacionIgv) {
        this.afectacionIgv = afectacionIgv;
    }

    public ActOrden getActOrden() {
        return actOrden;
    }

    public void setActOrden(ActOrden actOrden) {
        this.actOrden = actOrden;
    }

    

    public CnfProducto getCnfProducto() {
        return cnfProducto;
    }

    public void setCnfProducto(CnfProducto cnfProducto) {
        this.cnfProducto = cnfProducto;
    }

    public CnfImpuestoCondicion getCnfImpuestoCondicion() {
        return cnfImpuestoCondicion;
    }

    public void setCnfImpuestoCondicion(CnfImpuestoCondicion cnfImpuestoCondicion) {
        this.cnfImpuestoCondicion = cnfImpuestoCondicion;
    }

    @Override
    public String toString() {
        return "actOrdenDetalle [id=" + id + ", actOrden=" + (actOrden != null ? actOrden : "") + ", cnfProducto=" + (cnfProducto != null ? cnfProducto : "") + ", cnfImpuestoCondicion=" + (cnfImpuestoCondicion != null ? cnfImpuestoCondicion : "") + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precio=" + precio + ", descuento=" + descuento + ", importe=" + importe + ", afectacionIgv=" + afectacionIgv + "]";
    }

    public Float getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public void setPorcentajeGanancia(Float porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    
}
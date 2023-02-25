package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "invMovimientoProducto")
@Table(name = "inv_movimiento_producto")
public class InvMovimientoProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_movimiento_producto_id", nullable = false, unique = true)
    private long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @Column(name = "fecha_registro", length = 0, nullable = true)
    private LocalDateTime fechaRegistro;

    @Column(name = "cantidad", length = 18, nullable = true)
    private BigDecimal cantidad;

    @Column(name = "valor", length = 18, nullable = true)
    private BigDecimal valor;
    
    @OneToOne
    @JoinColumn(name = "inv_almacen_id")
    private InvAlmacen invAlmacen;

    @OneToOne
    @JoinColumn(name = "cnf_producto_id")
    private CnfProducto cnfProducto;

    @OneToOne
    @JoinColumn(name = "act_comprobante_id")
    private ActComprobante actComprobante;

    @OneToOne
    @JoinColumn(name = "inv_mov_almacen_id")
    private InvMovAlmacen invMovAlmacen;
    
    @Transient
    private BigDecimal cant;
    
    @Transient
    private BigDecimal costo;
    
    @Transient
    private BigDecimal costoTotal;
    
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public InvAlmacen getInvAlmacen() {
        return invAlmacen;
    }

    public void setInvAlmacen(InvAlmacen invAlmacen) {
        this.invAlmacen = invAlmacen;
    }

    public CnfProducto getCnfProducto() {
        return cnfProducto;
    }

    public void setCnfProducto(CnfProducto cnfProducto) {
        this.cnfProducto = cnfProducto;
    }

    public ActComprobante getActComprobante() {
        return actComprobante;
    }

    public void setActComprobante(ActComprobante actComprobante) {
        this.actComprobante = actComprobante;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public InvMovAlmacen getInvMovAlmacen() {
        return invMovAlmacen;
    }

    public void setInvMovAlmacen(InvMovAlmacen invMovAlmacen) {
        this.invMovAlmacen = invMovAlmacen;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public BigDecimal getCant() {
        return cant;
    }

    public void setCant(BigDecimal cant) {
        this.cant = cant;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "invMovimientoProducto [id=" + id + ", actComprobante=" + (actComprobante != null ? actComprobante : "") + ", invAlmacen=" + (invAlmacen != null ? invAlmacen : "") + ", cnfProducto=" + (cnfProducto != null ? cnfProducto : "") + ", fecha=" + fecha + ", fechaRegistro=" + fechaRegistro + ", cantidad=" + cantidad + "]";
    }

}

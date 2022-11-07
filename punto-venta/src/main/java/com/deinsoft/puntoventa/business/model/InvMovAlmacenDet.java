package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "invMovAlmacenDet")
@Table(name = "inv_mov_almacen_det")
public class InvMovAlmacenDet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_mov_almacen_det_id", nullable = false, unique = true)
    private long id;

    @Column(name = "cantidad", length = 15, nullable = true)
    private BigDecimal cantidad;

    @Column(name = "precio", length = 15, nullable = true)
    private BigDecimal precio;

    @Column(name = "importe", length = 15, nullable = true)
    private BigDecimal importe;

    @Size(max = 30)
    @Column(name = "nroserie", length = 30, nullable = true)
    private String nroserie;

    @OneToOne
    @JoinColumn(name = "inv_mov_almacen_id")
    private InvMovAlmacen invMovAlmacen;

    @OneToOne
    @JoinColumn(name = "cnf_producto_id")
    private CnfProducto cnfProducto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getNroserie() {
        return nroserie;
    }

    public void setNroserie(String nroserie) {
        this.nroserie = nroserie;
    }

    public InvMovAlmacen getInvMovAlmacen() {
        return invMovAlmacen;
    }

    public void setInvMovAlmacen(InvMovAlmacen invMovAlmacen) {
        this.invMovAlmacen = invMovAlmacen;
    }

    public CnfProducto getCnfProducto() {
        return cnfProducto;
    }

    public void setCnfProducto(CnfProducto cnfProducto) {
        this.cnfProducto = cnfProducto;
    }

    @Override
    public String toString() {
        return "invMovAlmacenDet [id=" + id + ", cnfProducto=" + (cnfProducto != null ? cnfProducto : "") + ", invMovAlmacen=" + (invMovAlmacen != null ? invMovAlmacen : "") + ", cantidad=" + cantidad + ", precio=" + precio + ", importe=" + importe + ", nroserie=" + nroserie + "]";
    }

}

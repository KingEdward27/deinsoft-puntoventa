/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.bean;

import com.deinsoft.puntoventa.business.model.CnfCategoria;
import com.deinsoft.puntoventa.business.model.CnfEmpresa;
import com.deinsoft.puntoventa.business.model.CnfFormaPago;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.model.CnfMaestro;
import com.deinsoft.puntoventa.business.model.CnfMoneda;
import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.model.CnfTipoComprobante;
import com.deinsoft.puntoventa.business.model.CnfZona;
import com.deinsoft.puntoventa.business.model.InvAlmacen;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author EDWARD-PC
 */
public class ParamBean {
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaDesde;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaHasta;
    
    private String flagIsventa;

    private CnfMaestro cnfMaestro;

    private CnfEmpresa cnfEmpresa;
    
    private CnfFormaPago cnfFormaPago;

    private CnfMoneda cnfMoneda;

    private CnfLocal cnfLocal;

    private CnfTipoComprobante cnfTipoComprobante;

    private InvAlmacen invAlmacen;

    private CnfCategoria CnfCategoria;
    
    private CnfProducto cnfProducto;
    
    private CnfZona cnfZona;
    
    private String direccion;
    
    private int flagEstado;
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaVencimiento;
    
    public ParamBean() {
    }

    
    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getFlagIsventa() {
        return flagIsventa;
    }

    public void setFlagIsventa(String flagIsventa) {
        this.flagIsventa = flagIsventa;
    }

    public CnfMaestro getCnfMaestro() {
        return cnfMaestro;
    }

    public void setCnfMaestro(CnfMaestro cnfMaestro) {
        this.cnfMaestro = cnfMaestro;
    }

    public CnfFormaPago getCnfFormaPago() {
        return cnfFormaPago;
    }

    public void setCnfFormaPago(CnfFormaPago cnfFormaPago) {
        this.cnfFormaPago = cnfFormaPago;
    }

    public CnfMoneda getCnfMoneda() {
        return cnfMoneda;
    }

    public void setCnfMoneda(CnfMoneda cnfMoneda) {
        this.cnfMoneda = cnfMoneda;
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

    public InvAlmacen getInvAlmacen() {
        return invAlmacen;
    }

    public void setInvAlmacen(InvAlmacen invAlmacen) {
        this.invAlmacen = invAlmacen;
    }

    public CnfEmpresa getCnfEmpresa() {
        return cnfEmpresa;
    }

    public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
        this.cnfEmpresa = cnfEmpresa;
    }

    public CnfCategoria getCnfCategoria() {
        return CnfCategoria;
    }

    public void setCnfCategoria(CnfCategoria CnfCategoria) {
        this.CnfCategoria = CnfCategoria;
    }

    public CnfProducto getCnfProducto() {
        return cnfProducto;
    }

    public void setCnfProducto(CnfProducto cnfProducto) {
        this.cnfProducto = cnfProducto;
    }

    public CnfZona getCnfZona() {
        return cnfZona;
    }

    public void setCnfZona(CnfZona cnfZona) {
        this.cnfZona = cnfZona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(int flagEstado) {
        this.flagEstado = flagEstado;
    }
    
    
}

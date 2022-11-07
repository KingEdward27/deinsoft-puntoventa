package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "invMovAlmacen")
@Table(name = "inv_mov_almacen")
public class InvMovAlmacen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_mov_almacen_id", nullable = false, unique = true)
    private long id;

    @Size(max = 4)
    @Column(name = "serie", length = 4, nullable = true)
    private String serie;

    @Size(max = 8)
    @Column(name = "numero", length = 8, nullable = true)
    private String numero;

    @Size(max = 18)
    @Column(name = "numero_ref", length = 18, nullable = true)
    private String numeroRef;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @Size(max = 300)
    @Column(name = "observacion", length = 300, nullable = true)
    private String observacion;

    @Column(name = "subtotal", length = 15, nullable = true)
    private BigDecimal subtotal;

    @Column(name = "igv", length = 15, nullable = true)
    private BigDecimal igv;

    @Column(name = "total", length = 15, nullable = true)
    private BigDecimal total;

    @Column(name = "fechareg", nullable = false)
    private LocalDateTime fechareg;

    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado;

    @OneToOne
    @JoinColumn(name = "inv_tipo_mov_almacen_id")
    private InvTipoMovAlmacen invTipoMovAlmacen;

    @OneToOne
    @JoinColumn(name = "cnf_maestro_id")
    private CnfMaestro cnfMaestro;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_local_id")
    private CnfLocal cnfLocal;

    @OneToOne
    @JoinColumn(name = "cnf_tipo_comprobante_id")
    private CnfTipoComprobante cnfTipoComprobante;

    @OneToOne
    @JoinColumn(name = "inv_almacen_id")
    private InvAlmacen invAlmacen;

    @OneToMany(mappedBy = "invMovAlmacen", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"invMovAlmacen"}, allowSetters = true)
    private Set<InvMovAlmacenDet> listInvMovAlmacenDet;

    @OneToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;
    
    public void addInvMovAlmacenDet(InvMovAlmacenDet item) {
        item.setInvMovAlmacen(this);
    }

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroRef() {
        return numeroRef;
    }

    public void setNumeroRef(String numeroRef) {
        this.numeroRef = numeroRef;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public LocalDateTime getFechareg() {
        return fechareg;
    }

    public void setFechareg(LocalDateTime fechareg) {
        this.fechareg = fechareg;
    }


    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public InvTipoMovAlmacen getInvTipoMovAlmacen() {
        return invTipoMovAlmacen;
    }

    public void setInvTipoMovAlmacen(InvTipoMovAlmacen invTipoMovAlmacen) {
        this.invTipoMovAlmacen = invTipoMovAlmacen;
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

    public InvAlmacen getInvAlmacen() {
        return invAlmacen;
    }

    public void setInvAlmacen(InvAlmacen invAlmacen) {
        this.invAlmacen = invAlmacen;
    }

    public Set<InvMovAlmacenDet> getListInvMovAlmacenDet() {
        return listInvMovAlmacenDet;
    }

    public void setListInvMovAlmacenDet(Set<InvMovAlmacenDet> listInvMovAlmacenDet) {
        this.listInvMovAlmacenDet = listInvMovAlmacenDet;
    }

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    @Override
    public String toString() {
        return "invMovAlmacen [id=" + id + ", cnfLocal=" + (cnfLocal != null ? cnfLocal : "") 
                + ", cnfMaestro=" + (cnfMaestro != null ? cnfMaestro : "") 
                + ", cnfTipoComprobante=" + (cnfTipoComprobante != null ? cnfTipoComprobante : "") 
                + ", invAlmacen=" + (invAlmacen != null ? invAlmacen : "") + ", invTipoMovAlmacen=" 
                + (invTipoMovAlmacen != null ? invTipoMovAlmacen : "") + ", segUsuario=" 
//                + (segUsuario != null ? segUsuario : "") + ", serie=" + serie + ", numero=" 
                + numero + ", numeroRef=" + numeroRef + ", fecha=" + fecha + ", observacion=" 
                + observacion + ", subtotal=" + subtotal + ", igv=" + igv + ", total=" + total 
                + ", fechareg=" + fechareg + ", flagEstado=" + flagEstado + "]";
    }

}

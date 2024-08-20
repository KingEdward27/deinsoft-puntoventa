package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import java.lang.reflect.Field;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Type;
import org.springframework.validation.annotation.Validated;

@Entity(name = "actOrden")
@Table(name = "act_orden")
public class ActOrden implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "act_orden_id", nullable = false, unique = true)
//    private long id;

    @Id
    @Column(name = "act_orden_id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();
    
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = false)
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

    @Column(name = "billete", length = 14, nullable = true)
    private BigDecimal billete;

    @NotNull
    @Column(name = "total", length = 14, nullable = false)
    private BigDecimal total;

    @Column(name = "vuelto", length = 14, nullable = true)
    private BigDecimal vuelto;

    @Column(name = "descuento", length = 14, nullable = true)
    private BigDecimal descuento;

    @Column(name = "subtotal", length = 14, nullable = true)
    private BigDecimal subtotal;

    @Column(name = "igv", length = 14, nullable = true)
    private BigDecimal igv;

    @Size(max = 100)
    @Column(name = "observacion", length = 100, nullable = true)
    private String observacion;

    @NotEmpty
    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = false)
    private String flagEstado;

    @NotEmpty
    @Size(max = 1)
    @Column(name = "flag_isventa", length = 1, nullable = false)
    private String flagIsventa;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_maestro_id")
    private CnfMaestro cnfMaestro;

    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_forma_pago_id")
    private CnfFormaPago cnfFormaPago;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_moneda_id")
    private CnfMoneda cnfMoneda;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_local_id")
    private CnfLocal cnfLocal;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_tipo_comprobante_id")
    private CnfTipoComprobante cnfTipoComprobante;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "inv_almacen_id")
    private InvAlmacen invAlmacen;

    @OneToMany(mappedBy = "actOrden", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"actOrden"}, allowSetters = true)
    private Set<ActOrdenDetalle> listActOrdenDetalle;

    @OneToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;
    
    
    public void addActOrdenDetalle(ActOrdenDetalle item) {
        item.setActOrden(this);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public BigDecimal getBillete() {
        return billete;
    }

    public void setBillete(BigDecimal billete) {
        this.billete = billete;
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

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
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

    public Set<ActOrdenDetalle> getListActOrdenDetalle() {
        return listActOrdenDetalle;
    }

    public void setListActOrdenDetalle(Set<ActOrdenDetalle> listActOrdenDetalle) {
        this.listActOrdenDetalle = listActOrdenDetalle;
    }

   

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    @Override
    public String toString() {
        return "actComprobante [id=" + id  + ", cnfLocal=" + (cnfLocal != null ? cnfLocal : "") + ", invAlmacen=" 
                + (invAlmacen != null ? invAlmacen : "") + ", cnfMaestro=" + (cnfMaestro != null ? cnfMaestro : "") 
                + ", cnfTipoComprobante=" + (cnfTipoComprobante != null ? cnfTipoComprobante : "") + ", cnfFormaPago=" 
                + (cnfFormaPago != null ? cnfFormaPago : "") + ", cnfMoneda=" + (cnfMoneda != null ? cnfMoneda : "") + ", fecha=" 
                + fecha + ", serie=" + serie + ", numero=" + numero + ", fechaRegistro=" + fechaRegistro + ", billete=" + billete 
                + ", total=" + total + ", vuelto=" + vuelto + ", descuento=" + descuento + ", subtotal=" + subtotal + ", igv=" 
                + igv + ", observacion=" + observacion + ", flagEstado=" + flagEstado + ", flagIsventa=" + flagIsventa + "]";
    }
    public static Map<String, Object> toMap(ActOrden object, String[] visibles) {
        Map<String, Object> map = new HashMap<>();
        try {

            for (Field f : object.getClass().getDeclaredFields()) {
//            System.out.println(f.toString());
//                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getType());
                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getModifiers());
                if (visibles == null) {
                    if (f.toString().contains("final") || f.toString().contains("list")) {
                        continue;
                    }
                } else {
                    if (f.toString().contains("final") || f.toString().contains("list")
                            || !Arrays.asList(visibles).contains(f.getName())) {
                        continue;
                    }
                }

                map.put(f.getName(), f.get(object));

//                objectBuilder.add(f.getName(), f.get(facturaElectronica).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}

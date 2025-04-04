package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import java.lang.reflect.Field;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Entity(name = "actComprobante")
@Table(name = "act_comprobante")
@Getter
@Setter
public class ActComprobante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_comprobante_id", nullable = false, unique = true)
    private long id;

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

    @Size(max = 1)
    @Column(name = "envio_pse_flag", length = 1, nullable = true)
    private String envioPseFlag;

    @Size(max = 1000)
    @Column(name = "envio_pse_mensaje", length = 1000, nullable = true)
    private String envioPseMensaje;

    @Size(max = 300)
    @Column(name = "xmlhash", length = 300, nullable = true)
    private String xmlhash;

    @Size(max = 300)
    @Column(name = "codigoqr", length = 300, nullable = true)
    private String codigoqr;

    @Size(max = 300)
    @Column(name = "num_ticket", length = 300, nullable = true)
    private String numTicket;

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

    @OneToMany(mappedBy = "actComprobante", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"actComprobante"}, allowSetters = true)
    private Set<ActComprobanteDetalle> listActComprobanteDetalle;

    @OneToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;
    
    
    @Size(max = 4)
    @Column(name = "serie_ref", length = 4, nullable = false)
    private String serieRef;

    @Size(max = 8)
    @Column(name = "numero_ref", length = 8, nullable = false)
    private String numeroRef;


    @OneToMany(mappedBy = "actComprobante", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"actComprobante"}, allowSetters = true)
    private List<ActMedioPagoDetalle> listActMedioPagoDetalle;

    public void addActComprobanteDetalle(ActComprobanteDetalle item) {
        item.setActComprobante(this);
    }
    public void addActMedioPagoDetalle(ActMedioPagoDetalle item) {
        item.setActComprobante(this);
    }

    public static Map<String, Object> toMap(ActComprobante object, String[] visibles) {
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

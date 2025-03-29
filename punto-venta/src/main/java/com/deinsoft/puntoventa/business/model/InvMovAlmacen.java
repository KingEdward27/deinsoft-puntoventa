package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "invMovAlmacen")
@Table(name = "inv_mov_almacen")
@Data
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

    @OneToOne
    @JoinColumn(name = "inv_almacen_destino_id")
    private InvAlmacen invAlmacenDestino;

    
    public void addInvMovAlmacenDet(InvMovAlmacenDet item) {
        item.setInvMovAlmacen(this);
    }


}

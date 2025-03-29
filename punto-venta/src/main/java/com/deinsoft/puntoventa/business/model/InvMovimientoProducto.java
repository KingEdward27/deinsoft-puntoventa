package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "invMovimientoProducto")
@Table(name = "inv_movimiento_producto")
@Data
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

    @Transient
    private BigDecimal cantidadDescuento;

    @Transient
    private BigDecimal valorDescuento;
    @Transient
    private BigDecimal totalDescuento;

    @Transient
    private String simboloMoneda;

}

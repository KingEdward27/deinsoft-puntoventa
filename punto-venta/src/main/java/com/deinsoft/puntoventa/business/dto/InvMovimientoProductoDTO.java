package com.deinsoft.puntoventa.business.dto;

import com.deinsoft.puntoventa.business.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class InvMovimientoProductoDTO {
    private long id;

    private String local;
    private String almacen;
    private String producto;
    private String tipoMovimiento;
    private LocalDate fecha;

    private LocalDateTime fechaRegistro;

    private BigDecimal cantidad;


    private BigDecimal valor;


    private BigDecimal cantidadTotal;
    private BigDecimal costo;

    private BigDecimal costoTotal;

    private BigDecimal cantidadDescuento;

    private BigDecimal valorDescuento;

    private BigDecimal totalDescuento;

    private String simboloMoneda;

    private boolean forDiscount;
}

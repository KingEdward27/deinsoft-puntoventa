package com.deinsoft.puntoventa.business.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
public class MedioPagoDTO {

    private String nombre;

    private BigDecimal total;
}

package com.deinsoft.puntoventa.business.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "actMedioPagoDetalle")
@Table(name = "act_medio_pago_detalle")
@Data
public class ActMedioPagoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "act_medio_pago_detalle_id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "act_comprobante_id")
    private ActComprobante actComprobante;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_medio_pago_id")
    private CnfMedioPago cnfMedioPago;


    @NotNull
    @Column(name = "monto", length = 14, nullable = false)
    private BigDecimal monto;

    @PrePersist
    public void generarUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}

package com.deinsoft.puntoventa.business.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity(name = "cnfPaqueteProducto")
@Table(name = "cnf_paquete_producto")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CnfPaqueteProducto {
    @Id
    @Column(name = "cnf_paquete_producto_id")
    @Type(type = "uuid-char")
    private UUID id = UUID.randomUUID();

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_producto_id")
    private CnfProducto cnfProducto;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_producto_contenido_id")
    private CnfProducto cnfProductoContenido;

    @Column(name = "cantidad")
    private BigDecimal cantidad;



}

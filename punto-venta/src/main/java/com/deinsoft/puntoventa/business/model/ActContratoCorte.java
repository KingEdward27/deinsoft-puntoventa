package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "actContratoCorte")
@Table(name = "act_contrato_corte")
public class ActContratoCorte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_act_contrato_corte_id")
    @SequenceGenerator(name = "seq_act_contrato_corte_id", sequenceName = "seq_act_contrato_corte_id", allocationSize = 1)
    @Column(name = "act_contrato_corte_id", nullable = false, unique = true)
    private long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado; 

    @Column(name = "seg_ususario_id", length = 19, nullable = true)
    private long segUsusarioId;

    @OneToOne
    @JoinColumn(name = "act_contrato_id")
    private ActContrato actContrato;

    @OneToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public long getSegUsusarioId() {
        return segUsusarioId;
    }

    public void setSegUsusarioId(long segUsusarioId) {
        this.segUsusarioId = segUsusarioId;
    }

    public ActContrato getActContrato() {
        return actContrato;
    }

    public void setActContrato(ActContrato actContrato) {
        this.actContrato = actContrato;
    }

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    @Override
    public String toString() {
        return "actContratoCorte [id=" + id + ", actContrato=" + (actContrato != null ? actContrato : "")
                + ", fecha=" + fecha + ", flgEstado=" + flagEstado + ", segUsusarioId=" + segUsusarioId + "]";
    }

}

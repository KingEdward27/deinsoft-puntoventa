package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "actContratoMov")
@Table(name = "act_contrato_mov")
public class ActContratoMov implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_contrato_mov_id", nullable = false, unique = true)
    private long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = true)
    private LocalDate fecha;

    @ColumnDefault("1")
    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado; 

    @Size(max = 1)
    @Column(name = "flag_tipo", length = 1, nullable = false)
    private String flagTipo; 
    
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

    public String getFlagTipo() {
        return flagTipo;
    }

    public void setFlagTipo(String flagTipo) {
        this.flagTipo = flagTipo;
    }

    @Override
    public String toString() {
        return "actContratoCorte [id=" + id + ", actContrato=" + (actContrato != null ? actContrato : "")
                + ", fecha=" + fecha + ", flgEstado=" + flagEstado + "]";
    }

}

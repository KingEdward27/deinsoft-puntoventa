package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "segPermiso")
@Table(name = "seg_permiso")
public class SegPermiso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_permiso_id", nullable = false, unique = true)
    private long id;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "seg_rol_id")
    private SegRol segRol;

    @OneToOne
    @JoinColumn(name = "seg_menu_id")
    private SegMenu segMenu;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "seg_accion_id")
    private SegAccion segAccion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SegRol getSegRol() {
        return segRol;
    }

    public void setSegRol(SegRol segRol) {
        this.segRol = segRol;
    }

    public SegMenu getSegMenu() {
        return segMenu;
    }

    public void setSegMenu(SegMenu segMenu) {
        this.segMenu = segMenu;
    }

    public SegAccion getSegAccion() {
        return segAccion;
    }

    public void setSegAccion(SegAccion segAccion) {
        this.segAccion = segAccion;
    }

    @Override
    public String toString() {
        return "segPermiso [id=" + id + ", segAccion=" + (segAccion != null ? segAccion : "") + ", segMenu=" + (segMenu != null ? segMenu : "") + ", segRol=" + (segRol != null ? segRol : "") + "]";
    }

}

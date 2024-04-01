package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;
import org.hibernate.annotations.ColumnDefault;

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

    @NotNull
    @OneToOne
    @Valid
    @JoinColumn(name = "seg_menu_id")
    private SegMenu segMenu;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "seg_accion_id")
    private SegAccion segAccion;

    @ColumnDefault("1")
    @Valid
    @Column(name = "perfil_empresa", length = 255, nullable = false)
    private int perfilEmpresa;
    
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
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SegPermiso other = (SegPermiso) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.segMenu, other.segMenu);
    }

    @Override
    public String toString() {
        return "segPermiso [id=" + id + ", segAccion=" + (segAccion != null ? segAccion : "") + ", segMenu=" + (segMenu != null ? segMenu : "") + ", segRol=" + (segRol != null ? segRol : "") + "]";
    }

    public int getPerfilEmpresa() {
        return perfilEmpresa;
    }

    public void setPerfilEmpresa(int perfilEmpresa) {
        this.perfilEmpresa = perfilEmpresa;
    }

}

package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "segMenu")
@Table(name = "seg_menu")
public class SegMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_menu_id", nullable = false, unique = true)
    private long id;

    @Size(max = 200)
    @Column(name = "nombre", length = 200, nullable = true)
    private String nombre;

    @Column(name = "seqorder", length = 10, nullable = false)
    private int seqorder;

    @Size(max = 64)
    @Column(name = "icon", length = 64, nullable = true)
    private String icon;

    @Size(max = 45)
    @Column(name = "path", length = 45, nullable = true)
    private String path;

    @JsonIgnoreProperties(value = "children", allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name="parent_id",referencedColumnName = "seg_menu_id")
    private SegMenu parent;

    @JsonIgnoreProperties(value = "segMenu", allowSetters = true)
    @OneToMany(mappedBy="parent", fetch = FetchType.LAZY)
    @OrderBy("seqorder ASC")
    private Set<SegMenu> children = new HashSet<SegMenu>();
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSeqorder() {
        return seqorder;
    }

    public void setSeqorder(int seqorder) {
        this.seqorder = seqorder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SegMenu getParent() {
        return parent;
    }

    public void setParent(SegMenu parent) {
        this.parent = parent;
    }

    public Set<SegMenu> getChildren() {
        return children;
    }

    public void setChildren(Set<SegMenu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "segMenu [id=" + id + ", segMenu=" + (parent != null ? parent : "") + ", nombre=" + nombre + ", seqorder=" + seqorder + ", icon=" + icon + ", path=" + path + "]";
    }

}

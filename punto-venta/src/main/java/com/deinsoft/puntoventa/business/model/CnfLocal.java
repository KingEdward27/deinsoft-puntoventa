package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.validation.Valid;

@Entity(name = "cnfLocal")
@Table(name = "cnf_local")
public class CnfLocal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_local_id", nullable = false, unique = true)
    private long id;

    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = true)
    private String nombre;

    @Size(max = 100)
    @Column(name = "direccion", length = 100, nullable = true)
    private String direccion;

    @Size(max = 200)
    @Column(name = "impresora_nombre", length = 200, nullable = true)
    private String impresoraNombre;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;

    public CnfLocal() {
    }


    public CnfLocal(long id) {
        this.id = id;
    }


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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImpresoraNombre() {
        return impresoraNombre;
    }

    public void setImpresoraNombre(String impresoraNombre) {
        this.impresoraNombre = impresoraNombre;
    }

    public CnfEmpresa getCnfEmpresa() {
        return cnfEmpresa;
    }

    public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
        this.cnfEmpresa = cnfEmpresa;
    }

    @Override
    public String toString() {
        return "cnfLocal [id=" + id + ", cnfEmpresa=" + (cnfEmpresa != null ? cnfEmpresa : "") + ", nombre=" + nombre + ", direccion=" + direccion + "]";
    }

}

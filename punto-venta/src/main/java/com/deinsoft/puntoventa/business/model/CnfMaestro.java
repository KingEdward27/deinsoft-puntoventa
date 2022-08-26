package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;

@Entity(name = "cnfMaestro")
@Table(name = "cnf_maestro")
public class CnfMaestro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_maestro_id")
    private long id;

    @Size(max = 11)
    @Column(name = "nro_doc", length = 11, nullable = true)
    private String nroDoc;

    @Size(max = 100)
    @Column(name = "nombres", length = 100, nullable = true)
    private String nombres;

    @Size(max = 100)
    @Column(name = "apellido_paterno", length = 100, nullable = true)
    private String apellidoPaterno;

    @Size(max = 100)
    @Column(name = "apellido_materno", length = 100, nullable = true)
    private String apellidoMaterno;

    @Size(max = 100)
    @Column(name = "razon_social", length = 100, nullable = true)
    private String razonSocial;

    @Size(max = 200)
    @Column(name = "direccion", length = 200, nullable = true)
    private String direccion;

    @Size(max = 100)
    @Column(name = "correo", length = 100, nullable = true)
    private String correo;

    @Size(max = 20)
    @Column(name = "telefono", length = 20, nullable = true)
    private String telefono;

    @Column(name = "fecha_registro", nullable = true)
    private LocalDateTime fechaRegistro;

    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado;

    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_tipo_documento_id")
    private CnfTipoDocumento cnfTipoDocumento;

    @OneToOne
    @Valid
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "cnf_distrito_id")
    private CnfDistrito cnfDistrito;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public CnfTipoDocumento getCnfTipoDocumento() {
        return cnfTipoDocumento;
    }

    public void setCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento) {
        this.cnfTipoDocumento = cnfTipoDocumento;
    }

    public CnfEmpresa getCnfEmpresa() {
        return cnfEmpresa;
    }

    public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
        this.cnfEmpresa = cnfEmpresa;
    }

    public CnfDistrito getCnfDistrito() {
        return cnfDistrito;
    }

    public void setCnfDistrito(CnfDistrito cnfDistrito) {
        this.cnfDistrito = cnfDistrito;
    }

    @Override
    public String toString() {
        return "cnfMaestro [id=" + id + ", cnfEmpresa=" + (cnfEmpresa != null ? cnfEmpresa : "") + ", cnfDistrito=" + (cnfDistrito != null ? cnfDistrito : "") + ", cnfTipoDocumento=" + (cnfTipoDocumento != null ? cnfTipoDocumento : "") + ", nroDoc=" + nroDoc + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", razonSocial=" + razonSocial + ", direccion=" + direccion + ", correo=" + correo + ", telefono=" + telefono + ", fechaRegistro=" + fechaRegistro + ", flagEstado=" + flagEstado + "]";
    }

}

package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import javax.validation.Valid;
import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;

@Entity(name = "cnfProducto")
@Table(name = "cnf_producto")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnfProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnf_producto_id", nullable = false, unique = true)
    private long id;

    @Size(max = 15)
    @Column(name = "codigo", length = 15, nullable = true)
    private String codigo;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = true)
    private String nombre;

    @Column(name = "costo", length = 14, nullable = true)
    private BigDecimal costo;
    
    @Column(name = "precio", length = 14, nullable = true)
    private BigDecimal precio;

    @Column(name = "existencia", length = 14, nullable = true)
    private BigDecimal existencia;

    @Column(name = "fecha_registro", nullable = true)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime fechaRegistro;

    @Column(name = "ruta_imagen", length = 900, nullable = true)
    private String rutaImagen;

    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = true)
    private String flagEstado;

    @Size(max = 100)
    @Column(name = "barcode", length = 100, nullable = true)
    private String barcode;

//    @Size(max = 300)
//    @Column(name = "image_path", length = 300, nullable = true)
//    private String imagePath;
    
    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_unidad_medida_id")
    private CnfUnidadMedida cnfUnidadMedida;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_empresa_id")
    private CnfEmpresa cnfEmpresa;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_sub_categoria_id")
    private CnfSubCategoria cnfSubCategoria;

    
    @OneToOne
    @JoinColumn(name = "cnf_marca_id")
    private CnfMarca cnfMarca;

//    @NotNull
//    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_categoria_id")
    private CnfCategoria cnfCategoria;
    
//    @Transient
//    private MultipartFile file;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getExistencia() {
        return existencia;
    }

    public void setExistencia(BigDecimal existencia) {
        this.existencia = existencia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public CnfUnidadMedida getCnfUnidadMedida() {
        return cnfUnidadMedida;
    }

    public void setCnfUnidadMedida(CnfUnidadMedida cnfUnidadMedida) {
        this.cnfUnidadMedida = cnfUnidadMedida;
    }

    public CnfEmpresa getCnfEmpresa() {
        return cnfEmpresa;
    }

    public void setCnfEmpresa(CnfEmpresa cnfEmpresa) {
        this.cnfEmpresa = cnfEmpresa;
    }

    public CnfSubCategoria getCnfSubCategoria() {
        return cnfSubCategoria;
    }

    public void setCnfSubCategoria(CnfSubCategoria cnfSubCategoria) {
        this.cnfSubCategoria = cnfSubCategoria;
    }

    public CnfMarca getCnfMarca() {
        return cnfMarca;
    }

    public void setCnfMarca(CnfMarca cnfMarca) {
        this.cnfMarca = cnfMarca;
    }

    public CnfCategoria getCnfCategoria() {
        return cnfCategoria;
    }

    public void setCnfCategoria(CnfCategoria cnfCategoria) {
        this.cnfCategoria = cnfCategoria;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "cnfProducto [id=" + id + ", codigo=" + codigo + ", cnfEmpresa=" + (cnfEmpresa != null ? cnfEmpresa : "") + ", cnfMarca=" + (cnfMarca != null ? cnfMarca : "") + ", cnfSubCategoria=" + (cnfSubCategoria != null ? cnfSubCategoria : "") + ", cnfUnidadMedida=" + (cnfUnidadMedida != null ? cnfUnidadMedida : "") + ", nombre=" + nombre + ", precio=" + precio + ", existencia=" + existencia + ", fechaRegistro=" + fechaRegistro + ", rutaImagen=" + rutaImagen + ", flagEstado=" + flagEstado + ", barcode=" + barcode + "]";
    }

}

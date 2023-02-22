package com.deinsoft.puntoventa.business.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import java.lang.reflect.Field;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.validation.annotation.Validated;

@Entity(name = "actContrato")
@Table(name = "act_contrato")
public class ActContrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_contrato_id", nullable = false, unique = true)
    private long id;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha", length = 0, nullable = false)
    private LocalDate fecha;

    @NotEmpty
    @Size(max = 4)
    @Column(name = "serie", length = 4, nullable = false)
    private String serie;

    @NotEmpty
    @Size(max = 8)
    @Column(name = "numero", length = 8, nullable = false)
    private String numero;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Size(max = 100)
    @Column(name = "observacion", length = 100, nullable = true)
    private String observacion;

    @NotEmpty
    @Size(max = 1)
    @Column(name = "flag_estado", length = 1, nullable = false)
    private String flagEstado;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_maestro_id")
    private CnfMaestro cnfMaestro;

    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_forma_pago_id")
    private CnfFormaPago cnfFormaPago;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_local_id")
    private CnfLocal cnfLocal;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_tipo_comprobante_id")
    private CnfTipoComprobante cnfTipoComprobante;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "cnf_plan_contrato_id")
    private CnfPlanContrato cnfPlanContrato;

    @OneToOne
    @JoinColumn(name = "seg_usuario_id")
    private SegUsuario segUsuario;
    

    @Column(name = "nro_poste", length = 10)
    private String nroPoste;
    
    @Column(name = "url_map", length = 1000)
    private String urlMap;
    
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

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFlagEstado() {
        return flagEstado;
    }

    public void setFlagEstado(String flagEstado) {
        this.flagEstado = flagEstado;
    }

    public CnfMaestro getCnfMaestro() {
        return cnfMaestro;
    }

    public void setCnfMaestro(CnfMaestro cnfMaestro) {
        this.cnfMaestro = cnfMaestro;
    }

    public CnfFormaPago getCnfFormaPago() {
        return cnfFormaPago;
    }

    public void setCnfFormaPago(CnfFormaPago cnfFormaPago) {
        this.cnfFormaPago = cnfFormaPago;
    }

    public CnfLocal getCnfLocal() {
        return cnfLocal;
    }

    public void setCnfLocal(CnfLocal cnfLocal) {
        this.cnfLocal = cnfLocal;
    }

    public CnfTipoComprobante getCnfTipoComprobante() {
        return cnfTipoComprobante;
    }

    public void setCnfTipoComprobante(CnfTipoComprobante cnfTipoComprobante) {
        this.cnfTipoComprobante = cnfTipoComprobante;
    }

    public CnfPlanContrato getCnfPlanContrato() {
        return cnfPlanContrato;
    }

    public void setCnfPlanContrato(CnfPlanContrato cnfPlanContrato) {
        this.cnfPlanContrato = cnfPlanContrato;
    }

    public SegUsuario getSegUsuario() {
        return segUsuario;
    }

    public void setSegUsuario(SegUsuario segUsuario) {
        this.segUsuario = segUsuario;
    }

    public String getNroPoste() {
        return nroPoste;
    }

    public void setNroPoste(String nroPoste) {
        this.nroPoste = nroPoste;
    }

    public String getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(String urlMap) {
        this.urlMap = urlMap;
    }
    
    public static Map<String, Object> toMap(ActContrato object, String[] visibles) {
        Map<String, Object> map = new HashMap<>();
        try {

            for (Field f : object.getClass().getDeclaredFields()) {
//            System.out.println(f.toString());
//                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getType());
                System.out.println(f.getGenericType() + " " + f.getName() + " " + f.getModifiers());
                if (visibles == null) {
                    if (f.toString().contains("final") || f.toString().contains("list")) {
                        continue;
                    }
                } else {
                    if (f.toString().contains("final") || f.toString().contains("list")
                            || !Arrays.asList(visibles).contains(f.getName())) {
                        continue;
                    }
                }

                map.put(f.getName(), f.get(object));

//                objectBuilder.add(f.getName(), f.get(facturaElectronica).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}

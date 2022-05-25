/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.facturador.client;

import java.lang.reflect.Field;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author EDWARD
 */
public class Detalle {

    private String codigo;
    private String descripcion;
    private String detalle_adicional;
    private String unidad_medida;
    private String cantidad;
    private String precio_unitario;
    private String descuento_porcentaje;
    private String tipo_igv;
    private String afectacion_igv;
    private String monto_referencial_unitario;
    private String cod_tributo_igv;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle_adicional() {
        return detalle_adicional;
    }

    public void setDetalle_adicional(String detalle_adicional) {
        this.detalle_adicional = detalle_adicional;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getDescuento_porcentaje() {
        return descuento_porcentaje;
    }

    public void setDescuento_porcentaje(String descuento_porcentaje) {
        this.descuento_porcentaje = descuento_porcentaje;
    }

    public String getTipo_igv() {
        return tipo_igv;
    }

    public void setTipo_igv(String tipo_igv) {
        this.tipo_igv = tipo_igv;
    }

    public String getAfectacion_igv() {
        return afectacion_igv;
    }

    public void setAfectacion_igv(String afectacion_igv) {
        this.afectacion_igv = afectacion_igv;
    }

    public String getMonto_referencial_unitario() {
        return monto_referencial_unitario;
    }

    public void setMonto_referencial_unitario(String monto_referencial_unitario) {
        this.monto_referencial_unitario = monto_referencial_unitario;
    }

    public String getCod_tributo_igv() {
        return cod_tributo_igv;
    }

    public void setCod_tributo_igv(String cod_tributo_igv) {
        this.cod_tributo_igv = cod_tributo_igv;
    }

    public Detalle() {
    }

    public JsonObject toJson(Detalle detail) {
        JsonObject object = null;
        try {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            for (Field f : detail.getClass().getDeclaredFields()) {
                if (f.get(detail) == null) {
                    continue;
                }
                System.out.println(f.getGenericType() + " " + f.getName() + " = " + f.get(detail));
                objectBuilder.add(f.getName(), f.get(detail).toString());
            }

            object = objectBuilder.build();

        } catch (Exception e) {
            System.out.println("error inesperado: " + e.toString());
        }
        return object;
    }
}

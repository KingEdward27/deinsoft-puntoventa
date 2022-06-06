/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.facturador.client;

import java.lang.reflect.Field;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author erojaalf
 */
public class Tributo {

    private String ide_tributo;

    private String nom_tributo;

    private String cod_tip_tributo;

    private String mto_base_imponible;
    private String mto_tributo;

    public String getIde_tributo() {
        return ide_tributo;
    }

    public void setIde_tributo(String ide_tributo) {
        this.ide_tributo = ide_tributo;
    }

    public String getNom_tributo() {
        return nom_tributo;
    }

    public void setNom_tributo(String nom_tributo) {
        this.nom_tributo = nom_tributo;
    }

    public String getCod_tip_tributo() {
        return cod_tip_tributo;
    }

    public void setCod_tip_tributo(String cod_tip_tributo) {
        this.cod_tip_tributo = cod_tip_tributo;
    }

    public String getMto_base_imponible() {
        return mto_base_imponible;
    }

    public void setMto_base_imponible(String mto_base_imponible) {
        this.mto_base_imponible = mto_base_imponible;
    }

    public String getMto_tributo() {
        return mto_tributo;
    }

    public void setMto_tributo(String mto_tributo) {
        this.mto_tributo = mto_tributo;
    }

    public JsonObject toJson(Tributo detail) {
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

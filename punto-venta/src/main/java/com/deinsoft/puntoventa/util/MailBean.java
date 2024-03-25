/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.util;

import java.util.Map;

/**
 *
 * @author EDWARD-PC
 */
public class MailBean {
    private String asunto;
    private String contenido;
    private String correoElectronicoFrom;
    private String correoElectronicoFromPass;
    private String correoElectronicoTo;
    private String[] adjuntos;
    private Map<String, String> mapInlineImages;
    public MailBean() {

    }

    public MailBean(String asunto, String contenido, String correoElectronicoFrom,
            String correoElectronicoFromPass, String correoElectronicoTo, String[] adjuntos,Map<String, String> mapInlineImages) {
        this.asunto = asunto;
        this.contenido = contenido;
        this.correoElectronicoFrom = correoElectronicoFrom;
        this.correoElectronicoFromPass = correoElectronicoFromPass;
        this.correoElectronicoTo = correoElectronicoTo;
        this.adjuntos = adjuntos;
        this.mapInlineImages = mapInlineImages;
    }
    
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getCorreoElectronicoFrom() {
        return correoElectronicoFrom;
    }

    public void setCorreoElectronicoFrom(String correoElectronicoFrom) {
        this.correoElectronicoFrom = correoElectronicoFrom;
    }

    public String getCorreoElectronicoTo() {
        return correoElectronicoTo;
    }

    public void setCorreoElectronicoTo(String correoElectronicoTo) {
        this.correoElectronicoTo = correoElectronicoTo;
    }

    public String getCorreoElectronicoFromPass() {
        return correoElectronicoFromPass;
    }

    public void setCorreoElectronicoFromPass(String correoElectronicoFromPass) {
        this.correoElectronicoFromPass = correoElectronicoFromPass;
    }

    public String[] getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(String[] adjuntos) {
        this.adjuntos = adjuntos;
    }

    public Map<String, String> getMapInlineImages() {
        return mapInlineImages;
    }

    public void setMapInlineImages(Map<String, String> mapInlineImages) {
        this.mapInlineImages = mapInlineImages;
    }


    @Override
    public String toString() {
        return "MailBean [asunto=" + asunto + ", contenido=<somuchtosee>"
                + ", correoElectronicoFrom=" + correoElectronicoFrom + ", correoElectronicoTo= "+correoElectronicoTo+"]";
    }
}

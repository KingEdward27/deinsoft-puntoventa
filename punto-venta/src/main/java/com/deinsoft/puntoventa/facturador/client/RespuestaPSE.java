/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.facturador.client;

import org.json.JSONObject;

/**
 *
 * @author EDWARD
 */
public class RespuestaPSE {
    private String id;
    private String tipo;
    private String serie;
    private String numero;
    private String total;
    private String totalLetras;
    private String moneda;
    private String codigoQR;
    private String xmlHash;
    private String errMessage;
//    private String errType;
    private String errCode;
    private String pdfRespuesta;
    private String xmlRespuesta;
    private boolean result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalLetras() {
        return totalLetras;
    }

    public void setTotalLetras(String totalLetras) {
        this.totalLetras = totalLetras;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getXmlHash() {
        return xmlHash;
    }

    public void setXmlHash(String xmlHash) {
        this.xmlHash = xmlHash;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

//    public String getErrType() {
//        return errType;
//    }
//
//    public void setErrType(String errType) {
//        this.errType = errType;
//    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getPdfRespuesta() {
        return pdfRespuesta;
    }

    public void setPdfRespuesta(String pdfRespuesta) {
        this.pdfRespuesta = pdfRespuesta;
    }

    public String getXmlRespuesta() {
        return xmlRespuesta;
    }

    public void setXmlRespuesta(String xmlRespuesta) {
        this.xmlRespuesta = xmlRespuesta;
    }
    public RespuestaPSE(String code, String errMessage){
        this.errCode = code;
        this.errMessage = errMessage;
        this.result = false;
    }
    public RespuestaPSE(String jsonString,boolean result) {
        JSONObject object = new JSONObject(jsonString);
        this.result = result;
        if (result) {
            this.id = String.valueOf(object.get("ticketOperacion"));
//            this.tipo = String.valueOf(object.get("tipo"));
//            this.serie = String.valueOf(object.get("serie"));
//            this.numero = String.valueOf(object.get("numero"));
//            this.total = String.valueOf( object.get("total"));
//            this.totalLetras = String.valueOf(object.get("total_letras"));
//            this.moneda = String.valueOf(object.get("moneda"));
//            this.codigoQR = String.valueOf(object.get("codigo_qr"));
            this.xmlHash = String.valueOf(object.get("xmlHash"));
            try {
                this.pdfRespuesta = String.valueOf(object.get("pdfBase64"));
                this.xmlRespuesta = String.valueOf(object.get("xmlBase64"));
            } catch (Exception e) {
                System.out.println("pdf o xml no considerado en respuesta");
            }
            
        }
        else   
        {
            this.errMessage = String.valueOf(object.get("message"));
//            this.errType = String.valueOf(object.get("type"));
            this.errCode = String.valueOf(object.get("code"));
        }
        
        
        
        
    }
            
    
}

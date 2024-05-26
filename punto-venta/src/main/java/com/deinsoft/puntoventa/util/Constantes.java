/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.util;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author EDWARD
 */
public class Constantes {
//    public static final float VALOR_IGV = 0.18f;
    public static final String DESCRIPCION_BOLETA = "BOLETA DE VENTA ELECTRÓNICA";
    public static final String DESCRIPCION_FACTURA = "FACTURA ELECTRÓNICA";
    public static final String TIPO_DOC_BOLETA = "03";
    public static final String TIPO_DOC_FACTURA = "01";
    public static final String TIPO_DOC_TICKET = "00";
//    public static final String ID_TIPO_DOC_FACTURA = "01";
//    public static final String ID_TIPO_DOC_PROFORMA = "00";
    public static final String TIPO_DOC_NOTA_CREDITO = "07";
    public static final String TIPO_DOC_NOTA_DEDITO = "08";
    public static final String TIPO_OPERACION_WS = "0101";
    public static final String MONEDA = "PEN";
//    public static final String NOM_VENDEDOR = "WALTER";
    public static final String INCLUIR_PDF = "false";
    public static final String INCLUIR_XML = "false";
    public static final String DESCRIPCION_UNIDAD = "ZZ";
    public static final String RESOLUCION = "N° 097-2021/SUNAT";
    public static final String PAGINA_WEB = "www.deinsoft-la.com";
    public static final String RUTA_DOC_PC = "E:/Sistema/";
    
    public static DateTimeFormatter YYYYMM_FORMATER = DateTimeFormatter.ofPattern("yyyyMM");
    public static DateTimeFormatter DDMMYYYY_FORMATER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static final String MSG_NO_AUTHORIZED = "No tiene acceso a ver esta página";
    public static final String MSG_NO_EXISTS_ITEM = "Item no existe";
    public static final String COD_ESTADO_ACTIVO = "1";
    public static final String COD_TIPO_COMPROBANTE_CONTRATO = "CNT";
}

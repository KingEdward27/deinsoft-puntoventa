/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import com.deinsoft.puntoventa.framework.model.JsonData;
import java.text.ParseException;
import java.util.Map;

/**
 *
 * @author EDWARD-PC
 */
public interface BusinessService {

    public RespuestaPSE sendApi(String tableName, long id) throws ParseException;

    public byte[] getPDF(long id, int tipo) throws ParseException;

    public Map<String, Object> savePurchase(JsonData jsonData);

    public Map<String, Object> saveSale(JsonData jsonData);

    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception;

    public byte[] print2(String staticResourcesFolder, int tipo, ActComprobante actComprobante, boolean isTicket) throws Exception;

    public byte[] printPago(String staticResourcesFolder, int tipo, ActPago actPago, boolean isTicket) throws Exception;

    public Map<String, Object> searchSunat(String nroDoc) throws Exception;
}

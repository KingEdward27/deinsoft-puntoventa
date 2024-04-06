/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.business.repository.ActComprobanteRepository;
import com.deinsoft.puntoventa.business.repository.ActContratoRepository;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.facturador.client.EnvioPSE;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import com.deinsoft.puntoventa.framework.model.Detail;
import com.deinsoft.puntoventa.framework.model.JsonData;
import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import com.deinsoft.puntoventa.framework.service.TransactionService;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.util.Impresion;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.apache.commons.io.FileUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
/**
 *
 * @author EDWARD-PC
 */
@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    JdbcRepository jdbcRepository;

    @Autowired
    TransactionService transactionService;
    
    @Autowired
    ActComprobanteRepository actComprobanteRepository;
    
    @Autowired
    ActContratoRepository actContratoRepository;

//    public static String urlBase = "http://localhost:8080/api/v1/";
//    public static String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTA5MjAwMTYsImlzcyI6IkRFSU5TT0ZUIiwianRpIjoiREVGQUNULUpXVCIsInN1YiI6IjEwNDE0MzE2NTk1L1BFUkVaIERFTEdBRE8gQkxBTkNBIE5FUkkiLCJudW1Eb2MiOiIxMDQxNDMxNjU5NSIsInJhem9uU29jaWFsIjoiUEVSRVogREVMR0FETyBCTEFOQ0EgTkVSSSIsInVzdWFyaW9Tb2wiOiI0MTQzMTY1OSIsImV4cCI6MTY4MjkyMDAxNn0.1Csg7-tYUYtrpMDHGwMG8K04_aGUXrj8Vbm3qqeSYL6GDxsg-JOOf-VxWHXWeBXGzZ-3C6Br3DB1kJpOH_Ueog";

    @Override
    public RespuestaPSE sendApi(String tableName, long id) throws ParseException {
        RespuestaPSE result = null;
        
        Map<String, Object> mapVenta = jdbcRepository.findById(tableName, id);
        Map<String,Object> local = (Map<String,Object>)mapVenta.get("cnf_local");
        Map<String,Object> empresa = (Map<String,Object>)local.get("cnf_empresa");
        String ruta = Util.getStringValue(empresa,"ruta_pse");
        String token = Util.getStringValue(empresa,"token");
//        if (ruta == null) throw new RuntimeException ("Ruta PSE no configurado");
//        if (token == null) throw new RuntimeException ("token PSE no configurado");
        EnvioPSE envioPSE = new EnvioPSE(ruta,token);
        String jsonBody = envioPSE.paramToJson(mapVenta);
        RespuestaPSE resultEnvioPSE = envioPSE.envioJsonPSE(jsonBody);

//        if (resultEnvioPSE.isResult()) {
        result = resultEnvioPSE;
//        }
        //update result
        Map<String, Object> map = new HashMap<>();

        if (resultEnvioPSE.isResult()) {
            map.put("envio_pse_flag", "1");
            map.put("envio_pse_mensaje", "Recibido correctamente");
            map.put("num_ticket", resultEnvioPSE.getId());
            map.put("codigoqr", resultEnvioPSE.getCodigoQR());
            map.put("xmlhash", resultEnvioPSE.getXmlHash());
        } else {
            map.put("envio_pse_flag", "0");
            map.put("envio_pse_mensaje", resultEnvioPSE.getErrCode() + "-" + resultEnvioPSE.getErrMessage());
        }
        JsonData json = new JsonData();
        json.setTableName(tableName);
        json.setFilters(map);
        json.setId(id);
        jdbcRepository.update(json);
        return result;
    }

    @Override
    public byte[] getPDF(long id, int tipo) throws ParseException {
        byte[] result = null;
        Map<String, Object> mapVenta = jdbcRepository.findById("act_comprobante", id);
        Map<String,Object> local = (Map<String,Object>)mapVenta.get("cnf_local");
        Map<String,Object> empresa = (Map<String,Object>)local.get("cnf_empresa");
        EnvioPSE envioPSE = new EnvioPSE(empresa.get("ruta_pse").toString(), empresa.get("token").toString());
        Map<String, Object> mapResult = null;
        try {
            mapResult = envioPSE.getPDF(id, tipo);
        } catch (Exception ex) {
            Logger.getLogger(BusinessServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mapResult != null) {
            result = Base64.decodeBase64(mapResult.get("pdfBase64").toString());
        }
        //update result
//        Map<String, Object> map = new HashMap<>();
//
//        if (resultEnvioPSE.isResult()) {
//            map.put("envio_pse_flag", "1");
//            map.put("envio_pse_mensaje", "Recibido correctamente");
//            map.put("num_ticket", resultEnvioPSE.getId());
//            map.put("codigo_qr", resultEnvioPSE.getCodigoQR());
//            map.put("xml_hash", resultEnvioPSE.getXmlHash());
//        } else {
//            map.put("envio_pse_flag", "0");
//            map.put("envio_pse_mensaje", resultEnvioPSE.getErrCode() + "-" + resultEnvioPSE.getErrMessage());
//        }
//        JsonData json = new JsonData();
//        json.setTableName(tableName);
//        json.setFilters(map);
//        json.setId(id);
//        jdbcRepository.update(json);
        return result;
    }
    @Override
    @Transactional(rollbackFor = {Exception.class,Throwable.class}, propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> saveSale(JsonData jsonData) {
        Map<String, Object> resultSave = null;
        
        try {
            
            ObjectMapper oMapper = new ObjectMapper();
            jdbcRepository.create(jsonData.getTableName(), jsonData.getFilters());
            String idColumn = jdbcRepository.getColumnPk(jsonData.getTableName());
            int idValue = (int) jdbcRepository.selectMaxValueFromColumn(jsonData.getTableName(), idColumn);
//            Map<String, Object> map = oMapper.convertValue(result, Map.class);
            for (Detail detail : jsonData.getDetails()) {
                for (Map<String, Object> object : detail.getFilters()) {
                    object.put(detail.getRelatedBy(), idValue);
                    jdbcRepository.create(detail.getTableName(), object);
                }
//                if(detail.getUpdateParam() != null){
//                    Object object = jdbcRepository.update(detail.getUpdateParam().getTableName(),
//                        detail.getUpdateParam().getColumns(), detail.getUpdateParam().getCondition());
//                }
            }
            resultSave = jdbcRepository.findById(jsonData.getTableName(), idValue);
            
            if(!resultSave.isEmpty()){
                for (Detail detail : jsonData.getDetails()) {
                    if (detail.getTableName().equalsIgnoreCase("act_comprobante_detalle")) {
                        for (Map<String, Object> object : detail.getFilters()) {
                            float cantidad = Float.valueOf(object.get("cantidad").toString());
                            int productId = Integer.valueOf(object.get("cnf_producto_id").toString());
                            jdbcRepository.update("inv_movimiento_producto", "cantidad = cantidad - " + String.valueOf(cantidad),
                                    "cnf_producto_id = " + String.valueOf(productId));
                        }
                    }

                }
            }
            
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ex.printStackTrace();
        }
        return resultSave;
    }
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Map<String, Object> savePurchase(JsonData jsonData) {
        Map<String, Object> resultSave = null;
        try {
            resultSave = transactionService.saveTransaction(jsonData);

            for (Detail detail : jsonData.getDetails()) {
                if (detail.getTableName().equalsIgnoreCase("act_comprobante_detalle")) {
                    for (Map<String, Object> object : detail.getFilters()) {
                        float cantidad = Float.valueOf(object.get("cantidad").toString());
                        int productId = Integer.valueOf(object.get("cnf_producto_id").toString());
                        jdbcRepository.update("inv_movimiento_producto", "cantidad = cantidad + " + String.valueOf(cantidad),
                                "cnf_producto_id = " + String.valueOf(productId));
                    }
                }

            }
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ex.printStackTrace();
        }
        return resultSave;
    }
    @Override
    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception {
        byte[] result = null;
//        EnvioPSE envioPSE = new EnvioPSE(urlBase + "document/get-pdf", token);
//        Map<String, Object> mapResult = null;
//        try {
//            mapResult = envioPSE.getPDF(id, tipo);
//        } catch (Exception ex) {
//            Logger.getLogger(BusinessServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        if (mapResult != null) {
//            result = Base64.decodeBase64(mapResult.get("pdfBase64").toString());
//        }
        Map<String, Object> mapVenta = jdbcRepository.findById("act_comprobante", id);
        byte[] bytes = print(tipo,mapVenta,false);
//        if (!appConfig.getEnvironment().equals("PRODUCTION")) {
//            FileUtils.writeByteArrayToFile(new File("D:/report.pdf"), bytes);
//        }
        return bytes;
    }
    private byte[] print(int tipo, Map<String, Object> mapVenta,boolean isTicket) throws Exception {
        ByteArrayInputStream stream = Impresion.Imprimir(tipo, mapVenta, isTicket);
        if (stream == null) {
            System.out.println("Ocurrió un error al generar el pdf desde la base de datos");
            return null;
        }
        int n = stream.available();
        byte[] bytes = new byte[n];
        stream.read(bytes, 0, n);
        return bytes;
    }
    @Override
    public byte[] print2(String staticResourcesFolder, int tipo, ActComprobante actComprobante,boolean isTicket) throws Exception {
        ByteArrayInputStream stream = Impresion.Imprimir2(staticResourcesFolder, tipo, actComprobante, isTicket);
        if (stream == null) {
            System.out.println("Ocurrió un error al generar el pdf desde la base de datos");
            return null;
        }
        int n = stream.available();
        byte[] bytes = new byte[n];
        stream.read(bytes, 0, n);
        return bytes;
    }
    @Override
    public byte[] printPago(String staticResourcesFolder, int tipo, ActPago actPago,boolean isTicket) throws Exception {
        ByteArrayInputStream stream = Impresion.ImprimirPago(staticResourcesFolder, tipo, actPago, isTicket);
        if (stream == null) {
            System.out.println("Ocurrió un error al generar el pdf desde la base de datos");
            return null;
        }
        int n = stream.available();
        byte[] bytes = new byte[n];
        stream.read(bytes, 0, n);
        return bytes;
    }
    
    public Map<String,Object> searchSunat (String nroDoc) throws Exception {
        Map<String,Object> result = null;
        Map<String,String> param = new HashMap<>();
        param.put("numero", nroDoc);
        if (nroDoc.length() == 8) {
            result = new Util().simpleGet(HttpMethod.GET, "https://api.apis.net.pe/v1/dni", "", param);
        } else if (nroDoc.length() == 11) {
            result = new Util().simpleGet(HttpMethod.GET, "https://api.apis.net.pe/v1/ruc", "", param);
        }
        
        return result;
    }
    
    @Override
    public void verifyPlan(ActComprobante actComprobante, ActContrato actContrato) throws Exception {
        if (actComprobante != null) {
            int month = actComprobante.getFecha().getMonthValue();
            List<ActComprobante> listVentas = actComprobanteRepository.findByCnfEmpresaIdAndMonth(
                    actComprobante.getCnfLocal().getCnfEmpresa().getId(),month);
            Double sumVentas = listVentas.stream().mapToDouble(o -> o.getTotal().doubleValue()).sum();
            if (actComprobante.getCnfLocal().getCnfEmpresa().getPlan() == 1 
                    && (listVentas.size() >= 10 || sumVentas.compareTo(10000d) >= 0)) {
                throw new Exception("Lo sentimos, su plan actual no le permite generar mas ventas en el mes actual");
            }

            if (actComprobante.getCnfLocal().getCnfEmpresa().getPlan() == 2
                    && (listVentas.size() >= 25 || sumVentas.compareTo(20000d) >= 0)) {
                throw new Exception("Lo sentimos, su plan actual no le permite generar mas ventas en el mes actual");
            }

            if (actComprobante.getCnfLocal().getCnfEmpresa().getPlan() == 3
                    && (listVentas.size() >= 50 || sumVentas.compareTo(100000d) >= 0)) {
                throw new Exception("Lo sentimos, su plan actual no le permite generar mas ventas en el mes actual");
            }
        } else {
            int month = actContrato.getFecha().getMonthValue();
            List<ActContrato> listVentas = actContratoRepository.findByCnfEmpresaIdAndMonth(
                    actContrato.getCnfLocal().getCnfEmpresa().getId(),month);
            //Double sumVentas = listVentas.stream().mapToDouble(o -> o.getTotal().doubleValue()).sum();
            
            if (actContrato.getCnfLocal().getCnfEmpresa().getPlan() == 1 
                    && listVentas.size() >= 10 ) {
                throw new Exception("Lo sentimos, su plan actual no le permite generar mas ventas en el mes actual");
            }

            if (actContrato.getCnfLocal().getCnfEmpresa().getPlan() == 2
                    && (listVentas.size() >= 25)) {
                throw new Exception("Lo sentimos, su plan actual no le permite generar mas ventas en el mes actual");
            }

            if (actContrato.getCnfLocal().getCnfEmpresa().getPlan() == 3
                    && (listVentas.size() >= 50)) {
                throw new Exception("Lo sentimos, su plan actual no le permite generar mas ventas en el mes actual");
            }
        }
        
    }
}

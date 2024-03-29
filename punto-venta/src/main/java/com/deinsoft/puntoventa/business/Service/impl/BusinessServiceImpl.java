/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.Service.impl;

import com.deinsoft.puntoventa.business.Service.BusinessService;
import com.deinsoft.puntoventa.facturador.client.EnvioPSE;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import com.deinsoft.puntoventa.framework.model.JsonData;
import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author EDWARD-PC
 */
@Service
@Transactional
public class BusinessServiceImpl implements  BusinessService{

    @Autowired
    JdbcRepository jdbcRepository;

    public static String urlBase =  "http://localhost:8080/api/v1/";
    public static String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTA5MjAwMTYsImlzcyI6IkRFSU5TT0ZUIiwianRpIjoiREVGQUNULUpXVCIsInN1YiI6IjEwNDE0MzE2NTk1L1BFUkVaIERFTEdBRE8gQkxBTkNBIE5FUkkiLCJudW1Eb2MiOiIxMDQxNDMxNjU5NSIsInJhem9uU29jaWFsIjoiUEVSRVogREVMR0FETyBCTEFOQ0EgTkVSSSIsInVzdWFyaW9Tb2wiOiI0MTQzMTY1OSIsImV4cCI6MTY4MjkyMDAxNn0.1Csg7-tYUYtrpMDHGwMG8K04_aGUXrj8Vbm3qqeSYL6GDxsg-JOOf-VxWHXWeBXGzZ-3C6Br3DB1kJpOH_Ueog"; 
    @Override
    public RespuestaPSE sendApi(String tableName, long id) throws ParseException {
        RespuestaPSE result = null;
        EnvioPSE envioPSE = new EnvioPSE(urlBase + "document/send-document", token);
        Map<String, Object> mapVenta = jdbcRepository.findById(tableName, id);
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
        EnvioPSE envioPSE = new EnvioPSE(urlBase + "document/get-pdf", token);
        Map<String,Object> mapResult = null;
        try {
            mapResult = envioPSE.getPDF(id,tipo);
        } catch (Exception ex) {
            Logger.getLogger(BusinessServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mapResult != null) {
            result = Base64.decodeBase64( mapResult.get("pdfBase64").toString());
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
}

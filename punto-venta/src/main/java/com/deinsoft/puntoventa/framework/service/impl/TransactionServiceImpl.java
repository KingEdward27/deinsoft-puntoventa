/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.framework.service.impl;

import com.deinsoft.puntoventa.facturador.client.EnvioPSE;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import com.deinsoft.puntoventa.framework.model.Detail;
import com.deinsoft.puntoventa.framework.model.JsonData;
import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import com.deinsoft.puntoventa.framework.service.TransactionService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 *
 * @author EDWARD-PC
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    JdbcRepository jdbcRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Map<String, Object> saveTransaction(JsonData jsonData) {
        Map<String, Object> result = new HashMap<>();
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
            result = jdbcRepository.findById(jsonData.getTableName(), idValue);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            ex.printStackTrace();
            //throw ex;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResponseEntity<?> sendToApi(int idValue) throws ParseException {
        EnvioPSE envioPSE = new EnvioPSE("", "");
        Map<String, Object> mapVenta = jdbcRepository.findById("act_comprobante", idValue);
        String jsonBody = envioPSE.paramToJson(mapVenta);
        RespuestaPSE resultEnvioPSE = envioPSE.envioJsonPSE(jsonBody);

        if (!resultEnvioPSE.isResult()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultEnvioPSE);
        }
        //update result
        Map<String, Object> map = new HashMap<>();
        
        if (resultEnvioPSE.isResult()) {
            map.put("envio_pse_flag", "1");
            map.put("envio_pse_mensaje", "Recibido correctamente");
            map.put("nro_respuesta", resultEnvioPSE.getId());
            map.put("codigo_qr", resultEnvioPSE.getCodigoQR());
            map.put("xml_hash", resultEnvioPSE.getXmlHash());
        } else {
            map.put("envio_pse_flag", "0");
            map.put("envio_pse_mensaje", resultEnvioPSE.getErrCode() + "-" + resultEnvioPSE.getErrMessage());
        }
        JsonData json = new JsonData();
        json.setTableName(jsonBody);
        json.setId(new Long(idValue));
        jdbcRepository.update(json);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultEnvioPSE);
    }
}

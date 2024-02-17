/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.framework.model.JsonData;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import com.deinsoft.puntoventa.framework.util.Util;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author EDWARD-PC
 */
@RestController
@RequestMapping("/api/business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @Autowired
    JdbcRepository jdbcRepository;
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping(value = "/sendapi")
    public ResponseEntity<?> sendApi(String tableName, long id) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessService.sendApi(tableName, id));
    }

    @PostMapping(value = "/getpdf")
    public ResponseEntity<?> getPdf(@RequestBody UpdateParam param) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.APPLICATION_PDF;
        Map<String, Object> map = param.getMap();
        String id = map.get("id").toString();
        int tipo = Integer.parseInt(map.get("tipo").toString());
        byte[] data = businessService.getPDF(new Long(id), tipo);
        if(data != null){
            String type = "pdf";
            ByteArrayInputStream stream = new ByteArrayInputStream(data);
            if (type.equals("pdf")) {
                headers.add("Content-Disposition", "inline; filename=pdf.pdf");
                mediaType = MediaType.APPLICATION_PDF;
            } else if (type.equals("excel")) {
                headers.add("Content-Disposition", "attachment; filename=excel.xlsx");
                mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            }
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body(new InputStreamResource(stream));
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body("Ocurrió un error comunicandose con el api facturador");
        
    }
    @PostMapping(value = "/getpdflocal")
    public ResponseEntity<?> getPdfLocal(@RequestBody UpdateParam param) throws ParseException, Exception {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.APPLICATION_PDF;
        Map<String, Object> map = param.getMap();
        String id = map.get("id").toString();
        int tipo = Integer.parseInt(map.get("tipo").toString());
        byte[] data = businessService.getPDFLocal(new Long(id), tipo);
        if(data != null){
            String type = "pdf";
            ByteArrayInputStream stream = new ByteArrayInputStream(data);
            if (type.equals("pdf")) {
                headers.add("Content-Disposition", "inline; filename=pdf.pdf");
                mediaType = MediaType.APPLICATION_PDF;
            } else if (type.equals("excel")) {
                headers.add("Content-Disposition", "attachment; filename=excel.xlsx");
                mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            }
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body(new InputStreamResource(stream));
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body("Ocurrió un error comunicandose con el api facturador");
        
    }
    @PostMapping(value = "/save-purchase")
    public ResponseEntity<?> savePurchase(@RequestBody JsonData jsonData, HttpServletRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessService.savePurchase(jsonData));
    }
    @PostMapping(value = "/save-sale")
    public ResponseEntity<?> saveSale(@RequestBody JsonData jsonData, HttpServletRequest request) throws Exception {
        Object object = jdbcRepository.validate(jsonData);

        if (object != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(object);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(businessService.saveSale(jsonData));
    }
    
    
}

package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.CnfPaqueteProducto;
import com.deinsoft.puntoventa.business.service.CnfPaqueteProductoService;
import com.deinsoft.puntoventa.business.service.StorageService;
import com.deinsoft.puntoventa.config.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/business/cnf-paquete-producto")
public class CnfPaqueteProductoController extends CommonController<CnfPaqueteProducto, UUID, CnfPaqueteProductoService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfPaqueteProductoController.class);

    @Autowired
    CnfPaqueteProductoService cnfPaqueteProductoService;


    @GetMapping(value = "/get-cnf-paquete-producto")
    public ResponseEntity<?> getCnfPaqueteProducto(@Param("id") String id) throws Exception {
        logger.info("getCnfPaqueteProducto received: " + id);
        
        try {
            CnfPaqueteProducto cnfPaqueteProducto = cnfPaqueteProductoService.getCnfPaqueteProducto(id);
            return ResponseEntity.status(HttpStatus.OK).body(cnfPaqueteProducto);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
        
    }

    @PostMapping(value = "/save-cnf-paquete-producto", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveCnfPaqueteProducto(
            @RequestPart("cnfPaqueteProducto") @Valid String cnfPaqueteProductoDTO, 
            @RequestPart(name = "file", required = false) MultipartFile file, BindingResult result) throws JsonProcessingException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        CnfPaqueteProducto cnfPaqueteProducto = objectMapper.readValue(cnfPaqueteProductoDTO, CnfPaqueteProducto.class);
        if (result.hasErrors()) {
            return this.validar(result);
        }
        CnfPaqueteProducto cnfPaqueteProductoResult = cnfPaqueteProductoService.saveCnfPaqueteProducto(cnfPaqueteProducto,file);
        return ResponseEntity.status(HttpStatus.CREATED).body(cnfPaqueteProductoResult);
//        return super.crear(cnfPaqueteProducto, result);
    }

    @GetMapping(value = "/get-all-cnf-paquete-producto-combo")
    public List<CnfPaqueteProducto> getAllCnfPaqueteProducto() {
        List<CnfPaqueteProducto> cnfPaqueteProductoList = cnfPaqueteProductoService.getAllCnfPaqueteProducto();
        return cnfPaqueteProductoList;
    }

    @DeleteMapping("/delete-cnf-paquete-producto")
    public ResponseEntity<?> delete(@Param("id") String id) {
        cnfPaqueteProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

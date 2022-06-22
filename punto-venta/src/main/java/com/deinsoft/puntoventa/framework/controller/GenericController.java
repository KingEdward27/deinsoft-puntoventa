package com.deinsoft.puntoventa.framework.controller;

import com.deinsoft.puntoventa.framework.model.Detail;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deinsoft.puntoventa.framework.model.JsonData;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import com.deinsoft.puntoventa.framework.repository.JdbcRepository;
import com.deinsoft.puntoventa.framework.service.TransactionService;
import com.deinsoft.puntoventa.framework.util.ExportUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/framework")
public class GenericController {

    private static final Logger logger = LoggerFactory.getLogger(GenericController.class);

    @Autowired
    JdbcRepository jdbcRepository;

    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/select-by-properties")
    public ResponseEntity<?> selectByProperties(@RequestBody JsonData jsonData, HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("selectByTablename received: " + jsonData.toString());
        logger.info("listColumns received: " + jsonData.getColumnsList());
        logger.info("foreigns received: " + jsonData.getForeignTables());
        List<Object[]> selectedColumns = jdbcRepository.selectColumns(jsonData);
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedColumns);
    }

    @PostMapping(value = "/select-by-tablename-with-filters")
    public ResponseEntity<?> selectByTablenameWithFilters(@RequestBody JsonData jsonData, HttpServletRequest request) {
        logger.info("selectByTablenameWithFilters received: " + jsonData.toString());
        List<Object[]> selectedColumns = jdbcRepository.selectColumns(jsonData);
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedColumns);
    }

    @GetMapping(value = "/select-combos-by-tablename")
    public ResponseEntity<?> selectByTablename(String tableName, String descriptionColumns, HttpServletRequest request) {
        logger.info("selectByTablename received: " + tableName);
        List<Object[]> selectedColumns = jdbcRepository.getListForCombos(tableName, descriptionColumns);
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedColumns);
    }

    @PostMapping(value = "/select-combos-by-properties")
    public ResponseEntity<?> selectByProperties2(@RequestBody JsonData jsonData, HttpServletRequest request) {
        logger.info("selectByProperties2 received: " + jsonData.toString());
        List<Object[]> selectedColumns = jdbcRepository.getListForCombosWithFilters(jsonData);
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedColumns);
    }

    @PostMapping(value = "/select-by-id")
    public ResponseEntity<?> selectById(@RequestBody JsonData jsonData, HttpServletRequest request) {
        logger.info("selectById received: " + jsonData.toString());
        Map<String, Object> object = jdbcRepository.selectData(jsonData);
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> create(@RequestBody JsonData jsonData, HttpServletRequest request) throws Exception {
        logger.info("selectByTablename received: " + jsonData.toString());
        logger.info("listColumns received: " + jsonData.getFilters());
        logger.info("getFilters received: " + jsonData.getFilters().entrySet());
        Object object = jdbcRepository.validate(jsonData);

        if (object != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(object);
        }
        if (jsonData.getId() == 0) {
            object = jdbcRepository.create(jsonData);
        } else {
            object = jdbcRepository.update(jsonData);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }
    @PostMapping(value = "/save-user")
    public ResponseEntity<?> createUser(@RequestBody JsonData jsonData, HttpServletRequest request) throws Exception {
        logger.info("selectByTablename received: " + jsonData.toString());
        logger.info("listColumns received: " + jsonData.getFilters());
        logger.info("getFilters received: " + jsonData.getFilters().entrySet());
        Object object = jdbcRepository.validate(jsonData);

        if (object != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(object);
        }
        if (jsonData.getId() == 0) {
            object = jdbcRepository.create(jsonData);
        } else {
            object = jdbcRepository.update(jsonData);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }
    @PostMapping(value = "/save-transaction")
    public ResponseEntity<?> createTransactional(@RequestBody JsonData jsonData, HttpServletRequest request) throws Exception {
        logger.info("selectByTablename received: " + jsonData.toString());
        logger.info("listColumns received: " + jsonData.getFilters());
        logger.info("getFilters received: " + jsonData.getFilters().entrySet());
        Object object = jdbcRepository.validate(jsonData);

        if (object != null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(object);
        }
//        for (Detail detail : jsonData.getDetails()) {
//            object = jdbcRepository.validate(jsonData);
//            if (object != null) {
//                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(object);
//            }
//        }
        
        if (jsonData.getId() == 0) {
            object = transactionService.saveTransaction(jsonData);
        } else {
            object = jdbcRepository.update(jsonData);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody UpdateParam updateParam) throws ParseException{
        Object object = jdbcRepository.update(updateParam.getTableName(),
                updateParam.getColumns(), updateParam.getCondition());
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@Param("tableName") String tableName, @Param("id") Long id, HttpServletRequest request) throws Exception {

        Object object = jdbcRepository.delete(tableName, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    @GetMapping(value = "/select-columns")
    public ResponseEntity<?> selectColumns(String tableName, String descriptionColumns, String condition, HttpServletRequest request) {
        logger.info("selectByTablename received: " + tableName);
        List<Object[]> selectedColumns = jdbcRepository.selectColumns(tableName, descriptionColumns, condition);
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedColumns);
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<?> getById(String tableName, Long id, HttpServletRequest request) throws ParseException {
        logger.info("selectByTablename received: " + tableName);
        Map<String, Object> selectedColumns = jdbcRepository.findById(tableName, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(selectedColumns);
    }
    @PostMapping(value = "/export/excel")
    //@RequestMapping(value = {"/factura/export/excel"}, method = RequestMethod.GET)
    public String exportToExcel(@RequestBody JsonData jsonData, HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException {
        
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=comprobantes_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        //List<FacturaElectronica> list = verDatos(null, usuario);
//        if(list == null){
//            return "/login";
//        }
        //String[] arrayVisibleObjects = {"tipo","serie","numero","fechaEmision","clienteNombre","totalValorVenta","indSituacion"};
        //String[] cabecera = {"Tipo","Serie","N. Documento","Fecha EmisiÓn","Cliente","Total","Situacion"};
        //List<Map<String,Object>> list2= new ArrayList<>();
//        for (FacturaElectronica facturaElectronica : list) {
//            list2.add(facturaElectronica.toMap(facturaElectronica,arrayVisibleObjects));
//        }
        List<Map<String,Object>> selectedColumns = jdbcRepository.selectColumnsToExport(jsonData);
        ExportUtil excelExporter = new ExportUtil(selectedColumns,jsonData.getHeaders(),null);
         
        excelExporter.export(response);
        return null;
    }  
}

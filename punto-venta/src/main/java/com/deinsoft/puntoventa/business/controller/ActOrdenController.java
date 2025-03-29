package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.GeneratedFile;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.dto.ReporteContableDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.ActOrden;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.service.ActOrdenService;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import com.deinsoft.puntoventa.framework.model.JsonData;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import com.deinsoft.puntoventa.framework.util.ExportUtil;
import com.deinsoft.puntoventa.framework.util.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/business/act-orden")
public class ActOrdenController extends CommonController<ActOrden, Long, ActOrdenService> {

    private static final Logger LOG = LoggerFactory.getLogger(ActOrdenController.class);

    @Autowired
    ActOrdenService actOrdenService;
    
    @Autowired
    AuthenticationHelper auth;
    
    @Autowired
    BusinessService businessService;
    
    @GetMapping(value = "/get-all-act-comprobante")
    public List<ActOrden> getAllActOrden(ActOrden actOrden) {
        LOG.info("getAllActOrden received: " + actOrden.toString());
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrden(actOrden);
        return actOrdenList;
    }

    @GetMapping(value = "/get-act-comprobante")
    public ResponseEntity<?> getActOrden(@Param("id") Long id) {
        LOG.info("getActOrden received: " + id);
        try {
            ActOrden actOrden = actOrdenService.getActOrden(id);
            return ResponseEntity.status(HttpStatus.OK).body(actOrden);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
        
    }

    @PostMapping(value = "/save-act-comprobante")
    public ResponseEntity<?> saveActOrden
        (@Valid @RequestBody ActOrden actOrden, BindingResult result, HttpServletRequest request) throws  Exception{
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Map<String, Object> errores = new HashMap<>();
        if(actOrden.getCnfMaestro().getId() == 0){
            errores.put("cnfMaestro", " Debe seleccionar al cliente");
        }
        if(actOrden.getCnfFormaPago().getId() == 0){
            errores.put("cnfFormaPago", " Debe seleccionar la forma de pago");
        }
        if(actOrden.getCnfMoneda().getId() == 0){
            errores.put("cnfMoneda", " Debe seleccionar la moneda");
        }
        if(actOrden.getCnfLocal().getId() == 0){
            errores.put("cnfLocal", " Debe seleccionar el Local");
        }
        if(!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        SegUsuario segUsuario = auth.getLoggedUserdata();
        actOrden.setSegUsuario(segUsuario);
        ActOrden actOrdenResult = actOrdenService.saveActOrden(actOrden);
        //send fe
//        if (actOrdenResult != null) {
//            try {
//                businessService.sendApi("act_comprobante", actOrdenResult.getId());
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(actOrdenResult);
    }

    @PostMapping(value = "/save-act-comprobante-compra")
    public ResponseEntity<?> saveActOrdenCompra
        (@Valid @RequestBody ActOrden actOrden, BindingResult result, HttpServletRequest request) throws  Exception{
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Map<String, Object> errores = new HashMap<>();
        if(actOrden.getCnfMaestro().getId() == 0){
            errores.put("cnfMaestro", " Debe seleccionar al proveedor");
        }
        if(actOrden.getCnfFormaPago().getId() == 0){
            errores.put("cnfFormaPago", " Debe seleccionar la forma de pago");
        }
        if(actOrden.getCnfMoneda().getId() == 0){
            errores.put("cnfMoneda", " Debe seleccionar la moneda");
        }
        if(actOrden.getCnfLocal().getId() == 0){
            errores.put("cnfLocal", " Debe seleccionar el Local");
        }
        if(!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        SegUsuario segUsuario = auth.getLoggedUserdata();
        actOrden.setSegUsuario(segUsuario);
        ActOrden actOrdenResult = actOrdenService.saveActOrdenCompra(actOrden);
        
        
        return ResponseEntity.status(HttpStatus.CREATED).body(actOrdenResult);
    }
    @GetMapping(value = "/get-all-act-comprobante-combo")
    public List<ActOrden> getAllActOrden() {
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrden();
        return actOrdenList;
    }

    @DeleteMapping("/delete-act-comprobante")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actOrdenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-maestro")
    public List<ActOrden> getAllActOrdenByCnfMaestro(@Param("id") Long id) {
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrdenByCnfMaestro(id);
        return actOrdenList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-forma-pago")
    public List<ActOrden> getAllActOrdenByCnfFormaPago(@Param("id") Long id) {
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrdenByCnfFormaPago(id);
        return actOrdenList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-moneda")
    public List<ActOrden> getAllActOrdenByCnfMoneda(@Param("id") Long id) {
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrdenByCnfMoneda(id);
        return actOrdenList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-local")
    public List<ActOrden> getAllActOrdenByCnfLocal(@Param("id") Long id) {
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrdenByCnfLocal(id);
        return actOrdenList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-tipo-comprobante")
    public List<ActOrden> getAllActOrdenByCnfTipoComprobante(@Param("id") Long id) {
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrdenByCnfTipoComprobante(id);
        return actOrdenList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-inv-almacen")
    public List<ActOrden> getAllActOrdenByInvAlmacen(@Param("id") Long id) {
        List<ActOrden> actOrdenList = actOrdenService.getAllActOrdenByInvAlmacen(id);
        return actOrdenList;
    }
    @PostMapping(value = "/get-report-act-comprobante")
    public List<ActOrden> getReportActOrden(@RequestBody ParamBean paramBean) {
        LOG.info("getAllActOrden received: " + paramBean.toString());
        List<ActOrden> actOrdenList = actOrdenService.getReportActOrden(paramBean);
        return actOrdenList;
    }
//    @GetMapping(value = "/sendapi")
//    public ResponseEntity<?> sendApi(long id) throws ParseException {
//        RespuestaPSE resp = actOrdenService.sendApi(id);
//        if (resp.isResult()) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
//        } else {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp.getErrMessage());
//        }
//        
//    }
    
    @PostMapping(value = "/export/excel")
    //@RequestMapping(value = {"/factura/export/excel"}, method = RequestMethod.GET)
    public String exportToExcel(@RequestBody ParamBean paramBean, HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException {
        
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=comprobantes_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
//        List<Map<String,Object>> selectedColumns = jdbcRepository.selectColumnsToExport(jsonData);
        List<ActOrden> actOrdenList = actOrdenService.getReportActOrden(paramBean);
        String[] arrayVisibleObjects = {"tipo","serie","numero","fechaEmision","clienteNombre","totalValorVenta","indSituacion"};
        String[] cabecera = {"Tipo","Serie","N. Documento","Fecha EmisiÓn","Cliente","Total","Situacion"};
        ExportUtil excelExporter = null;
        try {
            List<Map<String, Object>> divMap = actOrdenList.stream()
            .map( data -> data.toMap(data, null))
            .collect(Collectors.toList());
            
            excelExporter = new ExportUtil(divMap,cabecera,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         
        excelExporter.export(response);
        return null;
    }
    @PostMapping(value = "/invalidate-act-comprobante")
    public ResponseEntity<?> cambiarEstado(@Param("id") Long id, HttpServletRequest request) throws  Exception{
        
//        if(!errores.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
//        }
        actOrdenService.invalidate(id);
        
        
        return ResponseEntity.noContent().build();
    }
//    @PostMapping(value = "/validate-act-comprobante")
//    public ResponseEntity<?> validate(@Param("id") Long id, HttpServletRequest request) throws  Exception{
//        
//        actOrdenService.validate(id);
//        return ResponseEntity.noContent().build();
//    }
    @PostMapping(value = "/getpdflocal")
    public ResponseEntity<?> getPdfLocal(@RequestBody UpdateParam param) throws ParseException, Exception {
        HttpHeaders headers = new HttpHeaders(); 
        MediaType mediaType = MediaType.APPLICATION_PDF;
        Map<String, Object> map = param.getMap();
        String id = map.get("id").toString();
        int tipo = Integer.parseInt(map.get("tipo").toString());
        byte[] data = actOrdenService.getPDFLocal(new Long(id), tipo);
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
    @PostMapping(value = "/generateSireTxt")
    public ResponseEntity<?> generateTxt(@RequestBody ParamBean paramBean, HttpServletResponse response) throws Exception {
        GeneratedFile data = null;
        try {
            data = actOrdenService.generateSireTxt(paramBean);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        ByteArrayInputStream stream = new ByteArrayInputStream(data.getData());
        HttpHeaders httpHeaders = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
          .filename("Filename")
          .build();
        httpHeaders.setContentDisposition(contentDisposition);
        httpHeaders.setContentDisposition(ContentDisposition.builder("attachment").filename(data.getFileName()).build());
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(new InputStreamResource(stream));
    }
    
    @GetMapping(value = "/get-list-contable")
    public List<ReporteContableDto> getListaReporteContable(@Param("cnfLocalId") Long cnfLocalId) {
        List<ReporteContableDto> listReporteContableDto = actOrdenService.getListaReporteContable(cnfLocalId);
        return listReporteContableDto;
    }
    
    @PostMapping(value = "/get-dashboard-act-comprobante")
    public ResponseEntity<?> getDashboardActContrato(@RequestBody ParamBean paramBean) {
        Map<String,Object> actDashboard = actOrdenService.getDashboardActOrdens(paramBean);
        return ResponseEntity.status(HttpStatus.OK).body(actDashboard);
    }
    
}

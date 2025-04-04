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

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.service.ActComprobanteService;
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
@RequestMapping("/api/business/act-comprobante")
public class ActComprobanteController extends CommonController<ActComprobante, Long, ActComprobanteService> {

    private static final Logger LOG = LoggerFactory.getLogger(ActComprobanteController.class);

    @Autowired
    ActComprobanteService actComprobanteService;
    
    @Autowired
    AuthenticationHelper auth;
    
    @Autowired
    BusinessService businessService;
    
    @GetMapping(value = "/get-all-act-comprobante")
    public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante) {
        LOG.info("getAllActComprobante received: " + actComprobante.toString());
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobante(actComprobante);
        return actComprobanteList;
    }

    @GetMapping(value = "/get-act-comprobante")
    public ResponseEntity<?> getActComprobante(@Param("id") Long id) {
        LOG.info("getActComprobante received: " + id);
        try {
            ActComprobante actComprobante = actComprobanteService.getActComprobante(id);
            return ResponseEntity.status(HttpStatus.OK).body(actComprobante);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
        
    }

    @PostMapping(value = "/save-act-comprobante")
    public ResponseEntity<?> saveActComprobante
        (@Valid @RequestBody ActComprobante actComprobante, BindingResult result, HttpServletRequest request) throws  Exception{
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Map<String, Object> errores = new HashMap<>();
        if(actComprobante.getCnfMaestro().getId() == 0){
            errores.put("cnfMaestro", " Debe seleccionar al cliente");
        }
        if(actComprobante.getCnfFormaPago().getId() == 0){
            errores.put("cnfFormaPago", " Debe seleccionar la forma de pago");
        }
        if(actComprobante.getCnfMoneda().getId() == 0){
            errores.put("cnfMoneda", " Debe seleccionar la moneda");
        }
        if(actComprobante.getCnfLocal().getId() == 0){
            errores.put("cnfLocal", " Debe seleccionar el Local");
        }
        if(!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        SegUsuario segUsuario = auth.getLoggedUserdata();
        actComprobante.setSegUsuario(segUsuario);
        ActComprobante actComprobanteResult = actComprobanteService.saveActComprobante(actComprobante);
        //send fe
//        if (actComprobanteResult != null) {
//            try {
//                businessService.sendApi("act_comprobante", actComprobanteResult.getId());
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(actComprobanteResult);
    }

    @PostMapping(value = "/pre-save-act-comprobante")
    public ResponseEntity<?> preSaveActComprobante
        (@Valid @RequestBody ActComprobante actComprobante, BindingResult result) throws  Exception{
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Map<String, Object> errores = new HashMap<>();
        if(actComprobante.getCnfMaestro().getId() == 0){
            errores.put("cnfMaestro", " Debe seleccionar al proveedor");
        }
        if(actComprobante.getCnfFormaPago().getId() == 0){
            errores.put("cnfFormaPago", " Debe seleccionar la forma de pago");
        }
        if(actComprobante.getCnfMoneda().getId() == 0){
            errores.put("cnfMoneda", " Debe seleccionar la moneda");
        }
        if(actComprobante.getCnfLocal().getId() == 0){
            errores.put("cnfLocal", " Debe seleccionar el Local");
        }
        if(!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }



    @GetMapping(value = "/get-all-act-comprobante-combo")
    public List<ActComprobante> getAllActComprobante() {
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobante();
        return actComprobanteList;
    }

    @DeleteMapping("/delete-act-comprobante")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actComprobanteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-maestro")
    public List<ActComprobante> getAllActComprobanteByCnfMaestro(@Param("id") Long id) {
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfMaestro(id);
        return actComprobanteList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-forma-pago")
    public List<ActComprobante> getAllActComprobanteByCnfFormaPago(@Param("id") Long id) {
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfFormaPago(id);
        return actComprobanteList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-moneda")
    public List<ActComprobante> getAllActComprobanteByCnfMoneda(@Param("id") Long id) {
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfMoneda(id);
        return actComprobanteList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-local")
    public List<ActComprobante> getAllActComprobanteByCnfLocal(@Param("id") Long id) {
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfLocal(id);
        return actComprobanteList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-cnf-tipo-comprobante")
    public List<ActComprobante> getAllActComprobanteByCnfTipoComprobante(@Param("id") Long id) {
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByCnfTipoComprobante(id);
        return actComprobanteList;
    }

    @GetMapping(value = "/get-all-act-comprobante-by-inv-almacen")
    public List<ActComprobante> getAllActComprobanteByInvAlmacen(@Param("id") Long id) {
        List<ActComprobante> actComprobanteList = actComprobanteService.getAllActComprobanteByInvAlmacen(id);
        return actComprobanteList;
    }
    @PostMapping(value = "/get-report-act-comprobante")
    public List<ActComprobante> getReportActComprobante(@RequestBody ParamBean paramBean) {
        LOG.info("getAllActComprobante received: " + paramBean.toString());
        List<ActComprobante> actComprobanteList = actComprobanteService.getReportActComprobante(paramBean);
        return actComprobanteList;
    }
    @GetMapping(value = "/sendapi")
    public ResponseEntity<?> sendApi(long id) throws ParseException {
        RespuestaPSE resp = actComprobanteService.sendApi(id);
        if (resp.isResult()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp.getErrMessage());
        }
        
    }
    
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
        List<ActComprobante> actComprobanteList = actComprobanteService.getReportActComprobante(paramBean);
        String[] arrayVisibleObjects = {"tipo","serie","numero","fechaEmision","clienteNombre","totalValorVenta","indSituacion"};
        String[] cabecera = {"Tipo","Serie","N. Documento","Fecha EmisiÓn","Cliente","Total","Situacion"};
        ExportUtil excelExporter = null;
        try {
            List<Map<String, Object>> divMap = actComprobanteList.stream()
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
        actComprobanteService.invalidate(id);
        
        
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/validate-act-comprobante")
    public ResponseEntity<?> validate(@Param("id") Long id, HttpServletRequest request) throws  Exception{
        
        actComprobanteService.validate(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/getpdflocal")
    public ResponseEntity<?> getPdfLocal(@RequestBody UpdateParam param) throws ParseException, Exception {
        HttpHeaders headers = new HttpHeaders(); 
        MediaType mediaType = MediaType.APPLICATION_PDF;
        Map<String, Object> map = param.getMap();
        String id = map.get("id").toString();
        int tipo = Integer.parseInt(map.get("tipo").toString());
        byte[] data = actComprobanteService.getPDFLocal(new Long(id), tipo);
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
            data = actComprobanteService.generateSireTxt(paramBean);
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
    public List<ReporteContableDto> getListaReporteContable(@RequestParam("cnfLocalId") Long cnfLocalId) {
        List<ReporteContableDto> listReporteContableDto = actComprobanteService.getListaReporteContable(cnfLocalId);
        return listReporteContableDto;
    }
    
    @PostMapping(value = "/get-dashboard-act-comprobante")
    public ResponseEntity<?> getDashboardActContrato(@RequestBody ParamBean paramBean) {
        Map<String,Object> actDashboard = actComprobanteService.getDashboardActComprobantes(paramBean);
        return ResponseEntity.status(HttpStatus.OK).body(actDashboard);
    }
    
    @GetMapping(value = "/get-act-comprobante-by-cp")
    public ResponseEntity<?> getActComprobanteByCp(@RequestParam("cnfLocalId") Long id,
            @RequestParam("serie") String serie,
            @RequestParam("numero") String numero) {
        
        LOG.info("getActComprobante received: " + id);
        try {
            ActComprobante actComprobante = actComprobanteService.getByCnfEmpresaIdAndNumberCp(id,serie,numero);
            return ResponseEntity.status(HttpStatus.OK).body(actComprobante);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
        
    }
}

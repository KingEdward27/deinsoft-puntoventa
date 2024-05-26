package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.bean.UploadResponse;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.service.ActContratoService;
import com.deinsoft.puntoventa.business.service.StorageService;
import com.deinsoft.puntoventa.config.AppConfig;
import com.deinsoft.puntoventa.framework.util.ExportUtil;
import com.deinsoft.puntoventa.util.mail.CredentialResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/business/act-contrato")
public class ActContratoController extends CommonController<ActContrato, ActContratoService> {

    private static final Logger logger = LoggerFactory.getLogger(ActContratoController.class);

    @Autowired
    ActContratoService actContratoService;

    @Autowired
    AppConfig appConfig;
    
    
    @Autowired
    StorageService storageService;
    
    @GetMapping(value = "/get-all-act-contrato")
    public List<ActContrato> getAllActContrato(ActContrato actContrato) {
        logger.info("getAllActContrato received: " + actContrato.toString());
        List<ActContrato> actContratoList = actContratoService.getAllActContrato(actContrato);
        return actContratoList;
    }

    @GetMapping(value = "/get-act-contrato")
    public ActContrato getActContrato(@Param("id") Long id) {
        logger.info("getActContrato received: " + id);
        ActContrato actContrato = actContratoService.getActContrato(id);
        return actContrato;
    }

    @PostMapping(value = "/save-act-contrato")
    public ResponseEntity<?> saveActContrato(@Valid @RequestBody ActContrato actContrato, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        ActContrato actContratoResult = actContratoService.saveActContrato(actContrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(actContratoResult);
    }

    @GetMapping(value = "/get-all-act-contrato-combo")
    public List<ActContrato> getAllActContrato() {
        List<ActContrato> actContratoList = actContratoService.getAllActContrato();
        return actContratoList;
    }

    @DeleteMapping("/delete-act-contrato")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actContratoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-act-contrato-by-cnf-maestro")
    public List<ActContrato> getAllActContratoByCnfMaestro(@Param("id") Long id) {
        List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfMaestro(id);
        return actContratoList;
    }

    @GetMapping(value = "/get-all-act-contrato-by-cnf-local")
    public List<ActContrato> getAllActContratoByCnfLocal(@Param("id") Long id) {
        List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfLocal(id);
        return actContratoList;
    }

    @GetMapping(value = "/get-all-act-contrato-by-cnf-tipo-comprobante")
    public List<ActContrato> getAllActContratoByCnfTipoComprobante(@Param("id") Long id) {
        List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfTipoComprobante(id);
        return actContratoList;
    }

    @GetMapping(value = "/get-all-act-contrato-by-cnf-forma-pago")
    public List<ActContrato> getAllActContratoByCnfFormaPago(@Param("id") Long id) {
        List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfFormaPago(id);
        return actContratoList;
    }

    @GetMapping(value = "/get-all-act-contrato-by-cnf-plan-contrato")
    public List<ActContrato> getAllActContratoByCnfPlanContrato(@Param("id") Long id) {
        List<ActContrato> actContratoList = actContratoService.getAllActContratoByCnfPlanContrato(id);
        return actContratoList;
    }
    @PostMapping(value = "/get-report-act-contrato")
    public List<ActContrato> getReportActContrato(@RequestBody ParamBean paramBean) {
        logger.info("getReportActContrato received: " + paramBean.toString());
        List<ActContrato> actCajaOperacionList = actContratoService.getReportActContratos(paramBean);
        return actCajaOperacionList; 
    }
    @GetMapping(value = "/get-dashboard-act-contrato")
    public ResponseEntity<?> getDashboardActContrato(@Param("id") Long id) {
        Map<String,Object> actDashboard = actContratoService.getDashboardActContratos(id);
        return ResponseEntity.status(HttpStatus.OK).body(actDashboard);
    }
    
    @GetMapping(value = "/get-path")
    public String getPathPreResources(@Param("fileName") String fileName, @Param("fileName") long fileSize) {
        String path = storageService.getPath(Paths.get(appConfig.getFileSystemBasePath()+"/temp"), 
                Paths.get(appConfig.getFolderResources()), fileName, fileSize);
        return path;
    }
    
    @PostMapping(value = "/export/excel")
    public String exportToExcel(HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException {
        
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=comprobantes_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        String[] cabecera = {"Apellido Paterno","Apellido materno","Nombres","Razón Social","Tipo de documento (1= DNI,6=RUC)","Número de documento",
            "fecha de instalación (DD/MM/YYYY)","Plan","Zona","Dirección","Telefono","Correo","Nro. de poste"};
        List<Map<String,Object>> selectedColumns = new ArrayList<>();
        
        ExportUtil excelExporter = new ExportUtil(selectedColumns,cabecera,null);
        excelExporter.export(response);
        return null;
    }  
    
    @PostMapping(value = "import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> importFromExcel(
            @RequestPart("cnfLocal") String cnfLocal , 
            @Valid @NotNull @NotBlank @RequestPart("file") MultipartFile file , BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CnfLocal cnfLocalObject = mapper.readValue(cnfLocal, CnfLocal.class);
        List<UploadResponse> list = actContratoService.importExcel(file, cnfLocalObject);
        UploadResponse listWithErrors = list.stream().filter(predicate -> !predicate.getMessage().isEmpty()).findFirst().orElse(null);
        if (listWithErrors != null) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(list);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }
}

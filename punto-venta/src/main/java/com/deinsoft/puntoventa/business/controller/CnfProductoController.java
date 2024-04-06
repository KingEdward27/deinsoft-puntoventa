package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.service.CnfProductoService;
import com.deinsoft.puntoventa.business.service.StorageService;
import com.deinsoft.puntoventa.config.AppConfig;
import java.nio.file.Paths;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/business/cnf-producto")
public class CnfProductoController extends CommonController<CnfProducto, CnfProductoService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfProductoController.class);

    @Autowired
    CnfProductoService cnfProductoService;

    @Autowired
    StorageService storageService;
    
    @Autowired
    AppConfig appConfig;
    
    @GetMapping(value = "/get-all-cnf-producto")
    public List<CnfProducto> getAllCnfProducto(CnfProducto cnfProducto) {
        logger.info("getAllCnfProducto received: " + cnfProducto.toString());
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProducto(cnfProducto);
        return cnfProductoList;
    }

    @GetMapping(value = "/get-cnf-producto")
    public ResponseEntity<?> getCnfProducto(@Param("id") Long id) throws Exception {
        logger.info("getCnfProducto received: " + id);
        
        try {
            CnfProducto cnfProducto = cnfProductoService.getCnfProducto(id);
            return ResponseEntity.status(HttpStatus.OK).body(cnfProducto);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
        
    }

    @PostMapping(value = "/save-cnf-producto", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveCnfProducto(
            @RequestPart("cnfProducto") @Valid String cnfProductoDTO, 
            @RequestPart(name = "file", required = false) MultipartFile file, BindingResult result) throws JsonProcessingException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        CnfProducto cnfProducto = objectMapper.readValue(cnfProductoDTO, CnfProducto.class);
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Map<String, Object> errores = new HashMap<>();
        if(cnfProducto.getNombre().isEmpty()){
            errores.put("nombre", " Debe ingresar el nombre");
        }
        if(cnfProducto.getCnfUnidadMedida().getId() == 0){
            errores.put("cnfUnidadMedida", " Debe seleccionar la unidad de medida");
        }
        if(cnfProducto.getCnfSubCategoria().getId() == 0){
            errores.put("cnfSubCategoria", " Debe seleccionar la sub categoria");
        }
        if(!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        CnfProducto cnfProductoResult = cnfProductoService.saveCnfProducto(cnfProducto,file);
        return ResponseEntity.status(HttpStatus.CREATED).body(cnfProductoResult);
//        return super.crear(cnfProducto, result);
    }

    @GetMapping(value = "/get-all-cnf-producto-combo")
    public List<CnfProducto> getAllCnfProducto() {
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProducto();
        return cnfProductoList;
    }

    @DeleteMapping("/delete-cnf-producto")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        cnfProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-cnf-producto-by-cnf-unidad-medida")
    public List<CnfProducto> getAllCnfProductoByCnfUnidadMedida(@Param("id") Long id) {
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfUnidadMedida(id);
        return cnfProductoList;
    }

    @GetMapping(value = "/get-all-cnf-producto-by-cnf-empresa")
    public List<CnfProducto> getAllCnfProductoByCnfEmpresa(@Param("id") Long id) {
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfEmpresa(id);
        return cnfProductoList;
    }

    @GetMapping(value = "/get-all-cnf-producto-by-cnf-sub-categoria")
    public List<CnfProducto> getAllCnfProductoByCnfSubCategoria(@Param("id") Long id) {
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfSubCategoria(id);
        return cnfProductoList;
    }

    @GetMapping(value = "/get-all-cnf-producto-by-cnf-marca")
    public List<CnfProducto> getAllCnfProductoByCnfMarca(@Param("id") Long id) {
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoByCnfMarca(id);
        return cnfProductoList;
    }

    @GetMapping(value = "/get-all-cnf-producto-typehead")
    public ResponseEntity<?> getAllCnfProductTypeHead(String nameOrCode, long cnfEmpresaId,
            HttpServletRequest request) {
        List<CnfProducto> cnfProductList = cnfProductoService
                .getAllCnfProductTypeHead(nameOrCode, cnfEmpresaId);
        return ResponseEntity.status(HttpStatus.OK).body(cnfProductList);
    }
    @GetMapping(value = "/get-all-cnf-producto-typehead-no-servicios")
    public ResponseEntity<?> getAllCnfProductTypeHeadNoServicios(String nameOrCode, long cnfEmpresaId,
            HttpServletRequest request) {
        List<CnfProducto> cnfProductList = cnfProductoService
                .getAllCnfProductTypeHeadNoServicios(nameOrCode, cnfEmpresaId);
        return ResponseEntity.status(HttpStatus.OK).body(cnfProductList);
    }
    @PostMapping(value = "/get-all-cnf-producto-getpdf-codebars-pre")
    public List<CnfProducto> getCnfProductosPdfCodeBarsPre(@RequestBody ParamBean param){
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProductoCodeBarsPre(param);
        return cnfProductoList;
    }
    @PostMapping(value = "/getpdf-codebars")
    public ResponseEntity<?> getPdfCodeBars(@RequestBody ParamBean param) throws ParseException, Exception {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.APPLICATION_PDF;
//        Map<String, Object> map = param.getMap();
//        String id = map.get("id").toString();
//        int tipo = Integer.parseInt(map.get("tipo").toString());
        byte[] data = cnfProductoService.getPdfcodeBars(param);
        if (data != null) {
//            String type = "pdf";
            ByteArrayInputStream stream = new ByteArrayInputStream(data);
            headers.add("Content-Disposition", "inline; filename=pdf.pdf");
            mediaType = MediaType.APPLICATION_PDF;
//            if (type.equals("pdf")) {
//                headers.add("Content-Disposition", "inline; filename=pdf.pdf");
//                mediaType = MediaType.APPLICATION_PDF;
//            } else if (type.equals("excel")) {
//                headers.add("Content-Disposition", "attachment; filename=excel.xlsx");
//                mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            }
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body(new InputStreamResource(stream));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).contentType(mediaType).body("Ocurrió un error en el servidor");

    }
    
    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        cnfProductoService.storeTemp(file);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/get-ope-video-path")
    public String getPathPreResources(@Param("fileName") String fileName, @Param("fileName") long fileSize) {
        String path = storageService.getPath(Paths.get(appConfig.getFileSystemBasePath()+"/temp"), 
                Paths.get(appConfig.getFolderResources()), fileName, fileSize);
        return path;
    }
}

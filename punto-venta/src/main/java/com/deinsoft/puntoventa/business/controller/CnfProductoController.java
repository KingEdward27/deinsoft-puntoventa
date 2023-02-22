package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.service.CnfProductoService;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/business/cnf-producto")
public class CnfProductoController extends CommonController<CnfProducto, CnfProductoService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfProductoController.class);

    @Autowired
    CnfProductoService cnfProductoService;

    @GetMapping(value = "/get-all-cnf-producto")
    public List<CnfProducto> getAllCnfProducto(CnfProducto cnfProducto) {
        logger.info("getAllCnfProducto received: " + cnfProducto.toString());
        List<CnfProducto> cnfProductoList = cnfProductoService.getAllCnfProducto(cnfProducto);
        return cnfProductoList;
    }

    @GetMapping(value = "/get-cnf-producto")
    public CnfProducto getCnfProducto(@Param("id") Long id) {
        logger.info("getCnfProducto received: " + id);
        CnfProducto cnfProducto = cnfProductoService.getCnfProducto(id);
        return cnfProducto;
    }

    @PostMapping(value = "/save-cnf-producto")
    public ResponseEntity<?> saveCnfProducto(@Valid @RequestBody CnfProducto cnfProducto, BindingResult result) {
        
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Map<String, Object> errores = new HashMap<>();
        if(cnfProducto.getCnfUnidadMedida().getId() == 0){
            errores.put("cnfUnidadMedida.nombre", " Debe seleccionar la unidad de medida");
        }
        if(cnfProducto.getCnfSubCategoria().getId() == 0){
            errores.put("cnfSubCategoria.nombre", " Debe seleccionar la sub categoria");
        }
        if(!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        CnfProducto cnfProductoResult = cnfProductoService.saveCnfProducto(cnfProducto);
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
}

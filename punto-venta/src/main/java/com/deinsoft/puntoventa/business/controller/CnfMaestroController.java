package com.deinsoft.puntoventa.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.CnfMaestro;
import com.deinsoft.puntoventa.business.service.CnfMaestroService;
import com.deinsoft.puntoventa.framework.util.Util;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/business/cnf-maestro")
public class CnfMaestroController extends CommonController<CnfMaestro, CnfMaestroService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfMaestroController.class);

    @Autowired
    CnfMaestroService cnfMaestroService;

    @GetMapping(value = "/get-all-cnf-maestro")
    public List<CnfMaestro> getAllCnfMaestro(CnfMaestro cnfMaestro) {
        logger.info("getAllCnfMaestro received: " + cnfMaestro.toString());
        List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestro(cnfMaestro);
        return cnfMaestroList;
    }

    @GetMapping(value = "/get-cnf-maestro")
    public CnfMaestro getCnfMaestro(@Param("id") Long id) {
        logger.info("getCnfMaestro received: " + id);
        CnfMaestro cnfMaestro = cnfMaestroService.getCnfMaestro(id);
        return cnfMaestro;
    }

    @PostMapping(value = "/save-cnf-maestro")
    public ResponseEntity<?> saveCnfMaestro(@Valid @RequestBody CnfMaestro cnfMaestro, BindingResult result) throws Exception {
        Map<String, Object> errores = new HashMap<>();
        if(cnfMaestro.getCnfTipoDocumento().getId() == 0) {
            errores.put("cnfTipoDocumento.nombre", " Debe seleccionar el tipo de documento");
        }
        if(cnfMaestro.getCnfDistrito().getId() == 0){
            cnfMaestro.setCnfDistrito(null);
        }
        if(cnfMaestro.getCnfTipoDocumento().getCodigoSunat().equals("1") 
                && cnfMaestro.getNombres().trim().equals("")){
            errores.put("nombres", " Debe ingresar los nombres del cliente o proveedor");
        }
        if(cnfMaestro.getCnfTipoDocumento().getCodigoSunat().equals("6") 
                && cnfMaestro.getNroDoc().equals("")){
            errores.put("nombres", " Debe ingresar el número de documento del cliente o proveedor");
        }
        if(cnfMaestro.getCnfTipoDocumento().getCodigoSunat().equals("6") 
                && cnfMaestro.getRazonSocial().trim().equals("")){
            errores.put("razonSocial", " Debe ingresar la Razón Social del cliente o proveedor");
        }
        if(!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cnfMaestroService.saveCnfMaestro(cnfMaestro));
    }

    @GetMapping(value = "/get-all-cnf-maestro-combo")
    public List<CnfMaestro> getAllCnfMaestro() {
        List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestro();
        return cnfMaestroList;
    }

    @DeleteMapping("/delete-cnf-maestro")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        cnfMaestroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-cnf-maestro-by-cnf-tipo-documento")
    public List<CnfMaestro> getAllCnfMaestroByCnfTipoDocumento(@Param("id") Long id) {
        List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestroByCnfTipoDocumento(id);
        return cnfMaestroList;
    }

    @GetMapping(value = "/get-all-cnf-maestro-by-cnf-empresa")
    public List<CnfMaestro> getAllCnfMaestroByCnfEmpresa(@Param("id") Long id) {
        List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestroByCnfEmpresa(id);
        return cnfMaestroList;
    }

    @GetMapping(value = "/get-all-cnf-maestro-by-cnf-distrito")
    public List<CnfMaestro> getAllCnfMaestroByCnfDistrito(@Param("id") Long id) {
        List<CnfMaestro> cnfMaestroList = cnfMaestroService.getAllCnfMaestroByCnfDistrito(id);
        return cnfMaestroList;
    }

    @GetMapping(value = "/get-all-cnf-maestro-typehead")
    public ResponseEntity<?> getAllCnfMaestroTypeHead(String nameOrCode,String empresaId,
            HttpServletRequest request) {
        List<CnfMaestro> cnfProductList = cnfMaestroService
                .getAllCnfMaestroTypeHead(nameOrCode);
        if(empresaId != null && !empresaId.equals("*")) {
            cnfProductList = cnfProductList.stream()
                    .filter(item -> item.getCnfEmpresa().getId() == Integer.valueOf(empresaId))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.status(HttpStatus.OK).body(cnfProductList);
    }
    
    @GetMapping(value = "/get-search-sunat")
    public ResponseEntity<?> searchSunat (@Param("nroDoc") String nroDoc) throws Exception{
        Map<String,Object> result = null;
        Map<String,String> param = new HashMap<>();
        param.put("numero", nroDoc);
        if (nroDoc.length() == 8) {
            result = new Util().simpleGet(HttpMethod.GET, "https://api.apis.net.pe/v1/dni", "", param);
        } else if (nroDoc.length() == 11) {
            result = new Util().simpleGet(HttpMethod.GET, "https://api.apis.net.pe/v1/ruc", "", param);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

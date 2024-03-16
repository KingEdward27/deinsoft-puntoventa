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

import com.deinsoft.puntoventa.business.model.CnfEmpresa;
import com.deinsoft.puntoventa.business.service.CnfEmpresaService;

@RestController
@RequestMapping("/api/business/cnf-empresa")
public class CnfEmpresaController extends CommonController<CnfEmpresa, CnfEmpresaService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfEmpresaController.class);

    @Autowired
    CnfEmpresaService cnfEmpresaService;

    @GetMapping(value = "/get-all-cnf-empresa")
    public List<CnfEmpresa> getAllCnfEmpresa(CnfEmpresa cnfEmpresa) {
        logger.info("getAllCnfEmpresa received: " + cnfEmpresa.toString());
        List<CnfEmpresa> cnfEmpresaList = cnfEmpresaService.getAllCnfEmpresa(cnfEmpresa);
        return cnfEmpresaList;
    }

    @GetMapping(value = "/get-cnf-empresa")
    public CnfEmpresa getCnfEmpresa(@Param("id") Long id) {
        logger.info("getCnfEmpresa received: " + id);
        CnfEmpresa cnfEmpresa = cnfEmpresaService.getCnfEmpresa(id);
        return cnfEmpresa;
    }

    @PostMapping(value = "/save-cnf-empresa")
    public ResponseEntity<?> saveCnfEmpresa(@Valid @RequestBody CnfEmpresa cnfEmpresa, BindingResult result) {
        return super.crear(cnfEmpresa, result);
    }

    @GetMapping(value = "/get-all-cnf-empresa-combo")
    public List<CnfEmpresa> getAllCnfEmpresa() {
        List<CnfEmpresa> cnfEmpresaList = cnfEmpresaService.getAllCnfEmpresa();
        return cnfEmpresaList;
    }

    @DeleteMapping("/delete-cnf-empresa")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        cnfEmpresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-cnf-empresa-by-cnf-tipo-documento")
    public List<CnfEmpresa> getAllCnfEmpresaByCnfTipoDocumento(@Param("id") Long id) {
        List<CnfEmpresa> cnfEmpresaList = cnfEmpresaService.getAllCnfEmpresaByCnfTipoDocumento(id);
        return cnfEmpresaList;
    }

    @GetMapping(value = "/get-all-cnf-empresa-by-cnf-distrito")
    public List<CnfEmpresa> getAllCnfEmpresaByCnfDistrito(@Param("id") Long id) {
        List<CnfEmpresa> cnfEmpresaList = cnfEmpresaService.getAllCnfEmpresaByCnfDistrito(id);
        return cnfEmpresaList;
    }
}

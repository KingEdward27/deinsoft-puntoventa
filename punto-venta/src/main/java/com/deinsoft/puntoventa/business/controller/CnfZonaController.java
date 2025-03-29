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

import com.deinsoft.puntoventa.business.model.CnfZona;
import com.deinsoft.puntoventa.business.service.CnfZonaService;

@RestController
@RequestMapping("/api/business/cnf-zona")
public class CnfZonaController extends CommonController<CnfZona, Long, CnfZonaService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfZonaController.class);

    @Autowired
    CnfZonaService cnfZonaService;

    @GetMapping(value = "/get-all-cnf-zona")
    public List<CnfZona> getAllCnfZona(CnfZona cnfZona) {
        logger.info("getAllCnfZona received: " + cnfZona.toString());
        List<CnfZona> cnfZonaList = cnfZonaService.getAllCnfZona(cnfZona);
        return cnfZonaList;
    }

    @GetMapping(value = "/get-cnf-zona")
    public CnfZona getCnfZona(@Param("id") Long id) {
        logger.info("getCnfZona received: " + id);
        CnfZona cnfZona = cnfZonaService.getCnfZona(id);
        return cnfZona;
    }

    @PostMapping(value = "/save-cnf-zona")
    public ResponseEntity<?> saveCnfZona(@Valid @RequestBody CnfZona cnfZona, BindingResult result) {
        return super.crear(cnfZona, result);
    }

    @GetMapping(value = "/get-all-cnf-zona-combo")
    public List<CnfZona> getAllCnfZona() {
        List<CnfZona> cnfZonaList = cnfZonaService.getAllCnfZona();
        return cnfZonaList;
    }

    @DeleteMapping("/delete-cnf-zona")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        cnfZonaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-cnf-zona-by-cnf-empresa")
    public List<CnfZona> getAllCnfZonaByCnfEmpresa(@Param("id") Long id) {
        List<CnfZona> cnfZonaList = cnfZonaService.getAllCnfZonaByCnfEmpresa(id);
        return cnfZonaList;
    }
}

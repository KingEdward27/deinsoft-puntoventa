package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.CnfPlanContrato;
import com.deinsoft.puntoventa.business.service.CnfPlanContratoService;

@RestController
@RequestMapping("/api/business/cnf-plan-contrato")
public class CnfPlanContratoController extends CommonController<CnfPlanContrato, CnfPlanContratoService> {

    private static final Logger logger = LoggerFactory.getLogger(CnfPlanContratoController.class);

    @Autowired
    CnfPlanContratoService cnfPlanContratoService;

    @GetMapping(value = "/get-all-cnf-plan-contrato")
    public List<CnfPlanContrato> getAllCnfPlanContrato(CnfPlanContrato cnfPlanContrato) {
        logger.info("getAllCnfPlanContrato received: " + cnfPlanContrato.toString());
        List<CnfPlanContrato> cnfPlanContratoList = cnfPlanContratoService.getAllCnfPlanContrato(cnfPlanContrato);
        return cnfPlanContratoList;
    }

    @GetMapping(value = "/get-cnf-plan-contrato")
    public CnfPlanContrato getCnfPlanContrato(@Param("id") Long id) {
        logger.info("getCnfPlanContrato received: " + id);
        CnfPlanContrato cnfPlanContrato = cnfPlanContratoService.getCnfPlanContrato(id);
        return cnfPlanContrato;
    }

    @PostMapping(value = "/save-cnf-plan-contrato")
    public ResponseEntity<?> saveCnfPlanContrato(@Valid @RequestBody CnfPlanContrato cnfPlanContrato, BindingResult result) {
        return super.crear(cnfPlanContrato, result);
    }

    @GetMapping(value = "/get-all-cnf-plan-contrato-combo")
    public List<CnfPlanContrato> getAllCnfPlanContrato() {
        List<CnfPlanContrato> cnfPlanContratoList = cnfPlanContratoService.getAllCnfPlanContrato();
        return cnfPlanContratoList;
    }

    @DeleteMapping("/delete-cnf-plan-contrato")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        cnfPlanContratoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-cnf-plan-contrato-by-cnf-empresa")
    public List<CnfPlanContrato> getAllCnfPlanContratoByCnfEmpresa(@Param("id") Long id) {
        List<CnfPlanContrato> cnfPlanContratoList = cnfPlanContratoService.getAllCnfPlanContratoByCnfEmpresa(id);
        return cnfPlanContratoList;
    }
}

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

import com.deinsoft.puntoventa.business.model.ActCaja;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.service.ActCajaService;

@RestController
@RequestMapping("/api/business/act-caja")
public class ActCajaController extends CommonController<ActCaja, ActCajaService> {

    private static final Logger logger = LoggerFactory.getLogger(ActCajaController.class);

    @Autowired
    ActCajaService actCajaService;

    @GetMapping(value = "/get-all-act-caja")
    public List<ActCaja> getAllActCaja(ActCaja actCaja) {
        logger.info("getAllActCaja received: " + actCaja.toString());
        List<ActCaja> actCajaList = actCajaService.getAllActCaja(actCaja);
        return actCajaList;
    }

    @GetMapping(value = "/get-act-caja")
    public ActCaja getActCaja(@Param("id") Long id) {
        logger.info("getActCaja received: " + id);
        ActCaja actCaja = actCajaService.getActCaja(id);
        return actCaja;
    }

    @PostMapping(value = "/save-act-caja")
    public ResponseEntity<?> saveActCaja(@Valid @RequestBody ActCaja actCaja, BindingResult result) {
        return super.crear(actCaja, result);
    }

    @GetMapping(value = "/get-all-act-caja-combo")
    public List<ActCaja> getAllActCaja() {
        List<ActCaja> actCajaList = actCajaService.getAllActCaja();
        return actCajaList;
    }

    @DeleteMapping("/delete-act-caja")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actCajaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/get-all-act-caja-by-cnf-empresa")
    public List<ActCaja> getAllActCajaByCnfEmpresa(@Param("id") Long id) {
        List<ActCaja> list = actCajaService.getAllActCajaByCnfEmpresa(id);
        return list;
    }
}

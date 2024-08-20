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

import com.deinsoft.puntoventa.business.model.ActContratoMov;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.service.ActContratoCorteService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/business/act-contrato-corte")
public class ActContratoCorteController extends CommonController<ActContratoMov, ActContratoCorteService> {

    private static final Logger logger = LoggerFactory.getLogger(ActContratoCorteController.class);

    @Autowired
    ActContratoCorteService actContratoCorteService;

    @GetMapping(value = "/get-all-act-contrato-corte")
    public List<ActContratoMov> getAllActContratoCorte(ActContratoMov actContratoCorte) {
        logger.info("getAllActContratoCorte received: " + actContratoCorte.toString());
        List<ActContratoMov> actContratoCorteList = actContratoCorteService.getAllActContratoCorte(actContratoCorte);
        return actContratoCorteList;
    }

    @GetMapping(value = "/get-act-contrato-corte")
    public ActContratoMov getActContratoCorte(@Param("id") Long id) {
        logger.info("getActContratoCorte received: " + id);
        ActContratoMov actContratoCorte = actContratoCorteService.getActContratoCorte(id);
        return actContratoCorte;
    }

    @PostMapping(value = "/save-act-contrato-corte")
    public ResponseEntity<?> saveActContratoCorte(@Valid @RequestBody ActContratoMov actContratoCorte, BindingResult result) {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(actContratoCorteService.saveActContratoCorte(actContratoCorte));
//        return super.crear(actContratoCorte, result);
    }

    @GetMapping(value = "/get-all-act-contrato-corte-combo")
    public List<ActContratoMov> getAllActContratoCorte() {
        List<ActContratoMov> actContratoCorteList = actContratoCorteService.getAllActContratoCorte();
        return actContratoCorteList;
    }

    @DeleteMapping("/delete-act-contrato-corte")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actContratoCorteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-act-contrato-corte-by-act-contrato")
    public List<ActContratoMov> getAllActContratoCorteByActContrato(@Param("id") Long id) {
        List<ActContratoMov> actContratoCorteList = actContratoCorteService.getAllActContratoCorteByActContrato(id);
        return actContratoCorteList;
    }

    @GetMapping(value = "/get-all-act-contrato-corte-by-seg-usuario")
    public List<ActContratoMov> getAllActContratoCorteBySegUsuario(@Param("id") Long id) {
        List<ActContratoMov> actContratoCorteList = actContratoCorteService.getAllActContratoCorteBySegUsuario(id);
        return actContratoCorteList;
    }
}

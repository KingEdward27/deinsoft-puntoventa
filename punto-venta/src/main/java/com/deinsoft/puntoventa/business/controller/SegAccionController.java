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

import com.deinsoft.puntoventa.business.model.SegAccion;
import com.deinsoft.puntoventa.business.service.SegAccionService;

@RestController
@RequestMapping("/api/business/seg-accion")
public class SegAccionController extends CommonController<SegAccion, Long, SegAccionService> {

    private static final Logger logger = LoggerFactory.getLogger(SegAccionController.class);

    @Autowired
    SegAccionService segAccionService;

    @GetMapping(value = "/get-all-seg-accion")
    public List<SegAccion> getAllSegAccion(SegAccion segAccion) {
        logger.info("getAllSegAccion received: " + segAccion.toString());
        List<SegAccion> segAccionList = segAccionService.getAllSegAccion(segAccion);
        return segAccionList;
    }

    @GetMapping(value = "/get-seg-accion")
    public SegAccion getSegAccion(@Param("id") Long id) {
        logger.info("getSegAccion received: " + id);
        SegAccion segAccion = segAccionService.getSegAccion(id);
        return segAccion;
    }

    @PostMapping(value = "/save-seg-accion")
    public ResponseEntity<?> saveSegAccion(@Valid @RequestBody SegAccion segAccion, BindingResult result) {
        return super.crear(segAccion, result);
    }

    @GetMapping(value = "/get-all-seg-accion-combo")
    public List<SegAccion> getAllSegAccion() {
        List<SegAccion> segAccionList = segAccionService.getAllSegAccion();
        return segAccionList;
    }

    @DeleteMapping("/delete-seg-accion")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        segAccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

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

import com.deinsoft.puntoventa.business.model.SegRol;
import com.deinsoft.puntoventa.business.service.SegRolService;

@RestController
@RequestMapping("/api/business/seg-rol")
public class SegRolController extends CommonController<SegRol, SegRolService> {

    private static final Logger logger = LoggerFactory.getLogger(SegRolController.class);

    @Autowired
    SegRolService segRolService;

    @GetMapping(value = "/get-all-seg-rol")
    public List<SegRol> getAllSegRol(SegRol segRol) {
        logger.info("getAllSegRol received: " + segRol.toString());
        List<SegRol> segRolList = segRolService.getAllSegRol(segRol);
        return segRolList;
    }

    @GetMapping(value = "/get-seg-rol")
    public SegRol getSegRol(@Param("id") Long id) {
        logger.info("getSegRol received: " + id);
        SegRol segRol = segRolService.getSegRol(id);
        return segRol;
    }

    @PostMapping(value = "/save-seg-rol")
    public ResponseEntity<?> saveSegRol(@Valid @RequestBody SegRol segRol, BindingResult result) {
        return super.crear(segRol, result);
    }

    @GetMapping(value = "/get-all-seg-rol-combo")
    public List<SegRol> getAllSegRol() {
        List<SegRol> segRolList = segRolService.getAllSegRol();
        return segRolList;
    }

    @DeleteMapping("/delete-seg-rol")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        segRolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

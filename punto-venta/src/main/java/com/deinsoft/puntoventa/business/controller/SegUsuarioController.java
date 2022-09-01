package com.deinsoft.puntoventa.business.controller;

import java.util.List;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.service.SegUsuarioService;

@RestController
@RequestMapping("/api/business/seg-usuario")
public class SegUsuarioController extends CommonController<SegUsuario, SegUsuarioService> {

    private static final Logger logger = LoggerFactory.getLogger(SegUsuarioController.class);

    @Autowired
    SegUsuarioService segUsuarioService;

    @GetMapping(value = "/get-all-seg-usuario")
    public List<SegUsuario> getAllSegUsuario(SegUsuario segUsuario) {
        logger.info("getAllSegUsuario received: " + segUsuario.toString());
        List<SegUsuario> segUsuarioList = segUsuarioService.getAllSegUsuario(segUsuario);
        return segUsuarioList;
    }

    @GetMapping(value = "/get-seg-usuario")
    public SegUsuario getSegUsuario(@Param("id") Long id) {
        logger.info("getSegUsuario received: " + id);
        SegUsuario segUsuario = segUsuarioService.getSegUsuario(id);
        return segUsuario;
    }

    @PostMapping(value = "/save-seg-usuario")
    public ResponseEntity<?> saveSegUsuario(@Valid @RequestBody SegUsuario segUsuario, BindingResult result) {
        return super.crear(segUsuario, result);
    }

    @GetMapping(value = "/get-all-seg-usuario-combo")
    public List<SegUsuario> getAllSegUsuario() {
        List<SegUsuario> segUsuarioList = segUsuarioService.getAllSegUsuario();
        return segUsuarioList;
    }

    @DeleteMapping("/delete-seg-usuario")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        segUsuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

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

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.service.ActPagoProgramacionService;

@RestController
public class ActPagoProgramacionController extends CommonController<ActPagoProgramacion, ActPagoProgramacionService> {

    private static final Logger logger = LoggerFactory.getLogger(ActPagoProgramacionController.class);

    @Autowired
    ActPagoProgramacionService actPagoProgramacionService;

    @GetMapping(value = "/get-all-act-pago-programacion")
    public List<ActPagoProgramacion> getAllActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {
        logger.info("getAllActPagoProgramacion received: " + actPagoProgramacion.toString());
        List<ActPagoProgramacion> actPagoProgramacionList = actPagoProgramacionService.getAllActPagoProgramacion(actPagoProgramacion);
        return actPagoProgramacionList;
    }

    @GetMapping(value = "/get-act-pago-programacion")
    public ActPagoProgramacion getActPagoProgramacion(@Param("id") Long id) {
        logger.info("getActPagoProgramacion received: " + id);
        ActPagoProgramacion actPagoProgramacion = actPagoProgramacionService.getActPagoProgramacion(id);
        return actPagoProgramacion;
    }

    @PostMapping(value = "/save-act-pago-programacion")
    public ResponseEntity<?> saveActPagoProgramacion(@Valid @RequestBody ActPagoProgramacion actPagoProgramacion, BindingResult result) {
        return super.crear(actPagoProgramacion, result);
    }

    @GetMapping(value = "/get-all-act-pago-programacion-combo")
    public List<ActPagoProgramacion> getAllActPagoProgramacion() {
        List<ActPagoProgramacion> actPagoProgramacionList = actPagoProgramacionService.getAllActPagoProgramacion();
        return actPagoProgramacionList;
    }

    @DeleteMapping("/delete-act-pago-programacion")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actPagoProgramacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-act-pago-programacion-by-act-comprobante")
    public List<ActPagoProgramacion> getAllActPagoProgramacionByActComprobante(@Param("id") Long id) {
        List<ActPagoProgramacion> actPagoProgramacionList = actPagoProgramacionService.getAllActPagoProgramacionByActComprobante(id);
        return actPagoProgramacionList;
    }
}

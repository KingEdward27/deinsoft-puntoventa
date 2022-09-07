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

import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.service.ActCajaOperacionService;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/business/act-caja-operacion")
public class ActCajaOperacionController extends CommonController<ActCajaOperacion, ActCajaOperacionService> {

    private static final Logger logger = LoggerFactory.getLogger(ActCajaOperacionController.class);

    @Autowired
    ActCajaOperacionService actCajaOperacionService;

    @GetMapping(value = "/get-all-act-caja-operacion")
    public List<ActCajaOperacion> getAllActCajaOperacion(ActCajaOperacion actCajaOperacion) {
        logger.info("getAllActCajaOperacion received: " + actCajaOperacion.toString());
        List<ActCajaOperacion> actCajaOperacionList = actCajaOperacionService.getAllActCajaOperacion(actCajaOperacion);
        return actCajaOperacionList;
    }

    @GetMapping(value = "/get-act-caja-operacion")
    public ActCajaOperacion getActCajaOperacion(@Param("id") Long id) {
        logger.info("getActCajaOperacion received: " + id);
        ActCajaOperacion actCajaOperacion = actCajaOperacionService.getActCajaOperacion(id);
        return actCajaOperacion;
    }

    @PostMapping(value = "/save-act-caja-operacion")
    public ResponseEntity<?> saveActCajaOperacion(@Valid @RequestBody ActCajaOperacion actCajaOperacion, BindingResult result) {
        if (actCajaOperacion.getActComprobante() != null && actCajaOperacion.getActComprobante().getId() == 0) actCajaOperacion.setActComprobante(null);
        if (actCajaOperacion.getActPago()!= null && actCajaOperacion.getActPago().getId() == 0) actCajaOperacion.setActPago(null);
        actCajaOperacion.setEstado("1");
        actCajaOperacion.setFechaRegistro(LocalDateTime.now());
        return super.crear(actCajaOperacion, result);
    }

    @GetMapping(value = "/get-all-act-caja-operacion-combo")
    public List<ActCajaOperacion> getAllActCajaOperacion() {
        List<ActCajaOperacion> actCajaOperacionList = actCajaOperacionService.getAllActCajaOperacion();
        return actCajaOperacionList;
    }

    @DeleteMapping("/delete-act-caja-operacion")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actCajaOperacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-act-caja-operacion-by-act-caja-turno")
    public List<ActCajaOperacion> getAllActCajaOperacionByActCajaTurno(@Param("id") Long id) {
        List<ActCajaOperacion> actCajaOperacionList = actCajaOperacionService.getAllActCajaOperacionByActCajaTurno(id);
        return actCajaOperacionList;
    }

    @GetMapping(value = "/get-all-act-caja-operacion-by-act-comprobante")
    public List<ActCajaOperacion> getAllActCajaOperacionByActComprobante(@Param("id") Long id) {
        List<ActCajaOperacion> actCajaOperacionList = actCajaOperacionService.getAllActCajaOperacionByActComprobante(id);
        return actCajaOperacionList;
    }

    @GetMapping(value = "/get-all-act-caja-operacion-by-act-pago")
    public List<ActCajaOperacion> getAllActCajaOperacionByActPago(@Param("id") Long id) {
        List<ActCajaOperacion> actCajaOperacionList = actCajaOperacionService.getAllActCajaOperacionByActPago(id);
        return actCajaOperacionList;
    }
}

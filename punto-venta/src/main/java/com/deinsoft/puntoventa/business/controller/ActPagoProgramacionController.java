package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.service.ActPagoProgramacionService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("/api/business/act-pago-programacion")
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
    @GetMapping(value = "/get-all-act-pago-programacion-by-cnf-maestro")
    public List<ActPagoProgramacion> getAllActPagoProgramacionByCnfMaestro(@Param("id") Long id,
            @Param("fechaVencimiento") String fechaVencimiento,
            @Param("cnfLocalId") long cnfLocalId,
            @Param("onlyPendientes") boolean onlyPendientes) {
       LocalDate date = getDateFromString(fechaVencimiento);
        List<ActPagoProgramacion> actPagoProgramacionList 
                = actPagoProgramacionService.getAllActPagoProgramacionByCnfMaestro(id, date, cnfLocalId, onlyPendientes);
        return actPagoProgramacionList;
    }
    @GetMapping(value = "/get-all-act-pago-programacion-compra-by-cnf-maestro")
    public List<ActPagoProgramacion> getAllActPagoProgramacionCompraByCnfMaestro(@Param("id") Long id,
            @Param("fechaVencimiento") String fechaVencimiento) {
        LocalDate date = getDateFromString(fechaVencimiento);
        List<ActPagoProgramacion> actPagoProgramacionList 
                = actPagoProgramacionService.getAllActPagoProgramacionCompraByCnfMaestro(id, date);
        return actPagoProgramacionList;
    }

    private LocalDate getDateFromString(String fechaVencimiento) {
        if (fechaVencimiento.equals("")){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale( Locale.getDefault() );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse(fechaVencimiento, formatter);
        return date;
    }
    
    
}

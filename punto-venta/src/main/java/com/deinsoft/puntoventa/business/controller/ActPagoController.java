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

import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.service.ActPagoService;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/business/act-pago")
public class ActPagoController extends CommonController<ActPago, ActPagoService> {

    private static final Logger logger = LoggerFactory.getLogger(ActPagoController.class);

    @Autowired
    ActPagoService actPagoService;

    @Autowired
    AuthenticationHelper auth;
    
    @GetMapping(value = "/get-all-act-pago")
    public List<ActPago> getAllActPago(ActPago actPago) {
        logger.info("getAllActPago received: " + actPago.toString());
        List<ActPago> actPagoList = actPagoService.getAllActPago(actPago);
        return actPagoList;
    }

    @GetMapping(value = "/get-act-pago")
    public ActPago getActPago(@Param("id") Long id) {
        logger.info("getActPago received: " + id);
        ActPago actPago = actPagoService.getActPago(id);
        return actPago;
    }

    @PostMapping(value = "/save-act-pago")
    public ResponseEntity<?> saveActPago(@Valid @RequestBody ActPago actPago, BindingResult result) throws  Exception{
        if (result.hasErrors()) {
            return this.validar(result);
        }
        SegUsuario segUsuario = auth.getLoggedUserdata();
        actPago.setSegUsuario(segUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(actPagoService.saveActPago(actPago));
    }

    @GetMapping(value = "/get-all-act-pago-combo")
    public List<ActPago> getAllActPago() {
        List<ActPago> actPagoList = actPagoService.getAllActPago();
        return actPagoList;
    }

    @DeleteMapping("/delete-act-pago")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actPagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping(value = "/get-all-act-pago-by-act-pago-programacion")
//    public List<ActPago> getAllActPagoByActPagoProgramacion(@Param("id") Long id) {
//        List<ActPago> actPagoList = actPagoService.getAllActPagoByActPagoProgramacion(id);
//        return actPagoList;
//    }

//    @PostMapping(value = "/save-list-act-pago")
//    public ResponseEntity<?> saveListActPaymentDetail(
//            @RequestBody List<ActPagoProgramacion> listActPayment, HttpServletRequest request) throws Exception {
//        SegUsuario segUsuario = auth.getLoggedUserdata();
//        listActPayment.forEach(data -> {
//            data.setSegUsuario(segUsuario);
//        });
//        List<ActPago> listActPaymentDetailResult = actPagoService.saveActPaymentDetailFromList(listActPayment);
//        return ResponseEntity.status(HttpStatus.CREATED).body(listActPaymentDetailResult);
//    }
}

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

import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.service.ActPagoService;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.Map;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/business/act-pago")
public class ActPagoController extends CommonController<ActPago, Long, ActPagoService> {

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
    @PostMapping(value = "/get-report-act-pago")
    public List<ActPago> getReportActPago(@RequestBody ParamBean paramBean) {
        logger.info("getReportActCajaOperacion received: " + paramBean.toString());
        List<ActPago> actPagoList = actPagoService.getReportActPago(paramBean);
        return actPagoList; 
    }
    @PostMapping(value = "/getpdflocal")
    public ResponseEntity<?> getPdfLocal(@RequestBody UpdateParam param) throws ParseException, Exception {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.APPLICATION_PDF;
        Map<String, Object> map = param.getMap();
        String id = map.get("id").toString();
        int tipo = Integer.parseInt(map.get("tipo").toString());
        byte[] data = actPagoService.getPDFLocal(new Long(id), tipo);
        if(data != null){
            String type = "pdf";
            ByteArrayInputStream stream = new ByteArrayInputStream(data);
            if (type.equals("pdf")) {
                headers.add("Content-Disposition", "inline; filename=pdf.pdf");
                mediaType = MediaType.APPLICATION_PDF;
            } else if (type.equals("excel")) {
                headers.add("Content-Disposition", "attachment; filename=excel.xlsx");
                mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            }
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body(new InputStreamResource(stream));
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body("Ocurrió un error comunicandose con el api facturador");
        
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

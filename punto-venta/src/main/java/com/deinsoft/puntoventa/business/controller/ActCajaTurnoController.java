package com.deinsoft.puntoventa.business.controller;

import com.deinsoft.puntoventa.business.bean.ParamBean;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.deinsoft.puntoventa.business.dto.MedioPagoDTO;
import com.deinsoft.puntoventa.framework.model.UpdateParam;
import com.deinsoft.puntoventa.framework.util.pdf.PDFConfig;
import com.deinsoft.puntoventa.framework.util.pdf.PDFContent;
import com.deinsoft.puntoventa.framework.util.pdf.PDFContentType;
import com.deinsoft.puntoventa.framework.util.pdf.PDFGenerator;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import com.deinsoft.puntoventa.business.commons.controller.CommonController;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.service.ActCajaTurnoService;
import java.math.BigDecimal;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/business/act-caja-turno")
public class ActCajaTurnoController extends CommonController<ActCajaTurno, Long, ActCajaTurnoService> {

    private static final Logger logger = LoggerFactory.getLogger(ActCajaTurnoController.class);

    @Autowired
    ActCajaTurnoService actCajaTurnoService;

    @GetMapping(value = "/get-all-act-caja-turno")
    public List<ActCajaTurno> getAllActCajaTurno(ActCajaTurno actCajaTurno) {
        logger.info("getAllActCajaTurno received: " + actCajaTurno.toString());
        List<ActCajaTurno> actCajaTurnoList = actCajaTurnoService.getAllActCajaTurno(actCajaTurno);
        return actCajaTurnoList;
    }

    @GetMapping(value = "/get-act-caja-turno")
    public ResponseEntity<?> getActCajaTurno(@Param("id") Long id) {
        logger.info("getActCajaTurno received: " + id);
        try {
            ActCajaTurno actCajaTurno = actCajaTurnoService.getActCajaTurno(id);
            return ResponseEntity.status(HttpStatus.OK).body(actCajaTurno);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
        
    }

    @PostMapping(value = "/save-act-caja-turno")
    public ResponseEntity<?> saveActCajaTurno(@Valid @RequestBody ActCajaTurno actCajaTurno, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Map<String, Object> errores = new HashMap<>();
        if (actCajaTurno.getActCaja().getId() == 0) {
            errores.put("actCaja", " Debe seleccionar la caja");
        }
        if (actCajaTurno.getSegUsuario().getId() == 0) {
            errores.put("segUsuario", " Debe seleccionar el usuario");
        }
        try {
            if (actCajaTurno.getMontoApertura().compareTo(BigDecimal.ZERO) == -1) {
                errores.put("montoApertura", " Debe seleccionar la moneda");
            }
        } catch (Exception ex) {
            errores.put("montoApertura", "La cantidad debe ser una número positivo");
        }
        
//        if (actCajaTurno.getMontoCierre().compareTo(BigDecimal.ZERO) == -1) {
//            if (actComprobante.getCnfLocal().getId() == 0) {
//                errores.put("cnfLocal", " Debe seleccionar el Local");
//            }
//        }
        
        if (!errores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(actCajaTurnoService.saveActCajaTurno(actCajaTurno));
    }

    @GetMapping(value = "/get-all-act-caja-turno-combo")
    public List<ActCajaTurno> getAllActCajaTurno() {
        List<ActCajaTurno> actCajaTurnoList = actCajaTurnoService.getAllActCajaTurno();
        return actCajaTurnoList;
    }

    @DeleteMapping("/delete-act-caja-turno")
    public ResponseEntity<?> delete(@Param("id") Long id) {
        actCajaTurnoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get-all-act-caja-turno-by-seg-usuario")
    public List<ActCajaTurno> getAllActCajaTurnoBySegUsuario(@Param("id") Long id) {
        List<ActCajaTurno> actCajaTurnoList = actCajaTurnoService.getAllActCajaTurnoBySegUsuario(id);
        return actCajaTurnoList;
    }
    @PostMapping(value = "/get-report-act-caja-turno")
    public List<ActCajaTurno> getReportActCajaOperacion(@RequestBody ParamBean paramBean) {
        logger.info("getReportActCajaOperacion received: " + paramBean.toString());
        List<ActCajaTurno> actCajaOperacionList = actCajaTurnoService.getReportActCajaTurno(paramBean);
        return actCajaOperacionList; 
    }

    @PostMapping(value = "/get-cierre-act-caja-turno")
    public ResponseEntity<?> getCierreActCajaTurno(@RequestBody UpdateParam param) {
        Map<String, Object> map = param.getMap();
        String id = map.get("id").toString();
        byte[] data = actCajaTurnoService.getReportCierreCaja(new Long(id));
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.APPLICATION_PDF;
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        headers.add("Content-Disposition", "inline; filename=pdf.pdf");
        mediaType = MediaType.APPLICATION_PDF;
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType)
                .body(new InputStreamResource(stream));

//        try {
//            PDFConfig configA4 = PDFConfig.builder()
//                    .pageSize("A4")
//                    .logoPath("D:/qrcode.png")
//                    .printedBy("UsuarioAdmin")
//                    .fontSize(14)
//                    .build();
//
//
//            List<PDFContent> contentA4 = Arrays.asList(
//                    PDFContent.builder()
//                            .type(PDFContentType.TEXT)
//                            .text("Título del Reporte")
//                            .alignment("center").build(),
//                    PDFContent.builder()
//                            .type(PDFContentType.TEXT)
//                            .text("Subtítulo del Reporte")
//                            .alignment("center").build(),
//                    PDFContent.builder()
//                            .type(PDFContentType.TABLE)
//                            .tableData(Arrays.asList(
//                                    new HashMap<String, String>() {{
//                                        put("Nombre", "Juan");
//                                        put("Edad", "25");
//                                        put("Ciudad", "Madrid");
//                                    }},
//                                    new HashMap<String, String>() {{
//                                        put("Nombre", "Ana");
//                                        put("Edad", "30");
//                                        put("Ciudad", "Barcelona");
//                                    }},
//                                    new HashMap<String, String>() {{
//                                        put("Nombre", "Luis");
//                                        put("Edad", "28");
//                                        put("Ciudad", "Valencia");
//                                    }}
//                            )).build(),
//                    PDFContent.builder()
//                            .type(PDFContentType.SEPARATOR)
//                    .isSeparator(true).build(),
//                    PDFContent.builder()
//                    .type(PDFContentType.TEXT)
//                    .text("Resumen")
//                    .alignment("right")
//                    .spacingAfter(5f)
//                    .build());
//
//            OutputStream outputA4 = new FileOutputStream("Reporte_A4.pdf");
//            PDFGenerator.generatePDF(outputA4, contentA4, configA4);
//
//
//            PDFConfig configTicket = PDFConfig.builder()
//                    .pageSize("ticket")
//                    //.logoPath("D:/qrcode.png")
//                    .fontSize(14)
//                    .build();
//
//
//            List<PDFContent> contentTicket = new ArrayList<>();
//            contentTicket.add(PDFContent.builder()
//                    .type(PDFContentType.TEXT)
//                    .text("Cierre de Caja")
//                    .alignment("center").build());
//
//            contentTicket.add(PDFContent.builder()
//                    .type(PDFContentType.TEXT)
//                    .text("Subtítulo del Reporte")
//                    .alignment("center").build());
//
//            for (int i = 1; i <= 10; i++) {
//
//                contentTicket.add(PDFContent.builder()
//                        .type(PDFContentType.TEXT)
//                        .text("Producto " + i + " - $10.00")
//                        .fontSize(12)
//                        .alignment("left")
//                        .spacingAfter(5f)
//                        .build());
//            }
//
//            contentTicket.addAll(
//                    Arrays.asList(PDFContent.builder()
//                            .type(PDFContentType.SEPARATOR)
//                            .isSeparator(true).build(),
//                    PDFContent.builder()
//                            .type(PDFContentType.TEXT)
//                            .text("TOTAL: $50.00")
//                            .alignment("right")
//                            .spacingAfter(5f)
//                            .build()));
//            OutputStream outputTicket = new FileOutputStream("Ticket.pdf");
//            PDFGenerator.generatePDF(outputTicket, contentTicket, configTicket);
//
////            // Configuración del ticket
////            config = new PDFConfig();
////            config.setTitle("Ticket de Compra");
////            config.setFontSize(10);
////            config.setPageSize("ticket"); // Se define el tamaño del ticket
////            //config.setLogoPath("ruta/del/logo.png"); // Cambia esto a la ruta del logo
////            config.setPrintedBy("Cajero 01");
////
////            // Contenido del ticket
////            contentData = new ArrayList<>();
////
////            // Título del ticket
////            title = new HashMap<>();
////            title.put("text", "SUPERMERCADO XYZ");
////            title.put("fontSize", 16);
////            title.put("alignment", "center");
////            title.put("spacingAfter", 10f);
////            contentData.add(title);
////
////            // Subtítulo
////            Map<String, Object> subtitle = new HashMap<>();
////            subtitle.put("text", "Gracias por su compra");
////            subtitle.put("fontSize", 12);
////            subtitle.put("alignment", "center");
////            subtitle.put("spacingAfter", 5f);
////            contentData.add(subtitle);
////
////            // Lista de productos (Ejemplo)
////            for (int i = 1; i <= 5; i++) {
////                Map<String, Object> product = new HashMap<>();
////                product.put("text", "Producto " + i + " - $10.00");
////                product.put("fontSize", 10);
////                product.put("alignment", "left");
////                contentData.add(product);
////            }
////
////
////            Map<String, Object> separator1 = new HashMap<>();
////            separator1.put("separator", true);
////            contentData.add(separator1);
////
////            // Total
////            Map<String, Object> total = new HashMap<>();
////            total.put("text", "TOTAL: $50.00");
////            total.put("fontSize", 14);
////            total.put("alignment", "right");
////            total.put("spacingAfter", 10f);
////            contentData.add(total);
////
////            // Generar el ticket en un archivo PDF
////            FileOutputStream fos = new FileOutputStream("ticket.pdf");
////            PDFGenerator.generatePDF(fos, contentData, config);
////            fos.close();
////
////            System.out.println("Ticket generado con éxito!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }
}

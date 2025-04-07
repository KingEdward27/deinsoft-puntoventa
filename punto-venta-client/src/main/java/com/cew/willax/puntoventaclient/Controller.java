package com.cew.willax.puntoventaclient;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
@RestController
@RequestMapping("/api/v1/print")
public class Controller {
    @PostMapping(value = "/ticket")
    public ResponseEntity<?> printTicket(@RequestBody Map<String, Object> params) throws ParseException, Exception {

        JasperReport reporte = null;
        String ubicacion = "";
        ubicacion = "/jasper/ticket.jasper";
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println("currentPath + ubicacion: " + currentPath + ubicacion);
        File archivo = new File(currentPath + ubicacion);
        InputStream targetStream = new FileInputStream(archivo);
        reporte = (JasperReport) JRLoader.loadObject(targetStream);
        List<Map<String, String>> listaBean = params.get("details") != null ?
                (List<Map<String, String>>) params.get("details") : new ArrayList<>();
        JasperPrint print = JasperFillManager.fillReport(reporte, params, new JRBeanCollectionDataSource(listaBean));
//        JasperViewer visor = new JasperViewer(print, false);
        sendToPrinter(print, params.get("impresora_nombre").toString());

//        HttpHeaders headers = new HttpHeaders();
//        MediaType mediaType = MediaType.APPLICATION_PDF;
//        Map<String, Object> map = param.getMap();
//        String id = map.get("id").toString();
//        int tipo = Integer.parseInt(map.get("tipo").toString());
//        byte[] data = actComprobanteService.getPDFLocal(new Long(id), tipo);
//        if(data != null){
//            String type = "pdf";
//            ByteArrayInputStream stream = new ByteArrayInputStream(data);
//            if (type.equals("pdf")) {
//                headers.add("Content-Disposition", "inline; filename=pdf.pdf");
//                mediaType = MediaType.APPLICATION_PDF;
//            } else if (type.equals("excel")) {
//                headers.add("Content-Disposition", "attachment; filename=excel.xlsx");
//                mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            }
//            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body(new InputStreamResource(stream));
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).contentType(mediaType).body("Ocurrió un error comunicandose con el api facturador");
        return null;
    }
    public static void sendToPrinter(JasperPrint jasper, String printerName) throws JRException {
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
        printerJob.defaultPage(pageFormat);

        int selectedService = 0;
//                                        PrinterName printerName = new PrinterName("Microsoft XPS Document Writer", null); //gets printer
        javax.print.attribute.AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName(printerName, null));

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);

        try {
            printerJob.setPrintService(printService[selectedService]);

        } catch (Exception e) {

            System.out.println(e);
        }
        JRPrintServiceExporter exporter;
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
        printRequestAttributeSet.add(new Copies(1));

        // these are deprecated
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deinsoft.puntoventa.util;

import com.deinsoft.puntoventa.framework.util.CodigoQR;
import com.deinsoft.puntoventa.framework.util.Formatos;
import com.deinsoft.puntoventa.framework.util.NumberToLetterConverter;
import com.deinsoft.puntoventa.framework.util.Util;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
//import sistventa.JDVisor;
//import static sistventa.JIVentas.round;

/**
 *
 * @author EDWARD
 */
public class Impresion {

    public static final float IGV = 0.18f;
    public static final String UNIDAD_MEDIDA = "ZZ";

    public static ByteArrayInputStream Imprimir(int tipo,Map<String,Object> datosVenta, boolean isTicket) {
        try {
            JasperReport reporte = null;
            String ubicacion = "";
            if (tipo == 1) {
                ubicacion = "/jasper/boleta.jasper";
            } else {
                ubicacion = "/jasper/ticket.jasper";
            }
            String currentPath = new java.io.File(".").getCanonicalPath();
            System.out.println("currentPath + ubicacion: " + currentPath + ubicacion);
            File archivo = new File(currentPath + ubicacion);
            InputStream targetStream = new FileInputStream(archivo);
            reporte = (JasperReport) JRLoader.loadObject(targetStream);
            Map parametros;
            parametros = new HashMap<String, Object>();
            
            Map<String,Object> cliente = (Map<String,Object>)datosVenta.get("cnf_maestro");
            Map<String,Object> tipoDocId = (Map<String,Object>)cliente.get("cnf_tipo_documento");
            //Map<String,Object> numComprobante = (Map<String,Object>)datosVenta.get("cnf_num_comprobante");
            Map<String,Object> tipoDoc = (Map<String,Object>)datosVenta.get("cnf_tipo_comprobante");
            Map<String,Object> formaPago = (Map<String,Object>)datosVenta.get("cnf_forma_pago");
            Map<String,Object> moneda = (Map<String,Object>)datosVenta.get("cnf_moneda");
            List<Map<String,Object>> listDetail = (List<Map<String,Object>>)datosVenta.get("list_act_comprobante_detalle");
            Map<String,Object> local = (Map<String,Object>)datosVenta.get("cnf_local");
            Map<String,Object> empresa = (Map<String,Object>)local.get("cnf_empresa");
            parametros.put("razon_social", empresa.get("nombre").toString());
            parametros.put("direccion", empresa.get("direccion").toString());
            parametros.put("ruc", empresa.get("nro_documento").toString());
            parametros.put("ptelefono", empresa.get("telefono").toString());

            String tipoDocSunat = tipoDoc.get("codigo_sunat").toString();
            parametros.put("tipodoc", isTicket ? "TICKET DE ATENCIÓN" : (tipoDocSunat.equals("00") ? 
                    tipoDoc.get("nombre").toString() : tipoDoc.get("nombre").toString() + " ELECTRÓNICA"));
            parametros.put("numero", isTicket ? datosVenta.get("serie").toString() + "-" + String.format("%08d", Integer.parseInt(String.valueOf(datosVenta.get("numero")))) : 
                    datosVenta.get("serie").toString() + "-" + String.format("%08d", Integer.parseInt(String.valueOf(datosVenta.get("numero")))));
            
            
            parametros.put("ruc_dniCliente", cliente.get("nro_doc").toString());
            parametros.put("nombreCliente", cliente.get("apellido_paterno").toString() + " " + 
                                            cliente.get("apellido_materno").toString() + " " +
                                            cliente.get("nombres").toString());
            parametros.put("direccionCliente", cliente.get("direccion").toString());
            
            parametros.put("pFechaEmision",Formatos.sdfFecha.format(datosVenta.get("fecha")));
            parametros.put("fechaVencimiento",datosVenta.get("fecha_vencimiento") == null?
                                                "":Formatos.sdfFecha.format(datosVenta.get("fecha_vencimiento")));
            parametros.put("moneda", moneda.get("codigo").toString());

            parametros.put("pdescuento2", Formatos.df.format(0));
            parametros.put("pgravado", Formatos.df.format(Util.getBigDecimalValue(datosVenta,"total")));
            parametros.put("pigv", Formatos.df.format(Util.getBigDecimalValue(datosVenta,"igv")));
            parametros.put("ptotal", Formatos.df.format(Util.getBigDecimalValue(datosVenta,"total")));
            parametros.put("ptotal_letras", "SON " + NumberToLetterConverter.convertNumberToLetter( 
                                                    datosVenta.get("total").toString(),moneda.get("nombre").toString()));

            parametros.put("pusuario_fecha", "admin el " + Formatos.sdfFecha.format(datosVenta.get("fecha")));
            parametros.put("presolucion", "Autorizado mediantes resolución N° " + Constantes.RESOLUCION);
            parametros.put("tipoDocFooter", "Representación impresa de el/la " + tipoDoc.get("nombre").toString() + " ELECTRÓNICA");
            parametros.put("ppagina", "Para consultar el comprobante visita " + Constantes.PAGINA_WEB);
            parametros.put("presumen", datosVenta.get("xmlhash") == null?"":datosVenta.get("xmlhash").toString());
            parametros.put("pACuenta", Formatos.df.format(0));
            parametros.put("pSaldo", Formatos.df.format(0));
            parametros.put("pRecibido", Formatos.df.format(Util.getBigDecimalValue(datosVenta,"billete")==null?0:Util.getBigDecimalValue(datosVenta,"billete")));
            parametros.put("pVuelto", Formatos.df.format(Util.getBigDecimalValue(datosVenta,"vuelto")==null?0:Util.getBigDecimalValue(datosVenta,"vuelto")));
            parametros.put("pAnticipo", Formatos.df.format(0));
            parametros.put("idTipoDoc", tipoDocSunat== null? "00":tipoDocSunat);
            parametros.put("isTicket", isTicket);
//            parametros.put("psummary", "NOTA: Una vez retirada la prenda no hay lugar a reclamo. "
//                    + "Pasado 30 dias de no retirar su ropa esta sera rematada "
//                    + "para recuperar los gastos del servicio dado."
//                    + "Prendas gastadas que no soporten el lavado seran "
//                    + "responsabilidad del cliente. En este caso la lavenderia "
//                    + "no se responsabiliza");
            if (!isTicket) {
                String pathResult = CodigoQR.GenerarQR("/temp",
                        empresa.get("nro_documento").toString() + "|"
                        + tipoDocSunat + "|"
                        + datosVenta.get("serie").toString() + "-" + String.format("%08d", Integer.parseInt(String.valueOf(datosVenta.get("numero")))) + "|"
                        + Formatos.df.format(Util.getBigDecimalValue(datosVenta,"igv")) + "|"
                        + Formatos.df.format(Util.getBigDecimalValue(datosVenta,"total")) + "|"
                        + Formatos.sdfFecha.format(datosVenta.get("fecha")) + "|"
                        + tipoDocId.get("codigo_sunat").toString() + "|"
                        + cliente.get("nro_doc"));
                if (!pathResult.equals("")) {
                    parametros.put("rutaimagen", pathResult);
                }
            }

            List<Map<String, String>> listaBean = new ArrayList();
            Integer count = 0;
            for (Map<String, Object> detalleVenta : listDetail) {
                ++count;
                Map<String,Object> producto = (Map<String,Object>)detalleVenta.get("cnf_producto");
                Map<String,Object> um = (Map<String,Object>)producto.get("cnf_unidad_medida");
                Map<String,Object> impuesto = (Map<String,Object>)detalleVenta.get("cnf_impuesto_condicion");
                Map<String, String> beanMap = new HashMap<>();
                float subtotalDet = Util.round(Util.getBigDecimalValue(detalleVenta,"precio").floatValue() / (IGV + 1), 2);
                float igv = Util.round(Util.getBigDecimalValue(detalleVenta,"precio").floatValue() - subtotalDet, 2);
                beanMap.put("nro", count.toString());
                beanMap.put("cantidad",Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"cantidad")));
                beanMap.put("um", um.get("codigo_sunat").toString());
                beanMap.put("codigo", producto.get("codigo").toString());
                beanMap.put("descripcion", producto.get("nombre").toString());
                beanMap.put("vu", Formatos.df.format(subtotalDet));
                beanMap.put("pu", Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"precio")));
                beanMap.put("igv", Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"afectacion_igv")));
                beanMap.put("descuento", Formatos.df.format(0));
                beanMap.put("importe", Formatos.df.format(Util.getBigDecimalValue(detalleVenta,"precio")
                                                                .multiply(Util.getBigDecimalValue(detalleVenta,"cantidad"))));

                listaBean.add(beanMap);
            }
            if (count < 20 && tipo == 1) {
                if (count >= 15 && count < 20) {
                    for (int i = 0; i < 2; i++) {
                        Map<String, String> beanMap = new HashMap<>();
                        String empty = "";
                        beanMap.put("nro", empty);
                        beanMap.put("cantidad", empty);
                        beanMap.put("um", empty);
                        beanMap.put("codigo", empty);
                        beanMap.put("descripcion", empty);
                        beanMap.put("vu", empty);
                        beanMap.put("pu", empty);
                        beanMap.put("igv", empty);
                        beanMap.put("descuento", empty);
                        beanMap.put("importe", empty);
                        listaBean.add(beanMap);
                    }

                } else if (count >= 10 && count < 15) {
                    for (int i = 0; i < 5; i++) {
                        Map<String, String> beanMap = new HashMap<>();
                        String empty = "";
                        beanMap.put("nro", empty);
                        beanMap.put("cantidad", empty);
                        beanMap.put("um", empty);
                        beanMap.put("codigo", empty);
                        beanMap.put("descripcion", empty);
                        beanMap.put("vu", empty);
                        beanMap.put("pu", empty);
                        beanMap.put("igv", empty);
                        beanMap.put("descuento", empty);
                        beanMap.put("importe", empty);
                        listaBean.add(beanMap);
                    }
                } else if (count >= 5 && count < 10) {
                    for (int i = 0; i < 5; i++) {
                        Map<String, String> beanMap = new HashMap<>();
                        String empty = "";
                        beanMap.put("nro", empty);
                        beanMap.put("cantidad", empty);
                        beanMap.put("um", empty);
                        beanMap.put("codigo", empty);
                        beanMap.put("descripcion", empty);
                        beanMap.put("vu", empty);
                        beanMap.put("pu", empty);
                        beanMap.put("igv", empty);
                        beanMap.put("descuento", empty);
                        beanMap.put("importe", empty);
                        listaBean.add(beanMap);
                    }
                } else if (count < 5) {
                    for (int i = 0; i < 10; i++) {
                        Map<String, String> beanMap = new HashMap<>();
                        String empty = "";
                        beanMap.put("nro", empty);
                        beanMap.put("cantidad", empty);
                        beanMap.put("um", empty);
                        beanMap.put("codigo", empty);
                        beanMap.put("descripcion", empty);
                        beanMap.put("vu", empty);
                        beanMap.put("pu", empty);
                        beanMap.put("igv", empty);
                        beanMap.put("descuento", empty);
                        beanMap.put("importe", empty);
                        listaBean.add(beanMap);
                    }
                }
            }
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(listaBean));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
//            JasperViewer visor = new JasperViewer(print,false);
            
//            JDVisor dialog = new JDVisor(null, true);//the owner
//            dialog.setContentPane(visor.getContentPane());
//            dialog.setSize(visor.getSize());
//            dialog.setTitle("Visor");
//            dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(
//            getClass().getResource("URL IMG")));
//            dialog.setVisible(true);
            
//            visor.setTitle("Impresión de documento");
//            visor.setVisible(true);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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
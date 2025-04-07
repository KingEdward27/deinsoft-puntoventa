package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

import com.deinsoft.puntoventa.business.dto.MedioPagoDTO;
import com.deinsoft.puntoventa.business.dto.TopProductosDto;
import com.deinsoft.puntoventa.business.service.ActCajaOperacionService;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.framework.util.pdf.PDFConfig;
import com.deinsoft.puntoventa.framework.util.pdf.PDFContent;
import com.deinsoft.puntoventa.framework.util.pdf.PDFContentType;
import com.deinsoft.puntoventa.framework.util.pdf.PDFGenerator;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.service.ActCajaTurnoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import com.deinsoft.puntoventa.util.Constantes;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActCajaTurnoServiceImpl extends CommonServiceImpl<ActCajaTurno,Long, ActCajaTurnoRepository>
        implements ActCajaTurnoService {

    @Autowired
    ActCajaTurnoRepository actCajaTurnoRepository;
    @Autowired
    ActCajaOperacionService actCajaOperacionService;
    public List<ActCajaTurno> getAllActCajaTurno(ActCajaTurno actCajaTurno) {
        List<ActCajaTurno> actCajaTurnoList 
                = (List<ActCajaTurno>) actCajaTurnoRepository.getAllActCajaTurno(actCajaTurno.getEstado().toUpperCase(),listRoles());
        return actCajaTurnoList;//.stream()
//                .filter(item -> listRoles().stream()
//                        .anyMatch(predicate -> predicate.getEmpresa().getId() == item.getActCaja().getCnfEmpresa().getId()))
//                .collect(Collectors.toList());
    }

    public ActCajaTurno getActCajaTurno(Long id){
        ActCajaTurno actCajaTurno = null;
        Optional<ActCajaTurno> actCajaTurnoOptional = actCajaTurnoRepository.findById(id);
        if (actCajaTurnoOptional.isPresent()) {
            actCajaTurno = actCajaTurnoOptional.get();
            SecurityFilterDto f = listRoles();
            if (f.getEmpresaId() != actCajaTurno.getActCaja().getCnfEmpresa().getId()) {
                throw new SecurityException(Constantes.MSG_NO_AUTHORIZED);
            }
        } else {
            throw new SecurityException(Constantes.MSG_NO_EXISTS_ITEM);
        }
        return actCajaTurno;
    }

    public ActCajaTurno saveActCajaTurno(ActCajaTurno actCajaTurno) throws Exception {
        
        List<ActCajaTurno> actCajaTurnoList = (List<ActCajaTurno>) actCajaTurnoRepository.getAllActCajaTurno(
                Constantes.COD_ESTADO_ACTIVO,listRoles());
        
        val actCajaTurnoListByCaja = actCajaTurnoList.stream()
                .filter(item -> item.getActCaja().getId() == actCajaTurno.getActCaja().getId())
                .collect(Collectors.toList());


        if (actCajaTurno.getId() == 0 && !actCajaTurnoListByCaja.isEmpty()){
            throw new Exception("La caja " + actCajaTurno.getActCaja().getNombre() + " ya se encuentra aperturada");
        }

        var actCajaTurnoList2 = actCajaTurnoList.stream()
                .filter(item -> item.getSegUsuario().getId() == actCajaTurno.getSegUsuario().getId())
                .collect(Collectors.toList());

        if (actCajaTurno.getId() == 0 && !actCajaTurnoList2.isEmpty()){
            throw new Exception("El usario ya cuenta con una caja aperturada.");
        }
        ActCajaTurno actCajaTurnoResult = actCajaTurnoRepository.save(actCajaTurno);
        return actCajaTurnoResult;
    }

    public List<ActCajaTurno> getAllActCajaTurno() {
        List<ActCajaTurno> actCajaTurnoList = (List<ActCajaTurno>) actCajaTurnoRepository.findAll();
        return actCajaTurnoList;
    }

    public List<ActCajaTurno> getAllActCajaTurnoBySegUsuario(long id) {
        List<ActCajaTurno> actCajaTurnoList = (List<ActCajaTurno>) actCajaTurnoRepository.findBySegUsuarioId(id,listRoles());
        return actCajaTurnoList;//.stream()
//                .filter(item -> listRoles().stream()
//                        .anyMatch(predicate -> predicate.getEmpresa().getId() == item.getActCaja().getCnfEmpresa().getId()))
//                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        ActCajaTurno actCajaTurno = null;
        Optional<ActCajaTurno> actCajaTurnoOptional = actCajaTurnoRepository.findById(id);

        if (actCajaTurnoOptional.isPresent()) {
            actCajaTurno = actCajaTurnoOptional.get();
            actCajaTurnoRepository.delete(actCajaTurno);
        }
    }
    @Override
    public List<ActCajaTurno> getReportActCajaTurno(ParamBean paramBean) {
        LocalDateTime localDateTime1 = paramBean.getFechaDesde().atTime(23, 59, 59);
        LocalDateTime localDateTime2 = paramBean.getFechaHasta().atTime(0, 0, 0);
        List<ActCajaTurno> actCajaOperacionList = (List<ActCajaTurno>) actCajaTurnoRepository.getReportActCajaTurno(paramBean,listRoles());

        return actCajaOperacionList;
    }

    @Override
    public byte[] getReportCierreCaja(long id) {
        val actCajaTurno = getActCajaTurno(id);
        val operaciones = actCajaOperacionService.getAllActCajaOperacionByActCajaTurno(id);
        try {
            PDFConfig configTicket = PDFConfig.builder()
                    .pageSize("ticket")
                    //.logoPath("D:/qrcode.png")
                    .fontSize(14)
                    .build();


            List<PDFContent> contentTicket = new ArrayList<>();
            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("Cierre de Caja")
                    .fontWeight("bold")
                    .alignment("center").build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text(" ")
                    .fontSize(10)
                    .alignment("left").build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("Usuario: "+actCajaTurno.getSegUsuario().getNombre())
                    .fontSize(10)
                    .alignment("left").build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("Caja: "+ actCajaTurno.getActCaja().getNombre())
                    .fontSize(10)
                    .alignment("left").build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("Fecha apertura: "+actCajaTurno.getFechaApertura())
                    .fontSize(10)
                    .alignment("left").build());


            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("Fecha cierre: "+actCajaTurno.getFechaCierre())
                    .fontSize(10)
                    .alignment("left").build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.SEPARATOR)
                            .isSeparator(true)
                    .build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("CONTADO")
                    .fontSize(12)
                    .fontWeight("bold")
                    .alignment("center").build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("APERTURA: " + Constantes.FORMAT_NUMBER.format(actCajaTurno.getMontoApertura()))
                    .fontSize(10)
                    .alignment("left").build());

            val operacionesComprobantes = operaciones.stream().filter(item -> item.getActComprobante() != null)
                    .filter(item -> item.getActComprobante().getListActMedioPagoDetalle() != null
                            && item.getActComprobante().getFlagIsventa().equals(Constantes.FLAG_IS_VENTA))
                    .collect(Collectors.toList());

            val operacionesComprobantes2 = operacionesComprobantes.stream()
                    .flatMap(item -> {
                        String medioPago = "Mixto";
                        List<MedioPagoDTO> listMedioPago = new ArrayList<>(); //MedioPagoDTO
                        return item.getActComprobante().getListActMedioPagoDetalle().stream().map(item2 -> {
                                return MedioPagoDTO.builder().nombre(item2.getCnfMedioPago().getNombre())
                                        .total(item2.getMonto()).build();
                            });

    //                    if (uniqueMedioPago != null && uniqueMedioPago.size() == 1)
    //                        medioPago = uniqueMedioPago.get(0).getCnfMedioPago().getNombre();
    //                        return MedioPagoDTO.builder().nombre(medioPago).build();
                    })
                    .collect(Collectors.groupingBy(item -> item.getNombre(),
                            Collectors.summingDouble(mapper -> mapper.getTotal().floatValue())))
                    .entrySet().stream()
                    .map(mapper -> {
                        return MedioPagoDTO.builder().nombre(mapper.getKey()).total(new BigDecimal(mapper.getValue()))
                                .build();

                    })
                    .sorted(Comparator.comparing(MedioPagoDTO::getNombre))
                            .collect(Collectors.toList());

            operacionesComprobantes2.forEach(item -> {
                contentTicket.add(PDFContent.builder()
                        .type(PDFContentType.TEXT)
                        .text(item.getNombre() + ": " + Constantes.FORMAT_NUMBER.format(item.getTotal()))
                        .fontSize(10)
                        .alignment("left").build());
            });


            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.SEPARATOR)
                    .isSeparator(true)
                    .build());


            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("PAGOS")
                    .fontWeight("bold")
                    .fontSize(12)
                    .alignment("center").build());

            val pagos = operaciones.stream().filter(item -> item.getActPago() != null)
                    .collect(Collectors.groupingBy(item -> item.getFlagIngreso().equals("1")?"INGRESO":"SALIDA",
                            Collectors.summingDouble(mapper -> mapper.getMonto().floatValue())))
                    .entrySet().stream()
                    .map(mapper -> {
                        return MedioPagoDTO.builder().nombre(mapper.getKey()).total(new BigDecimal(mapper.getValue()))
                                .build();

                    })
                    .sorted(Comparator.comparing(MedioPagoDTO::getNombre))
                    .collect(Collectors.toList());

            pagos.forEach(item -> {
                contentTicket.add(PDFContent.builder()
                        .type(PDFContentType.TEXT)
                        .text(item.getNombre() + ": " + Constantes.FORMAT_NUMBER.format(item.getTotal()))
                        .fontSize(10)
                        .alignment("left").build());
            });

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.SEPARATOR)
                    .isSeparator(true)
                    .build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("OTROS MOVIMIENTOS DE CAJA")
                    .fontSize(12)
                    .fontWeight("bold")
                    .alignment("center").build());

            val movCaja = operaciones.stream().filter(item -> item.getActComprobante() == null && item.getActPago() == null)
                    .collect(Collectors.groupingBy(item -> item.getFlagIngreso().equals("1")?"INGRESO":"SALIDA",
                            Collectors.summingDouble(mapper -> mapper.getMonto().floatValue())))
                    .entrySet().stream()
                    .map(mapper -> {
                        return MedioPagoDTO.builder().nombre(mapper.getKey()).total(new BigDecimal(mapper.getValue()))
                                .build();

                    })
                    .sorted(Comparator.comparing(MedioPagoDTO::getNombre))
                    .collect(Collectors.toList());

            movCaja.forEach(item -> {
                contentTicket.add(PDFContent.builder()
                        .type(PDFContentType.TEXT)
                        .text(item.getNombre() + ": " + Constantes.FORMAT_NUMBER.format(item.getTotal()))
                        .fontSize(10)
                        .alignment("left").build());
            });

//            contentTicket.add(PDFContent.builder()
//                    .type(PDFContentType.SEPARATOR)
//                    .isSeparator(true)
//                    .build());

            val comprasContad = operaciones.stream().filter(item -> item.getActComprobante() != null)
                    .filter(item -> item.getActComprobante().getFlagIsventa().equals(Constantes.FLAG_IS_COMPRA))
                    .filter(item -> item.getActComprobante().getCnfFormaPago().getTipo().equals(Constantes.COD_ESTADO_ACTIVO))//contado
                    .collect(Collectors.summingDouble(data -> data.getMonto().floatValue()));

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("COMPRAS: " + Constantes.FORMAT_NUMBER.format(comprasContad))
                    .fontSize(10)
                    .alignment("left").build());

                // Generar el ticket en un archivo PDF

            Double total = actCajaTurno.getMontoApertura().doubleValue();

            total = total + operacionesComprobantes2.stream()
                    .collect(Collectors.summingDouble(data -> data.getTotal().floatValue()));

            total = total + pagos.stream().collect(Collectors.summingDouble(
                    data -> (data.getNombre().equals("INGRESO")?1:-1)*data.getTotal().floatValue()));

            total = total + movCaja.stream().collect(Collectors.summingDouble(
                    data -> (data.getNombre().equals("INGRESO")?1:-1)*data.getTotal().floatValue()));

            total = total - comprasContad;

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.SEPARATOR)
                    .isSeparator(true)
                    .build());

            contentTicket.add(PDFContent.builder()
                    .type(PDFContentType.TEXT)
                    .text("TOTAL: " + Constantes.FORMAT_NUMBER.format(total))
                    .fontSize(14)
                    .fontWeight("bold")
                    .alignment("right").build());

//            FileOutputStream fos = new FileOutputStream("ticket.pdf");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PDFGenerator.generatePDF(output, contentTicket, configTicket);
            output.close();
            val stream = new ByteArrayInputStream(output.toByteArray());
            int n = stream.available();
            byte[] bytes = new byte[n];
            stream.read(bytes, 0, n);
            return bytes;
//            return operacionesComprobantes2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

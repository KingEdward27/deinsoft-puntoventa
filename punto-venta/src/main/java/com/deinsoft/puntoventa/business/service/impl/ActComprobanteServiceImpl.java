package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.GeneratedFile;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActComprobante;
import com.deinsoft.puntoventa.business.repository.ActComprobanteRepository;
import com.deinsoft.puntoventa.business.service.ActComprobanteService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.dto.ReporteContableDto;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.dto.TopProductosDto;
import com.deinsoft.puntoventa.business.exception.BusinessException;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;
import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.model.CnfProducto;
import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.model.InvMovAlmacen;
import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.repository.ActComprobanteDetalleRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoRepository;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import com.deinsoft.puntoventa.business.service.CnfImpuestoCondicionService;
import com.deinsoft.puntoventa.business.service.CnfLocalService;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;
import com.deinsoft.puntoventa.config.AppConfig;
import com.deinsoft.puntoventa.facturador.client.EnvioPSE2;
import com.deinsoft.puntoventa.facturador.client.RespuestaPSE;
import com.deinsoft.puntoventa.framework.util.Formatos;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.util.Constantes;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class ActComprobanteServiceImpl extends CommonServiceImpl<ActComprobante,Long, ActComprobanteRepository>
        implements ActComprobanteService {

    @Autowired
    ActComprobanteRepository actComprobanteRepository;

    @Autowired
    ActComprobanteDetalleRepository actComprobanteDetalleRepository;

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;

    @Autowired
    InvAlmacenProductoRepository invAlmacenProductoRepository;

    @Autowired
    InvAlmacenProductoService invAlmacenProductoService;

    @Autowired
    InvMovimientoProductoRepository invMovimientoProductoRepository;

    @Autowired
    CnfFormaPagoDetalleRepository cnfFormaPagoDetalleRepository;

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;

    @Autowired
    ActCajaTurnoRepository actCajaTurnoRepository;

    @Autowired
    ActCajaOperacionRepository actCajaOperacionRepository;

    @Autowired
    BusinessService businessService;

    @Autowired
    CnfImpuestoCondicionService cnfImpuestoCondicionService;

    @Autowired
    ActPagoRepository actPagoRepository;

    @Autowired
    CnfLocalService cnfLocalService;
    
    @Autowired
    AppConfig appConfig;

    static DateTimeFormatter YYYYMM_FORMATER = DateTimeFormatter.ofPattern("yyyyMM");
    static DateTimeFormatter YYYYMMDD_FORMATER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante) {
        SecurityFilterDto securityFilterDto = listRoles();
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.getAllActComprobante(
                actComprobante.getSerie().toUpperCase(), actComprobante.getNumero().toUpperCase(),
                actComprobante.getObservacion().toUpperCase(),
                actComprobante.getFlagEstado().toUpperCase(),
                actComprobante.getFlagIsventa().toUpperCase(),
                actComprobante.getEnvioPseFlag().toUpperCase(),
                actComprobante.getEnvioPseMensaje().toUpperCase(),
                actComprobante.getXmlhash().toUpperCase(),
                actComprobante.getCodigoqr().toUpperCase(),
                actComprobante.getNumTicket().toUpperCase(), securityFilterDto);
        return actComprobanteList;
    }

    public ActComprobante getActComprobante(Long id) {
        ActComprobante actComprobante = null;
        Optional<ActComprobante> actComprobanteOptional = actComprobanteRepository.findById(id);
        Set<ActComprobanteDetalle> list;
        if (actComprobanteOptional.isPresent()) {
            actComprobante = actComprobanteOptional.get();
            list = actComprobante.getListActComprobanteDetalle().stream().map(mapper -> {
                mapper.setCnfImpuestoCondicion(
                        cnfImpuestoCondicionService.getCnfImpuestoCondicion(mapper.getCnfImpuestoCondicion().getId()));
                return mapper;
            }).collect(Collectors.toSet());

            actComprobante.setListActComprobanteDetalle(list);

            SecurityFilterDto f = listRoles();
            if (f.getEmpresaId() != actComprobante.getCnfLocal().getCnfEmpresa().getId()) {
                throw new SecurityException(Constantes.MSG_NO_AUTHORIZED);
            }
        } else {
            throw new SecurityException(Constantes.MSG_NO_EXISTS_ITEM);
        }
        return actComprobante;
    }

    @Transactional
    @Override
    public ActComprobante saveActComprobante(ActComprobante actComprobante) throws Exception {

        businessService.verifyPlan(actComprobante, null);

        ActComprobante actComprobanteResult = null;

        actComprobante.setSegUsuario(auth.getLoggedUserdata());
        //tiene que obedecer un parametro de configuración caja turno, por ahora null
        List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(actComprobante.getSegUsuario().getId(), listRoles());
        actCajaTurno = actCajaTurno.stream()
                .filter(item -> item.getEstado().equals("1"))
                .collect(Collectors.toList());

        if (actCajaTurno.isEmpty()) {
            throw new Exception("El usuario no tiene caja aperturada");
        }
        try {

//            if (numComprobante.isEmpty()) {
//                throw new Exception("No existe numeración para el tipo de comprobante y el local");
//            }
//            if (numComprobante.isEmpty()) {
//                throw new Exception("No existe numeración para el tipo de comprobante y el local");
//            }
            if (actComprobante.getId() == 0) {

                List<CnfNumComprobante> numComprobante = cnfNumComprobanteRepository.findByCnfTipoComprobanteIdAndCnfLocalId(
                        actComprobante.getCnfTipoComprobante().getId(),
                        actComprobante.getCnfLocal().getId());
                if (numComprobante.isEmpty()) {
                    throw new Exception("No existe numeración para el tipo de comprobante y el local");
                }

                //update num comprobante
                CnfNumComprobante cnfNumComprobante = numComprobante.get(0);
                cnfNumComprobante.setUltimoNro(numComprobante.get(0).getUltimoNro() + 1);

                cnfNumComprobanteRepository.save(cnfNumComprobante);

                actComprobante.setFechaRegistro(LocalDateTime.now());
                actComprobante.setNumero(String.valueOf(numComprobante.get(0).getUltimoNro()));
            }

            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                actComprobante.addActComprobanteDetalle(data);
            });
            actComprobanteResult = actComprobanteRepository.save(actComprobante);
            if (actComprobante.getId() != 0) {
                if (actComprobanteResult.getCnfLocal().getCnfEmpresa().getFlagVentaRapida() == 1) {
                    validate(actComprobante.getId());

                    actComprobanteResult.setFlagEstado("2");
                    actComprobanteRepository.save(actComprobante);
                }
            }

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
            throw e;
        }

        return actComprobanteResult;
    }

    @Transactional
    @Override
    public ActComprobante saveActComprobanteCompra(ActComprobante actComprobante) throws Exception {
        ActComprobante actComprobanteResult = null;

        actComprobante.setSegUsuario(auth.getLoggedUserdata());

        List<ActComprobante> listExists = actComprobanteRepository.findByCnfEmpresaIdAndNumberCp(actComprobante);
        if (!listExists.isEmpty()) {
            throw new BusinessException("Ya existe un comprobante de compra con la misma numeración");
        }
        try {
            actComprobante.setFechaRegistro(LocalDateTime.now());
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                actComprobante.addActComprobanteDetalle(data);
            });
            actComprobanteResult = actComprobanteRepository.save(actComprobante);

            if (actComprobanteResult.getCnfLocal().getCnfEmpresa().getFlagCompraRapida() == 1) {
                validate(actComprobante.getId());

                actComprobanteResult.setFlagEstado("2");
                actComprobanteRepository.save(actComprobante);
            }

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
            throw e;
        }

        return actComprobanteResult;
    }

    @Override
    public void validate(long id) throws Exception {

        ActComprobante actComprobante = getActComprobante(id);
        //update stock
        invAlmacenProductoService.registerProductMovementAndUpdateStock(actComprobante, null);

        //save pagos programacion
        List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId
        (auth.getLoggedUserdata().getId(), listRoles());
        actCajaTurno = actCajaTurno.stream()
                .filter(item -> item.getEstado().equals("1"))
                .collect(Collectors.toList());
        if (actCajaTurno.isEmpty()) {
            throw new Exception("El usuario no tiene caja aperturada");
        }

        saveActPagoProgramacionOrCajaOperacion(actComprobante, actCajaTurno,
                actComprobante.getFlagIsventa().equals("1") ? true : false);

        actComprobante.setFlagEstado("2");
        ActComprobante actComprobanteDb = actComprobanteRepository.save(actComprobante);

        //send to sunat
        if (actComprobanteDb.getCnfTipoComprobante().getFlagElectronico().equals("1")
                && actComprobanteDb.getFlagIsventa().equals("1")) {
            RespuestaPSE respuestaPSE = sendApi(actComprobanteDb.getId());
        }

    }

    @Override
    public RespuestaPSE sendApi(long id) {
        RespuestaPSE result = null;

        ActComprobante actComprobante = getActComprobante(id);
//        Map<String,Object> local = (Map<String,Object>)mapVenta.get("cnf_local");
//        Map<String,Object> empresa = (Map<String,Object>)local.get("cnf_empresa");
        String ruta = actComprobante.getCnfLocal().getCnfEmpresa().getRutaPse();
        String token = actComprobante.getCnfLocal().getCnfEmpresa().getToken();
//        if (ruta == null) throw new RuntimeException ("Ruta PSE no configurado");
//        if (token == null) throw new RuntimeException ("token PSE no configurado");
        EnvioPSE2 envioPSE = new EnvioPSE2(ruta, token);
        String jsonBody = envioPSE.paramToJson(actComprobante);
        RespuestaPSE resultEnvioPSE = envioPSE.envioJsonPSE(jsonBody);

//        if (resultEnvioPSE.isResult()) {
        result = resultEnvioPSE;
//        }
        if (resultEnvioPSE.isResult()) {
            actComprobante.setEnvioPseFlag("2");
            actComprobante.setEnvioPseMensaje("Enviado a PSE");
            actComprobante.setNumTicket(resultEnvioPSE.getId());
            actComprobante.setCodigoqr(resultEnvioPSE.getCodigoQR());
            actComprobante.setXmlhash(resultEnvioPSE.getXmlHash());
//            map.put("envio_pse_flag", "1");
//            map.put("envio_pse_mensaje", "Recibido correctamente");
//            map.put("num_ticket", resultEnvioPSE.getId());
//            map.put("codigoqr", resultEnvioPSE.getCodigoQR());
//            map.put("xmlhash", resultEnvioPSE.getXmlHash());
        } else {
            actComprobante.setEnvioPseFlag("0");
            actComprobante.setEnvioPseMensaje(resultEnvioPSE.getErrCode() + "-" + resultEnvioPSE.getErrMessage());
//            map.put("envio_pse_flag", "0");
//            map.put("envio_pse_mensaje", resultEnvioPSE.getErrCode() + "-" + resultEnvioPSE.getErrMessage());
        }
//        JsonData json = new JsonData();
//        json.setTableName(tableName);
//        json.setFilters(map);
//        json.setId(id);
        save(actComprobante);
        return result;
    }

    public List<ActComprobante> getAllActComprobante() {
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.findAll();
        return actComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfMaestro(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfMaestroId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfFormaPago(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfFormaPagoId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfMoneda(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfMonedaId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfLocal(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfLocalId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByCnfTipoComprobante(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByCnfTipoComprobanteId(id);
        return ActComprobanteList;
    }

    public List<ActComprobante> getAllActComprobanteByInvAlmacen(long id) {
        List<ActComprobante> ActComprobanteList = (List<ActComprobante>) actComprobanteRepository.findByInvAlmacenId(id);
        return ActComprobanteList;
    }

    @Override
    public void delete(long id) {
        ActComprobante actComprobante = null;
        Optional<ActComprobante> actComprobanteOptional = actComprobanteRepository.findById(id);

        if (actComprobanteOptional.isPresent()) {
            actComprobante = actComprobanteOptional.get();
            actComprobanteRepository.delete(actComprobante);
        }
    }

    @Override
    public List<ActComprobante> getReportActComprobante(ParamBean paramBean) {
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.getReportActComprobante(paramBean, listRoles());
        return actComprobanteList;
    }

    @Transactional
    @Override
    public String invalidate(long id) throws Exception {
        String result = "";
        try {
            ActComprobante actInvoice = getActComprobante(id);
            if (actInvoice.getCnfLocal().getCnfEmpresa().getFlagVentaRapida() == 1) {
                if (!"2".equals(actInvoice.getFlagEstado())) {
                    result = "El documento no se encuentra en estado Validado";
                    throw new Exception(result);
                }
            } else {
                if (!"1".equals(actInvoice.getFlagEstado())) {
                    result = "El documento no se encuentra en estado Registrado";
                    throw new Exception(result);
                }
            }
            if (actInvoice.getEnvioPseFlag().equals("2")) {
                throw new Exception("El documento ya se encuentra enviado");
            }
            // clean in actOrder
            for (ActComprobanteDetalle item : actInvoice.getListActComprobanteDetalle()) {
//                List<ActOrderLine> listActOrderline = actOrderLineService.findByActInvoiceId(item.getId());
//                for (ActOrderLine actOrderLine : listActOrderline) {
//                    actOrderLine.setActInvoiceLine(null);
//                    actOrderLineService.save(actOrderLine);
//                }
                // update balance
                if (!item.getCnfProducto().getCnfUnidadMedida().getCodigoSunat().equals("ZZ")) {
                    InvAlmacenProducto invBalance
                            = invAlmacenProductoRepository.findByCnfProductoIdAndInvAlmacenId(
                                    item.getCnfProducto().getId(), actInvoice.getInvAlmacen().getId()).stream().findFirst().orElse(null);
                    if (invBalance != null) {
                        BigDecimal currentQty = invBalance.getCantidad();
                        if (actInvoice.getFlagIsventa().equals("1")) {
                            invBalance.setCantidad(currentQty.add(item.getCantidad()));
                        } else {
                            invBalance.setCantidad(currentQty.subtract(item.getCantidad()));
                        }
                        invAlmacenProductoRepository.save(invBalance);
                    }

                    invMovimientoProductoRepository.deleteByActComprobante(actInvoice);
                }

            }
            actInvoice.setFlagEstado("0");
            actInvoice.setTotal(BigDecimal.ZERO);
            // update cab
            this.save(actInvoice);
            // delete detail
//            actInvoiceLineService.deleteByActInvoice(actInvoice);

            // delete payment(remains validate)
            actPagoProgramacionRepository.deleteByActComprobante(actInvoice);

            // delete invoice tax
//            actInvoiceTaxService.deleteByActInvoice(actInvoice);
            // delete movement
//            if (actInvoice.getIsinventory()) {
//                invMovimientoProductoRepository.deleteByActInvoice(actInvoice);
//            }
            // enviar a ebi
//            if (actInvoice.getCnfDoctype().getIsebi()) {
//                result = "No se pudo enviar la anulacion de documento a EBI, porque no era un documento electronico";
//                String documentSerial = actInvoice.getDocumentserial();
//                String documentNro = String.format("%08d", actInvoice.getDocumentno());
//                String docType = actInvoice.getCnfDoctype().getValue();
//                ObjectNode objectNode = ebiService.ebiDeleteDocument(docType, documentSerial, documentNro);
//                result = objectNode.get(RESPONSE).asText();
//                if (result.toLowerCase().startsWith("ok")) {
//                    result = "";
//                }
//            }
//        } catch (GenericBusinessException e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            result = e.getMessage();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = e.getMessage();
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    private void saveActPagoProgramacionOrCajaOperacion(ActComprobante actComprobante,
            List<ActCajaTurno> actCajaTurno, boolean flagIngreso) throws Exception {
        BigDecimal pending = actComprobante.getTotal();
        LocalDate dueDate = actComprobante.getFecha();
        List<CnfFormaPagoDetalle> list = cnfFormaPagoDetalleRepository.findByCnfFormaPagoId(
                actComprobante.getCnfFormaPago().getId());
        if (actComprobante.getCnfFormaPago().getTipo() != 1 && list.size() == 0) {
            throw new BusinessException("Si el tipo de pago es diferente de CONTADO, debe registrar al menos una programación de forma de pago");
        }
        if (!list.isEmpty()) {

            if (actComprobante.getCnfFormaPago().getTipo() == 2) {
                for (CnfFormaPagoDetalle cnfFormaPagoDetalle : list) {
                    ActPagoProgramacion p = new ActPagoProgramacion();
                    p.setFecha(actComprobante.getFecha());
                    p.setActComprobante(actComprobante);

                    if (cnfFormaPagoDetalle.getModoDiaVencimiento() != null
                            && cnfFormaPagoDetalle.getModoDiaVencimiento() != 0 && cnfFormaPagoDetalle.getModoMonto() != null) {
                        p.setFechaVencimiento(actComprobante.getFecha()
                                .plusDays(cnfFormaPagoDetalle.getModoDiaVencimiento()));
                        p.setMonto(cnfFormaPagoDetalle.getModoMonto());
                        p.setMontoPendiente(cnfFormaPagoDetalle.getModoMonto());
                    } else if (cnfFormaPagoDetalle.getModoPorcentaje() != null
                            && cnfFormaPagoDetalle.getModoDiasIntervalo() != null) {
                        p.setFechaVencimiento(actComprobante.getFecha()
                                .plusDays(cnfFormaPagoDetalle.getModoDiasIntervalo()));
                        //actPayment.setTotalamt(actInvoice.getGrandtotal()
                        //				.multiply(cnfTendertypeSchedule.getPercent().divide(new BigDecimal(100))))
                        p.setMonto(actComprobante.getTotal().multiply(cnfFormaPagoDetalle.getModoPorcentaje().divide(new BigDecimal(100))));

                    } else if (cnfFormaPagoDetalle.getModoPorcentaje() != null
                            && cnfFormaPagoDetalle.getModoDiaVencimiento() != null) {

                        p.setFechaVencimiento(
                                actComprobante.getFecha().withDayOfMonth(actComprobante.getFecha().lengthOfMonth())
                                        .plusMonths(1));
                        p.setMonto(actComprobante.getTotal().multiply(cnfFormaPagoDetalle.getModoPorcentaje().divide(new BigDecimal(100))));

                    }  else {
                        if (pending.compareTo(cnfFormaPagoDetalle.getModoMonto()) == -1) {
                            p.setMonto(pending);
                        }
                        p.setMonto(cnfFormaPagoDetalle.getModoMonto());
                    }
                    pending = pending.subtract(p.getMonto());
                    p.setMontoPendiente(p.getMonto());
                    actPagoProgramacionRepository.save(p);
                }
                if (pending.compareTo(BigDecimal.ZERO) == 1) {
                    ActPagoProgramacion actPayment = new ActPagoProgramacion();
                    actPayment.setActComprobante(actComprobante);
                    actPayment.setFecha(actComprobante.getFecha());
                    actPayment.setFechaVencimiento(dueDate.plusDays(30));
                    actPayment.setMonto(pending);
                    actPayment.setMontoPendiente(actPayment.getMonto());
                    actPagoProgramacionRepository.save(actPayment);
                }
            } else if (actComprobante.getCnfFormaPago().getTipo() == 3) {
                if (list.size() > 1) {
                    throw new Exception("Forma de pago cíclica no debe tener mas de un registro");
                }
                for (int i = 0; i < 12; i++) {
                    ActPagoProgramacion actPayment = new ActPagoProgramacion();
                    actPayment.setActComprobante(actComprobante);
                    actPayment.setFecha(actComprobante.getFecha());
                    actPayment.setFechaVencimiento(
                            actComprobante.getFecha()
                                    .plusMonths(i));
                    if (actPayment.getFechaVencimiento().lengthOfMonth() < list.get(0).getModoDiaVencimiento()) {
                        actPayment.setFechaVencimiento(actPayment.getFechaVencimiento()
                                .withDayOfMonth(actPayment.getFechaVencimiento().lengthOfMonth()));
                    } else {
                        actPayment.setFechaVencimiento(
                                actPayment.getFechaVencimiento()
                                        .withDayOfMonth(list.get(0).getModoDiaVencimiento()));
                    }

                    actPayment.setMonto(list.get(0).getModoMonto());
                    actPayment.setMontoPendiente(list.get(0).getModoMonto());
                    actPagoProgramacionRepository.save(actPayment);
                }
            } else {
                throw new Exception("Tipo de forma de pago no configurada");
            }

        } else {
            ActCajaOperacion actCajaOperacion = new ActCajaOperacion();
            actCajaOperacion.setActCajaTurno(actCajaTurno == null ? null : actCajaTurno.get(0));
            actCajaOperacion.setActPago(null);
            actCajaOperacion.setActComprobante(actComprobante);
            actCajaOperacion.setFecha(actComprobante.getFecha());
            actCajaOperacion.setFechaRegistro(LocalDateTime.now());
            actCajaOperacion.setMonto(actComprobante.getTotal());
            actCajaOperacion.setFlagIngreso(flagIngreso ? "1" : "2");
            actCajaOperacion.setEstado("1");
            actCajaOperacionRepository.save(actCajaOperacion);
        }
    }

    @Override
    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception {
        byte[] bytes = businessService.printComprobante(appConfig.getStaticResourcesPath(), tipo, getActComprobante(id), false);
        return bytes;
    }

    @Override
    public GeneratedFile generateSireTxt(ParamBean paramBean) throws Exception {

        ParamBean paramWorked = paramBean;
        int lastDay = LocalDate.parse(paramWorked.getPeriodo() + "01", YYYYMMDD_FORMATER).lengthOfMonth();
        paramWorked.setFechaDesde(LocalDate.parse(paramWorked.getPeriodo() + "01", YYYYMMDD_FORMATER));
        paramWorked.setFechaHasta(LocalDate.parse(paramWorked.getPeriodo() + String.valueOf(lastDay), YYYYMMDD_FORMATER));
        paramWorked.setFlagEstado("2");
        List<String> x = getReportActComprobante(paramWorked)
                .stream()
                .filter(predicate -> predicate.getCnfTipoComprobante().getFlagElectronico().equals("1"))
                .map(mapper -> {
                    BigDecimal multiplicand = BigDecimal.ONE;
                    if (mapper.getCnfTipoComprobante().getCodigoSunat().equals(Constantes.TIPO_DOC_NOTA_CREDITO)) {
                        multiplicand = multiplicand.multiply(BigDecimal.valueOf(-1));
                    }

                    String line = mapper.getCnfLocal().getCnfEmpresa().getNroDocumento()
                            .concat("|")
                            .concat(mapper.getCnfLocal().getCnfEmpresa().getNombre())
                            .concat("|")
                            .concat(mapper.getFecha().format(Constantes.YYYYMM_FORMATER))
                            .concat("|")
                            .concat("|")
                            .concat(mapper.getFecha().format(Constantes.DDMMYYYY_FORMATER))
                            .concat("|")
                            .concat("|")
                            .concat(mapper.getCnfTipoComprobante().getCodigoSunat())
                            .concat("|")
                            .concat(mapper.getSerie())
                            .concat("|")
                            .concat("|")
                            .concat(String.format("%08d", Integer.parseInt(mapper.getNumero())))//9
                            .concat("|")
                            .concat("|")
                            .concat(mapper.getCnfMaestro().getCnfTipoDocumento().getCodigoSunat()).concat("|")
                            .concat(mapper.getCnfMaestro().getNroDoc()).concat("|")
                            .concat(mapper.getCnfMaestro().getRazonSocial()).concat("|");
                    line = line.concat(Formatos.df.format(mapper.getSubtotal().multiply(multiplicand))).concat("|")//15
                            //.concat("").concat("|")//DESCUENTO
                            .concat(Formatos.df.format(mapper.getIgv().multiply(multiplicand))).concat("|");
                    //.concat("").concat("|");//DESCUENTO
                    line = line.concat("0.00").concat("|")//exonerada
                            .concat("0.00").concat("|")//inafecta
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|")
                            .concat("0.00").concat("|");
                    line = line.concat(Formatos.df.format(mapper.getTotal().multiply(multiplicand))).concat("|")
                            .concat(mapper.getCnfMoneda().getCodigo()).concat("|")
                            .concat("|")//tipo cambio

                            .concat("|")//fecha emision comprobante modificado
                            .concat("|")//tipo comprobante modificado
                            .concat("|")//serie comprobante modificado
                            .concat("|")//numero comprobante modificado
                            .concat("|")
                            .concat("|").concat("\n");
                    return line;
                }).collect(Collectors.toList());
        if (x.isEmpty()) {
            throw new Exception("No hay datos para el periodo");
        }
        String joined = x.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
//        String fileName = "temp";
        String fileName = "LE"
                .concat(paramBean.getCnfLocal().getCnfEmpresa().getNroDocumento())
                .concat(paramBean.getFechaDesde().format(Constantes.YYYYMM_FORMATER))
                .concat("00")
                .concat(paramWorked.getFlagIsventa().equals("1") ? "140100" : "080400")//codigo libro
                .concat("02")//Código de oportunidad de presentación del RVIE/RCE: -> RCE Cuando realiza ajustes posteriores
                .concat("1")//Indicador de operaciones -> 0 Cierre o baja de RUC  / 1 Empresa operativa  / 2 Cierre de libro
                .concat("1")//Indicador del contenido del libro o registro -> 1 Con información / 0 Sin información
                .concat("1")//Indicador de la moneda utilizada -> 1 Soles  / 2 Dólares
                .concat("2")//Indicador de libro electrónico generado por el MIGE IGV/RVIE -> Generado por el SIRE ( Fijo) (2)
                //.concat("1")//Correlativo de los ajustes posteriores -> Puede ser 01, 02, 03, etc. Según el correlativo que corresponde.
                .concat(".txt");
        byte[] txtData = Util.generateFile(fileName, joined);
        byte[] zippedData = Util.comprimirArchivo(new ByteArrayInputStream(txtData), fileName);
//        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\user\\Documents\\test.zip")) {
//            fos.write(zippedData);
//        }
        ByteArrayInputStream stream = new ByteArrayInputStream(zippedData);
        InputStreamResource inputStreamResource = new InputStreamResource(stream);
        return new GeneratedFile(fileName, zippedData);
    }

    public List<ReporteContableDto> getListaReporteContable(Long cnfLocalId) {
        if (cnfLocalId == 0) {
            throw new RuntimeException("Dbe ingresar el local");
        }
        List<ActComprobante> list = actComprobanteRepository.findByCnfLocalId(cnfLocalId);
        List<ReporteContableDto> x = list.stream()
                .filter(predicate -> predicate.getCnfTipoComprobante().getFlagElectronico().equals("1"))
                .collect(
                        Collectors.groupingBy(item -> grouping(item),
                                Collectors.summingDouble(mapper -> mapper.getTotal().floatValue())))
                .entrySet().stream()
                .map(mapper -> {
                    return new ReporteContableDto(mapper.getKey().split("-")[0],
                            mapper.getValue(), mapper.getKey().split("-")[1], mapper.getKey().split("-")[2]);
                })
                .sorted(Comparator.comparing(ReporteContableDto::getPeriodo, Collections.reverseOrder()))
                .collect(Collectors.toList());

        return x;
    }

    @Override
    public Map<String, Object> getDashboardActComprobantes(ParamBean param) {

        param.setFlagIsventa("1");
        param.setFlagEstado("2");
        List<ActComprobante> listVentas = getReportActComprobante(param);
        Double sumVentas = listVentas.stream().mapToDouble(o -> o.getTotal().doubleValue()).sum();

        List<Long> listClientes = listVentas.stream().map(action -> {
            return action.getCnfMaestro().getId();
        }).distinct().collect(Collectors.toList());

        String moneda = "";
        if (!listVentas.isEmpty()) {
            moneda = listVentas.get(0).getCnfLocal().getCnfEmpresa().getCnfMoneda().getSimbolo();
        }

        List<ActComprobanteDetalle> listActComprobanteDetalle = new ArrayList<>();
        for (ActComprobante listVenta : listVentas) {
            listActComprobanteDetalle.addAll(listVenta.getListActComprobanteDetalle());
        }
        List<TopProductosDto> listTopProducts = listActComprobanteDetalle.stream()
                .collect(
                        Collectors.groupingBy(item -> grouping(item),
                                Collectors.summingDouble(mapper -> mapper.getImporte().floatValue())))
                .entrySet().stream()
                .map(mapper -> {
                    return new TopProductosDto(mapper.getKey(),
                            mapper.getValue());
                })
                .sorted(Comparator.comparing(TopProductosDto::getPrecio, Collections.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());

        List<TopProductosDto> listTopClients = listVentas.stream()
                .collect(
                        Collectors.groupingBy(item -> item.getCnfMaestro().getRazonSocial(),
                                Collectors.summingDouble(mapper -> mapper.getTotal().floatValue())))
                .entrySet().stream()
                .map(mapper -> {
                    return new TopProductosDto(mapper.getKey(),
                            mapper.getValue());
                })
                .sorted(Comparator.comparing(TopProductosDto::getPrecio, Collections.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());

        param.setFlagIsventa("2");
        List<ActComprobante> listCompras = getReportActComprobante(param);
        Double sumCompras = listCompras.stream().mapToDouble(o -> o.getTotal().doubleValue()).sum();

        Double totalGanancia = listVentas.stream().map(mapper -> {
            return mapper.getListActComprobanteDetalle().stream()
                    .mapToDouble(o -> (o.getPrecio()
                    .subtract(o.getCnfProducto().getCosto() == null ? BigDecimal.ZERO : o.getCnfProducto().getCosto())).doubleValue())
                    .sum();
        }).mapToDouble(o -> o).sum();
        Map<String, Object> map = new HashMap<>();
        map.put("totalClientes", listClientes.size());
        map.put("totalVentas", listVentas.size());
        map.put("totalMontoVentas", sumVentas);
        map.put("totalCompras", listCompras.size());
        map.put("totalMontoCompras", sumCompras);
        map.put("totalCaja", totalGanancia);
        map.put("moneda", moneda);
        map.put("listTopProducts", listTopProducts);
        map.put("listTopClients", listTopClients);
        return map;
    }

    @Override
    public ActComprobante getByCnfEmpresaIdAndNumberCp(long localId, String serie, String numero) {
        ActComprobante actComprobante = new ActComprobante();
        actComprobante.setCnfLocal(cnfLocalService.getCnfLocal(localId));
        actComprobante.setSerie(serie);
        actComprobante.setNumero(numero);
        actComprobante.setFlagIsventa("1");
        List<ActComprobante> listExists = actComprobanteRepository.findByCnfEmpresaIdAndNumberCp(actComprobante);
        
        if (listExists.isEmpty()) {
            throw new BusinessException("comprobante no encontrado");
        }
        
        if (listExists.size() > 1) {
            throw new BusinessException("La búsqueda resultó mas de un comprobante, consultar con ADMIN TI");
        }
        
        return listExists.stream().findFirst().orElse(null);
    }
    
    String grouping(ActComprobante item) {
        String fileNameCompras = "LE"
                .concat(item.getCnfLocal().getCnfEmpresa().getNroDocumento())
                .concat(item.getFecha().format(Constantes.YYYYMM_FORMATER))
                .concat("00")
                .concat("080400")//codigo libro
                .concat("02")//Código de oportunidad de presentación del RVIE/RCE: -> RCE Cuando realiza ajustes posteriores
                .concat("1")//Indicador de operaciones -> 0 Cierre o baja de RUC  / 1 Empresa operativa  / 2 Cierre de libro
                .concat("1")//Indicador del contenido del libro o registro -> 1 Con información / 0 Sin información
                .concat("1")//Indicador de la moneda utilizada -> 1 Soles  / 2 Dólares
                .concat("2")//Indicador de libro electrónico generado por el MIGE IGV/RVIE -> Generado por el SIRE ( Fijo) (2)
                //.concat("1")//Correlativo de los ajustes posteriores -> Puede ser 01, 02, 03, etc. Según el correlativo que corresponde.
                .concat(".zip");
        String fileNameVentas = "LE"
                .concat(item.getCnfLocal().getCnfEmpresa().getNroDocumento())
                .concat(item.getFecha().format(Constantes.YYYYMM_FORMATER))
                .concat("00")
                .concat("140100")//codigo libro
                .concat("02")//Código de oportunidad de presentación del RVIE/RCE: -> RCE Cuando realiza ajustes posteriores
                .concat("1")//Indicador de operaciones -> 0 Cierre o baja de RUC  / 1 Empresa operativa  / 2 Cierre de libro
                .concat("1")//Indicador del contenido del libro o registro -> 1 Con información / 0 Sin información
                .concat("1")//Indicador de la moneda utilizada -> 1 Soles  / 2 Dólares
                .concat("2")//Indicador de libro electrónico generado por el MIGE IGV/RVIE -> Generado por el SIRE ( Fijo) (2)
                //.concat("1")//Correlativo de los ajustes posteriores -> Puede ser 01, 02, 03, etc. Según el correlativo que corresponde.
                .concat(".zip");
        return item.getFecha().format(YYYYMM_FORMATER) + "-" + fileNameCompras + "-" + fileNameVentas;
    }

    String grouping(ActComprobanteDetalle item) {
        return item.getCnfProducto().getNombre();
    }

}

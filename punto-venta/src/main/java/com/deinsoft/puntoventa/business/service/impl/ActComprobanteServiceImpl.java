package com.deinsoft.puntoventa.business.service.impl;

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
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;
import com.deinsoft.puntoventa.business.model.InvMovAlmacen;
import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.repository.ActComprobanteDetalleRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.repository.InvAlmacenProductoRepository;
import com.deinsoft.puntoventa.business.repository.InvMovimientoProductoRepository;
import com.deinsoft.puntoventa.business.service.InvAlmacenProductoService;
import com.deinsoft.puntoventa.config.AppConfig;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional
public class ActComprobanteServiceImpl extends CommonServiceImpl<ActComprobante, ActComprobanteRepository>
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
    AuthenticationHelper auth;

    @Autowired
    AppConfig appConfig;

    public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante) {
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.getAllActComprobante(
                actComprobante.getSerie().toUpperCase(), actComprobante.getNumero().toUpperCase(),
                actComprobante.getObservacion().toUpperCase(),
                actComprobante.getFlagEstado().toUpperCase(),
                actComprobante.getFlagIsventa().toUpperCase(),
                actComprobante.getEnvioPseFlag().toUpperCase(),
                actComprobante.getEnvioPseMensaje().toUpperCase(),
                actComprobante.getXmlhash().toUpperCase(),
                actComprobante.getCodigoqr().toUpperCase(),
                actComprobante.getNumTicket().toUpperCase());
        return actComprobanteList;
    }

    public ActComprobante getActComprobante(Long id) {
        ActComprobante actComprobante = null;
        Optional<ActComprobante> actComprobanteOptional = actComprobanteRepository.findById(id);
        if (actComprobanteOptional.isPresent()) {
            actComprobante = actComprobanteOptional.get();
        }
        return actComprobante;
    }

    @Transactional
    @Override
    public ActComprobante saveActComprobante(ActComprobante actComprobante) throws Exception {
        ActComprobante actComprobanteResult = null;

        //tiene que obedecer un parametro de configuración caja turno, por ahora null
        List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(actComprobante.getSegUsuario().getId());
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
                actComprobante.setNumero(String.valueOf(numComprobante.get(0).getUltimoNro() + 1));
            }

            actComprobanteResult = actComprobanteRepository.save(actComprobante);
            actComprobante.getListActComprobanteDetalle().forEach(data -> {
                actComprobante.addActComprobanteDetalle(data);
            });

            if (actComprobante.getId() == 0) {
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
        List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(auth.getLoggedUserdata().getId());
        actCajaTurno = actCajaTurno.stream()
                .filter(item -> item.getEstado().equals("1"))
                .collect(Collectors.toList());
        if (actCajaTurno.isEmpty()) {
            throw new Exception("El usuario no tiene caja aperturada");
        }

        saveActPagoProgramacionOrCajaOperacion(actComprobante, actCajaTurno, actComprobante.getFlagIsventa().equals("1") ? false : true);

        actComprobante.setFlagEstado("2");
        actComprobanteRepository.save(actComprobante);
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
        List<ActComprobante> actComprobanteList = (List<ActComprobante>) actComprobanteRepository.getReportActComprobante(paramBean);
        return actComprobanteList;
    }

    @Transactional
    @Override
    public String invalidate(long id) throws Exception {
        String result = "";
        try {
            ActComprobante actInvoice = getActComprobante(id);
            if (!"1".equals(actInvoice.getFlagEstado())) {
                result = "El documento no se encuentra en estado Registrado";
                throw new Exception(result);
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
                                    item.getCnfProducto().getId(), actInvoice.getInvAlmacen().getId());
                    BigDecimal currentQty = invBalance.getCantidad();
                    if (actInvoice.getFlagIsventa().equals("1")) {
                        invBalance.setCantidad(currentQty.add(item.getCantidad()));
                    } else {
                        invBalance.setCantidad(currentQty.subtract(item.getCantidad()));
                    }
                    invAlmacenProductoRepository.save(invBalance);

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
        if (!list.isEmpty()) {
            if (actComprobante.getCnfFormaPago().getTipo() == 1) {
                for (CnfFormaPagoDetalle cnfFormaPagoDetalle : list) {
                    ActPagoProgramacion p = new ActPagoProgramacion();
                    p.setFecha(actComprobante.getFecha());
                    p.setActComprobante(actComprobante);

                    if (cnfFormaPagoDetalle.getModoDiaVencimiento() != null
                            && cnfFormaPagoDetalle.getModoDiaVencimiento() != 0) {
                        p.setFechaVencimiento(actComprobante.getFecha()
                                .plusDays(cnfFormaPagoDetalle.getModoDiaVencimiento()));
                        p.setMonto(cnfFormaPagoDetalle.getModoMonto());
                        p.setMontoPendiente(cnfFormaPagoDetalle.getModoMonto());
                    } else if (cnfFormaPagoDetalle.getModoPorcentaje() != null
                            && cnfFormaPagoDetalle.getModoPorcentaje() != null) {
                        p.setFechaVencimiento(actComprobante.getFecha()
                                .plusDays(cnfFormaPagoDetalle.getModoDiasIntervalo()));
                        //actPayment.setTotalamt(actInvoice.getGrandtotal()
                        //				.multiply(cnfTendertypeSchedule.getPercent().divide(new BigDecimal(100))))
                        p.setMonto(actComprobante.getTotal().multiply(cnfFormaPagoDetalle.getModoPorcentaje().divide(new BigDecimal(100))));

                    } else {
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
            } else if (actComprobante.getCnfFormaPago().getTipo() == 2) {
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
            actCajaOperacion.setFecha(LocalDate.now());
            actCajaOperacion.setFechaRegistro(LocalDateTime.now());
            actCajaOperacion.setMonto(actComprobante.getTotal());
            actCajaOperacion.setFlagIngreso(flagIngreso ? "1" : "2");
            actCajaOperacion.setEstado("1");
            actCajaOperacionRepository.save(actCajaOperacion);
        }
    }

    @Override
    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception {
        byte[] bytes = businessService.print2(appConfig.getStaticResourcesPath(), tipo, getActComprobante(id), false);
        return bytes;
    }
}

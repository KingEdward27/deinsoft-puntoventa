package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.model.ActComprobante;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActContratoRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoRepository;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.service.ActContratoService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActContratoServiceImpl extends CommonServiceImpl<ActContrato, ActContratoRepository> implements ActContratoService {

    @Autowired
    ActContratoRepository actContratoRepository;

    @Autowired
    ActCajaOperacionRepository actCajaOperacionRepository;

    @Autowired
    CnfFormaPagoDetalleRepository cnfFormaPagoDetalleRepository;

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;

    @Autowired
    ActPagoRepository actPagoRepository;

    public List<ActContrato> getAllActContrato(ActContrato actContrato) {
        List<ActContrato> actContratoList = (List<ActContrato>) actContratoRepository.getAllActContrato(
                actContrato.getCnfEmpresaId(),
                actContrato.getSerie().toUpperCase(),
                actContrato.getNumero().toUpperCase(),
                actContrato.getObservacion().toUpperCase(),
                actContrato.getFlagEstado().toUpperCase(),
                actContrato.getNroPoste().toUpperCase(),
                actContrato.getUrlMap().toUpperCase(),
                actContrato.getDireccion().toUpperCase());
        return actContratoList;
    }

    public ActContrato getActContrato(Long id) {
        ActContrato actContrato = null;
        Optional<ActContrato> actContratoOptional = actContratoRepository.findById(id);
        if (actContratoOptional.isPresent()) {
            actContrato = actContratoOptional.get();
        }
        return actContrato;
    }

    @Transactional
    public ActContrato saveActContrato(ActContrato actContrato) throws Exception {
        long id = actContrato.getId();
        if (id == 0) {
            actContrato.setFechaRegistro(LocalDateTime.now());
        }
        if (actContrato.getCnfFormaPago() != null && actContrato.getCnfFormaPago().getId() == 0) {
            actContrato.setCnfFormaPago(null);
        }
        if (id == 0) {
            List<CnfNumComprobante> numComprobante = cnfNumComprobanteRepository.findByCnfTipoComprobanteCodigoAndCnfLocalId(
                    "CNT",
                    actContrato.getCnfLocal().getId());
            if (numComprobante.isEmpty()) {
                throw new Exception("No existe numeración para el tipo de comprobante y el local");
            }
            List<ActContrato> listContratos = actContratoRepository.findByCnfMaestroIdAndCnfPlanContratoId(actContrato.getCnfMaestro().getId(), actContrato.getCnfPlanContrato().getId());
            if (!listContratos.isEmpty()) {
                throw new Exception("Ya existe un registro con el mismo plan y mismo cliente");
            }
            CnfNumComprobante cnfNumComprobante = numComprobante.get(0);
            cnfNumComprobante.setUltimoNro(numComprobante.get(0).getUltimoNro() + 1);
            cnfNumComprobanteRepository.save(cnfNumComprobante);

            actContrato.setNumero(String.valueOf(cnfNumComprobante.getUltimoNro()));
        }

        ActContrato actContratoResult = actContratoRepository.save(actContrato);

        if (id == 0) {
            if ((actContrato.getCnfFormaPago() != null && actContrato.getCnfFormaPago().getId() > 0)) {
                saveActPagoProgramacionOrCajaOperacion(actContratoResult);
            } else {
                saveActPagoProgramacionMensualRepetitivo(actContratoResult);
            }
        }

        return actContratoResult;
    }

    public List<ActContrato> getAllActContrato() {
        List<ActContrato> actContratoList = (List<ActContrato>) actContratoRepository.findAll();
        return actContratoList;
    }

    public List<ActContrato> getAllActContratoByCnfMaestro(long id) {
        List<ActContrato> ActContratoList = (List<ActContrato>) actContratoRepository.findByCnfMaestroId(id);
        return ActContratoList;
    }

    public List<ActContrato> getAllActContratoByCnfLocal(long id) {
        List<ActContrato> ActContratoList = (List<ActContrato>) actContratoRepository.findByCnfLocalId(id);
        return ActContratoList;
    }

    public List<ActContrato> getAllActContratoByCnfTipoComprobante(long id) {
        List<ActContrato> ActContratoList = (List<ActContrato>) actContratoRepository.findByCnfTipoComprobanteId(id);
        return ActContratoList;
    }

    public List<ActContrato> getAllActContratoByCnfFormaPago(long id) {
        List<ActContrato> ActContratoList = (List<ActContrato>) actContratoRepository.findByCnfFormaPagoId(id);
        return ActContratoList;
    }

    public List<ActContrato> getAllActContratoByCnfPlanContrato(long id) {
        List<ActContrato> ActContratoList = (List<ActContrato>) actContratoRepository.findByCnfPlanContratoId(id);
        return ActContratoList;
    }

    @Override
    public void delete(long id) {
        ActContrato actContrato = null;
        Optional<ActContrato> actContratoOptional = actContratoRepository.findById(id);

        if (actContratoOptional.isPresent()) {

            actContrato = actContratoOptional.get();

            // delete payment(remains validate)
            actPagoProgramacionRepository.deleteByActContrato(actContrato);

            actContratoRepository.delete(actContrato);
        }
    }

    private void saveActPagoProgramacionMensualRepetitivo(ActContrato actContrato) throws Exception {
        LocalDate dueDate = actContrato.getFecha();
        ActPagoProgramacion actPayment = new ActPagoProgramacion();
        actPayment.setActContrato(actContrato);
        actPayment.setFecha(actContrato.getFecha());

        if (actContrato.getCnfPlanContrato().getDiaVencimiento() == null) {
            throw new Exception("No se encontró fecha de vencimiento para el plan seleccionado");
        }
        if (actContrato.getFecha().lengthOfMonth() < actContrato.getCnfPlanContrato().getDiaVencimiento()) {
            actPayment.setFechaVencimiento(actContrato.getFecha()
                    .withDayOfMonth(actPayment.getFecha().lengthOfMonth()));
        } else {
            actPayment.setFechaVencimiento(
                    actContrato.getFecha()
                            .withDayOfMonth(actContrato.getCnfPlanContrato().getDiaVencimiento()));
        }

        actPayment.setMonto(actContrato.getMontoPrimerMes());
        actPayment.setMontoPendiente(actContrato.getMontoPrimerMes());
        actPagoProgramacionRepository.save(actPayment);

        for (int i = 1; i < 12; i++) {
            actPayment = new ActPagoProgramacion();
            actPayment.setActContrato(actContrato);
            actPayment.setFecha(actContrato.getFecha());
            actPayment.setFechaVencimiento(
                    actContrato.getFecha()
                            .plusMonths(i));
            if (actContrato.getCnfPlanContrato().getDiaVencimiento() == null) {
                throw new Exception("No se encontró fecha de vencimiento para el plan seleccionado");
            }
            if (actPayment.getFechaVencimiento().lengthOfMonth() < actContrato.getCnfPlanContrato().getDiaVencimiento()) {
                actPayment.setFechaVencimiento(actPayment.getFechaVencimiento()
                        .withDayOfMonth(actPayment.getFechaVencimiento().lengthOfMonth()));
            } else {
                actPayment.setFechaVencimiento(
                        actPayment.getFechaVencimiento()
                                .withDayOfMonth(actContrato.getCnfPlanContrato().getDiaVencimiento()));
            }

            actPayment.setMonto(actContrato.getCnfPlanContrato().getPrecio());
            actPayment.setMontoPendiente(actContrato.getCnfPlanContrato().getPrecio());
            actPagoProgramacionRepository.save(actPayment);
        }
    }

    private void saveActPagoProgramacionOrCajaOperacion(ActContrato actContrato) throws Exception {
//        BigDecimal pending = actContrato.getTotal();
        LocalDate dueDate = actContrato.getFecha();
//        List<CnfFormaPagoDetalle> list = cnfFormaPagoDetalleRepository.findByCnfFormaPagoId(
//                actContrato.getCnfFormaPago().getId());
//        if (!list.isEmpty()) {
        if (actContrato.getCnfFormaPago().getTipo() == 1) {
//                for (CnfFormaPagoDetalle cnfFormaPagoDetalle : list) {
//                    ActPagoProgramacion p = new ActPagoProgramacion();
//                    p.setActContrato(actContrato);
//
//                    if (cnfFormaPagoDetalle.getModoDiaVencimiento() != null
//                            && cnfFormaPagoDetalle.getModoDiaVencimiento() != 0) {
//                        p.setFecha(actContrato.getFecha());
//                        p.setFechaVencimiento(actContrato.getFecha()
//                                .plusDays(cnfFormaPagoDetalle.getModoDiaVencimiento()));
//                        p.setMonto(cnfFormaPagoDetalle.getModoMonto());
//                        p.setMontoPendiente(cnfFormaPagoDetalle.getModoMonto());
//                    } else if (cnfFormaPagoDetalle.getModoPorcentaje() != null
//                            && cnfFormaPagoDetalle.getModoPorcentaje() != null) {
//                        p.setFechaVencimiento(actContrato.getFecha()
//                                .plusDays(cnfFormaPagoDetalle.getModoDiasIntervalo()));
//                        //actPayment.setTotalamt(actInvoice.getGrandtotal()
//                        //				.multiply(cnfTendertypeSchedule.getPercent().divide(new BigDecimal(100))))
//                        p.setMonto(actContrato.getTotal().multiply(cnfFormaPagoDetalle.getModoPorcentaje().divide(new BigDecimal(100))));
//
//                    } else {
//                        if (pending.compareTo(cnfFormaPagoDetalle.getModoMonto()) == -1) {
//                            p.setMonto(pending);
//                        }
//                        p.setMonto(cnfFormaPagoDetalle.getModoMonto());
//                    }
//                    pending = pending.subtract(p.getMonto());
//                    p.setMontoPendiente(p.getMonto());
//                    actPagoProgramacionRepository.save(p);
//                }
//                if (pending.compareTo(BigDecimal.ZERO) == 1) {
//                    ActPagoProgramacion actPayment = new ActPagoProgramacion();
//                    actPayment.setActContrato(actContrato);
//                    actPayment.setFecha(actContrato.getFecha());
//                    actPayment.setFechaVencimiento(dueDate.plusDays(30));
//                    actPayment.setMonto(pending);
//                    actPayment.setMontoPendiente(actPayment.getMonto());
//                    actPagoProgramacionRepository.save(actPayment);
//                }
        } else if (actContrato.getCnfFormaPago().getTipo() == 2) {
//                if (list.size() > 1) {
//                    throw new Exception("Forma de pago cíclica no debe tener mas de un registro");
//                }
            for (int i = 0; i < 12; i++) {
                ActPagoProgramacion actPayment = new ActPagoProgramacion();
                actPayment.setActContrato(actContrato);
                actPayment.setFecha(actContrato.getFecha());
                actPayment.setFechaVencimiento(
                        actContrato.getFecha()
                                .plusMonths(i));
                if (actContrato.getCnfPlanContrato().getDiaVencimiento() == null) {
                    throw new Exception("No se encontró fecha de vencimiento para el plan seleccionado");
                }
                if (actPayment.getFechaVencimiento().lengthOfMonth() < actContrato.getCnfPlanContrato().getDiaVencimiento()) {
                    actPayment.setFechaVencimiento(actPayment.getFechaVencimiento()
                            .withDayOfMonth(actPayment.getFechaVencimiento().lengthOfMonth()));
                } else {
                    actPayment.setFechaVencimiento(
                            actPayment.getFechaVencimiento()
                                    .withDayOfMonth(actContrato.getCnfPlanContrato().getDiaVencimiento()));
                }

                actPayment.setMonto(actContrato.getCnfPlanContrato().getPrecio());
                actPayment.setMontoPendiente(actContrato.getCnfPlanContrato().getPrecio());
                actPagoProgramacionRepository.save(actPayment);
            }
        } else {
            throw new Exception("Tipo de forma de pago no configurada");
        }

//        } 
//        else {
//            ActCajaOperacion actCajaOperacion = new ActCajaOperacion();
//            actCajaOperacion.setActCajaTurno(actCajaTurno == null ? null : actCajaTurno.get(0));
//            actCajaOperacion.setActPago(null);
//            actCajaOperacion.setActComprobante(actContrato);
//            actCajaOperacion.setFecha(LocalDate.now());
//            actCajaOperacion.setFechaRegistro(LocalDateTime.now());
//            actCajaOperacion.setMonto(actContrato.getTotal());
//            actCajaOperacion.setFlagIngreso(flagIngreso?"1":"2");
//            actCajaOperacion.setEstado("1");
//            actCajaOperacionRepository.save(actCajaOperacion);
//        }
    }

    @Override
    public List<ActContrato> getReportActContratos(ParamBean paramBean) {
        List<ActContrato> actContratoList = (List<ActContrato>) actContratoRepository.getReportActContrato(paramBean);

        List<ActPagoProgramacion> actPagoProgramacionList = actPagoProgramacionRepository.findAll();

        List<ActPago> actPagoList = actPagoRepository.findAll();

        return actContratoList.stream()
                .filter(predicate -> {
                    if (paramBean.getCnfLocal().getId() == 0) {
                        return true;
                    } else {
                        return predicate.getCnfLocal().getId() == paramBean.getCnfLocal().getId();

                    }
                })
                .filter(predicate -> {
                    if (paramBean.getCnfMaestro().getId() == 0) {
                        return true;
                    } else {
                        return predicate.getCnfMaestro().getId() == paramBean.getCnfMaestro().getId();
                    }
                })
                .map(mapper -> {
                    if (mapper.getMontoPendiente() == null) {
                        mapper.setMontoPendiente(BigDecimal.ZERO);
                    }
                    List<ActPagoProgramacion> actPagoProgramacionList2
                            = actPagoProgramacionList.stream()
                                    .filter(data -> data.getActContrato() != null && data.getActContrato().getId() == mapper.getId())
                                    .collect(Collectors.toList());

                    actPagoProgramacionList2.forEach(action -> {
                        if ((action.getMontoPendiente().compareTo(BigDecimal.ZERO) == 0)) {

                            ActPago actPago
                                    = actPagoList.stream()
                                            .filter(data -> data.getListActPagoDetalle().stream()
                                            .anyMatch(predicate -> predicate.getActPagoProgramacion() != null
                                            && predicate.getActPagoProgramacion().getId() == action.getId()))
                                            .findFirst().orElse(new ActPago());

                            mapper.setUltimoPago(actPago.getSerie() + "-" + actPago.getNumero() + " (" + actPago.getFecha().format(DateTimeFormatter.ISO_DATE) + "): " + actPago.getTotal());
                        } else {
                            if (action.getFechaVencimiento().compareTo(paramBean.getFechaVencimiento()) <= 0) {

                                mapper.setMontoPendiente(mapper.getMontoPendiente().add(action.getMontoPendiente()));
                            }
                        }
                    });
                    return mapper;
//                            .map(mapper2 -> {
//                                if ((mapper2.getMontoPendiente().compareTo(BigDecimal.ZERO) == 0)) {
//                                    mapper.setUltimoPago("xx");
//                                } else {
//                                    if (mapper2.getFechaVencimiento().compareTo(paramBean.getFechaVencimiento()) <= 0) {
//
//                                        mapper.setMontoPendiente(mapper.getMontoPendiente().add(mapper2.getMontoPendiente()));
//                                    }
//                                }
//                                return mapper2;
//                            });
//                    actPagoProgramacionList.forEach(action -> {
                    //                        if (action.getActContrato() != null && action.getActContrato().getId() == mapper.getId()) {
                    //                            if ((action.getMontoPendiente().compareTo(BigDecimal.ZERO) == 0)) {
                    //                                mapper.setUltimoPago("xx");
                    //                            } else {
                    //                                if (action.getFechaVencimiento().compareTo(paramBean.getFechaVencimiento()) <= 0) {
                    //
                    //                                    mapper.setMontoPendiente(mapper.getMontoPendiente().add(action.getMontoPendiente()));
                    //                                }
                    //                            }
                    //                        }
                    //                    });
                    //                    return mapper; 
                })
                .map(mapper -> {
                    if (paramBean.getFlagEstado() == 0 && mapper.getMontoPendiente().compareTo(BigDecimal.ZERO) == 1) {
                        mapper.setEstadoDescripcion("Afecto a Corte");
                    }
                    if (paramBean.getFlagEstado() == 1 && mapper.getMontoPendiente().compareTo(BigDecimal.ZERO) == 0) {
                        mapper.setEstadoDescripcion("Vigente");
                    }
                    return mapper;
                })
                .filter(predicate -> (paramBean.getFlagEstado() == 0 && predicate.getMontoPendiente().compareTo(BigDecimal.ZERO) == 1)
                || (paramBean.getFlagEstado() == 1 && predicate.getMontoPendiente().compareTo(BigDecimal.ZERO) == 0)
                || paramBean.getFlagEstado() == 2)
                .collect(Collectors.toList());
    }
}

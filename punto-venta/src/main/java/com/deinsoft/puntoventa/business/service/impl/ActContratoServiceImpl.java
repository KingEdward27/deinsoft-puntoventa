package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import com.deinsoft.puntoventa.business.bean.UploadResponse;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.dto.SecurityFilterDto;
import com.deinsoft.puntoventa.business.exception.BusinessException;
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
import com.deinsoft.puntoventa.business.model.CnfEmpresa;
import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.model.CnfMaestro;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.model.CnfPlanContrato;
import com.deinsoft.puntoventa.business.model.CnfTipoComprobante;
import com.deinsoft.puntoventa.business.model.CnfTipoDocumento;
import com.deinsoft.puntoventa.business.model.CnfZona;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActContratoRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoRepository;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.repository.CnfMaestroRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.service.ActContratoService;
import com.deinsoft.puntoventa.business.service.ActPagoProgramacionService;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.business.service.CnfEmpresaService;
import com.deinsoft.puntoventa.business.service.CnfMaestroService;
import com.deinsoft.puntoventa.business.service.CnfPlanContratoService;
import com.deinsoft.puntoventa.business.service.CnfTipoComprobanteService;
import com.deinsoft.puntoventa.business.service.CnfTipoDocumentoService;
import com.deinsoft.puntoventa.business.service.CnfZonaService;
import com.deinsoft.puntoventa.framework.util.Util;
import com.deinsoft.puntoventa.util.Constantes;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

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
    ActPagoProgramacionService actPagoProgramacionService;

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;

    @Autowired
    ActPagoRepository actPagoRepository;

    @Autowired
    BusinessService businessService;

    @Autowired
    CnfTipoDocumentoService cnfTipoDocumentoService;

    @Autowired
    CnfPlanContratoService cnfPlanContratoService;

    @Autowired
    CnfZonaService cnfZonaService;

    @Autowired
    CnfEmpresaService cnfEmpresaService;

    @Autowired
    CnfMaestroRepository cnfMaestroRepository;

    @Autowired
    CnfMaestroService cnfMaestroService;

    @Autowired
    CnfTipoComprobanteService cnfTipoComprobanteService;

    static DateTimeFormatter YYYYMM_FORMATER = DateTimeFormatter.ofPattern("yyyyMM");

    static DateTimeFormatter DD_MM_YYYY_FORMATER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter YYYY_MM_D_FORMATER = DateTimeFormatter.ofPattern("yyyy-M-d");
    static DateTimeFormatter YYYYMMDD_FORMATER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<ActContrato> getAllActContrato(ActContrato actContrato) {
        List<ActContrato> actContratoList = (List<ActContrato>) actContratoRepository.getAllActContrato(
                actContrato.getCnfEmpresaId(),
                actContrato.getSerie().toUpperCase(),
                actContrato.getNumero().toUpperCase(),
                actContrato.getFlagEstado().toUpperCase(),
                actContrato.getNroPoste().toUpperCase(),
                actContrato.getUrlMap().toUpperCase(),
                actContrato.getDireccion().toUpperCase(),
                listRoles());
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
        SecurityFilterDto securityFilterDto = listRoles();
        businessService.verifyPlan(null, actContrato);

        long id = actContrato.getId();
        if (id == 0) {
            actContrato.setFechaRegistro(LocalDateTime.now());
        }
        if (actContrato.getCnfFormaPago() != null && actContrato.getCnfFormaPago().getId() == 0) {
            actContrato.setCnfFormaPago(null);
        }
        actContrato.setSegUsuario(auth.getLoggedUserdata());
        if (id == 0) {
            List<ActContrato> listContratos = actContratoRepository.findByCnfMaestroIdAndCnfPlanContratoId(actContrato.getCnfMaestro().getId(), actContrato.getCnfPlanContrato().getId());
            if (!listContratos.isEmpty()) {
                throw new Exception("Ya existe un registro con el mismo plan y mismo cliente");
            }
            setSerieAndNumero(actContrato);
        }

        ActContrato actContratoResult = actContratoRepository.save(actContrato);

        if (id == 0) {
            if ((actContrato.getCnfFormaPago() != null && actContrato.getCnfFormaPago().getId() > 0)) {
                saveActPagoProgramacionOrCajaOperacion(actContratoResult);
            } else {
                try {
                    saveActPagoProgramacionMensualRepetitivo(actContratoResult);
                } catch (Exception e) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw e;
                }
            }
        }

        return actContratoResult;
    }

    private void setSerieAndNumero(ActContrato actContrato) throws Exception {
        List<CnfNumComprobante> numComprobante = cnfNumComprobanteRepository.findByCnfTipoComprobanteCodigoAndCnfLocalId(
                Constantes.COD_TIPO_COMPROBANTE_CONTRATO,
                actContrato.getCnfLocal().getId());
        if (numComprobante.isEmpty()) {
            throw new Exception("No existe numeración para el tipo de comprobante y el local");
        }

        CnfNumComprobante cnfNumComprobante = numComprobante.get(0);
        cnfNumComprobante.setUltimoNro(numComprobante.get(0).getUltimoNro() + 1);
        cnfNumComprobanteRepository.save(cnfNumComprobante);
        actContrato.setSerie(cnfNumComprobante.getSerie());
        actContrato.setNumero(String.format("%08d", cnfNumComprobante.getUltimoNro()));
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
        int diaVencimiento = actContrato.getCnfPlanContrato().getDiaVencimiento();
        if (actContrato.getFecha().lengthOfMonth() < diaVencimiento) {
            diaVencimiento = actContrato.getFecha().lengthOfMonth();
        }
        long days = 0;
        //primero validar si es mes actual
        long elapsedMonths = ChronoUnit.MONTHS.between(LocalDate.now(),
                actContrato.getFecha().withDayOfMonth(LocalDate.now().getDayOfMonth()));
        if (elapsedMonths < 0 || elapsedMonths > 1) {
            throw new Exception("Fecha de instalación invalida");
        }
        LocalDate proxDate = actContrato.getFecha()
                .withDayOfMonth(diaVencimiento);
        if (elapsedMonths == 0) {

            if (diaVencimiento >= actContrato.getFecha().getDayOfMonth()) {
                days = ChronoUnit.DAYS.between(actContrato.getFecha(), actContrato.getFecha().withDayOfMonth(diaVencimiento));
            } else {
                proxDate = actContrato.getFecha()
                        .withDayOfMonth(diaVencimiento)
                        .plusMonths(1);
                days = ChronoUnit.DAYS.between(actContrato.getFecha(), actContrato.getFecha().withDayOfMonth(diaVencimiento).plusMonths(1));
            }
        }
        if (elapsedMonths == 1) {
            if (diaVencimiento <= actContrato.getFecha().getDayOfMonth()) {
                throw new Exception("La fecha de instalación no puede ser posterior a la fecha de vencimiento del mes siguiente");
            } else {

                days = ChronoUnit.DAYS.between(actContrato.getFecha().plusMonths(1), actContrato.getFecha().withDayOfMonth(diaVencimiento).plusMonths(1));
            }
        }

        actPayment.setFechaVencimiento(proxDate);
//        
//        if (actContrato.getFecha().getMonthValue() == LocalDate.now().getMonthValue()
//                && diaVencimiento <= actContrato.getCnfPlanContrato().getDiaVencimiento()) {
//            throw new Exception("No se encontró fecha de vencimiento para el plan seleccionado");
//        }

        actPayment.setMonto(new BigDecimal(actContrato.getCnfPlanContrato().getPrecioInstalacion().doubleValue() + actContrato.getCnfPlanContrato().getPrecio().doubleValue() / actContrato.getFecha().lengthOfMonth() * days));
        actPayment.setMontoPendiente(actPayment.getMonto());
        actPagoProgramacionRepository.save(actPayment);
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
            //colegios?
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
        List<ActContrato> actContratoList = (List<ActContrato>) actContratoRepository.getReportActContrato(paramBean, listRoles());

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
                        if ((action.getMontoPendiente().compareTo(action.getMonto()) != 0)) {

                            ActPago actPago
                                    = actPagoList.stream()
                                            .filter(data -> data.getListActPagoDetalle().stream()
                                            .anyMatch(predicate -> predicate.getActPagoProgramacion() != null
                                            && predicate.getActPagoProgramacion().getId() == action.getId()))
                                            .findFirst().orElse(new ActPago());

                            //mapper.setUltimoPago(actPago.getSerie() + "-" + actPago.getNumero() + " (" + actPago.getFecha().format(DateTimeFormatter.ISO_DATE) + "): " + actPago.getTotal());
                            mapper.setUltimoPago(actPago.getSerie() + "-" + actPago.getNumero() + " ("
                                    + action.getFechaVencimiento().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) + "): " + actPago.getTotal());
                            if (action.getMontoPendiente().compareTo(BigDecimal.ZERO) > 0) {
                                mapper.setMesesDeuda(mapper.getMesesDeuda() + 1);
                                mapper.setMontoPendiente(mapper.getMontoPendiente().add(action.getMontoPendiente()));
                            }
                        } else {
                            if (action.getFechaVencimiento().compareTo(paramBean.getFechaVencimiento()) <= 0) {

                                mapper.setMesesDeuda(mapper.getMesesDeuda() + 1);
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
                    if (mapper.getFlagEstado().equals("2")) {
                        mapper.setEstadoDescripcion("Corte Efectuado");
                        mapper.setColor("red");
                    } else {

                        mapper.setColor("green");
                        if (mapper.getMesesDeuda() > 1) {
                            mapper.setEstadoDescripcion("Afecto a Corte");
                            mapper.setColor("yellow");
                        } else if (mapper.getMesesDeuda() == 1) {
                            if (LocalDate.now().getDayOfMonth() == 0) {
                                if (mapper.getMontoPendiente().compareTo(BigDecimal.ZERO) == 1) {
                                    mapper.setEstadoDescripcion("Afecto a Corte");
                                    mapper.setColor("yellow");
                                }
                                if (mapper.getMontoPendiente().compareTo(BigDecimal.ZERO) == 0) {
                                    mapper.setEstadoDescripcion("Vigente");
                                    mapper.setMesesDeuda(0);
                                    mapper.setMontoPendiente(BigDecimal.ZERO);
                                }
                            } else {
                                if (mapper.getCnfPlanContrato().getDiaProcesoCorte() != null && LocalDate.now().getDayOfMonth() >= mapper.getCnfPlanContrato().getDiaProcesoCorte()) {
                                    if (mapper.getMontoPendiente().compareTo(BigDecimal.ZERO) == 1) {
                                        mapper.setEstadoDescripcion("Afecto a Corte");
                                        mapper.setColor("yellow");
                                    }
                                    if (mapper.getMontoPendiente().compareTo(BigDecimal.ZERO) == 0) {
                                        mapper.setEstadoDescripcion("Vigente");
                                        mapper.setMesesDeuda(0);
                                        mapper.setMontoPendiente(BigDecimal.ZERO);
                                    }
                                } else {
                                    mapper.setEstadoDescripcion("Vigente");
                                    
                                    mapper.setMesesDeuda(0);
                                    mapper.setMontoPendiente(BigDecimal.ZERO);
                                }
                            }

                        } else {
                            mapper.setEstadoDescripcion("Vigente");
                            mapper.setMontoPendiente(BigDecimal.ZERO);
                        }
                    }

                    return mapper;
                })
                .filter(predicate -> (paramBean.getFlagEstado().equals("0") && predicate.getEstadoDescripcion().equals("Afecto a Corte"))
                || (paramBean.getFlagEstado().equals("1") && predicate.getEstadoDescripcion().equals("Vigente"))
                || paramBean.getFlagEstado().equals("2"))
                .collect(Collectors.toList());

    }

    @Override
    public Map<String, Object> getDashboardActContratos(long empresaId) {

//        actPagoProgramacionService.refreshProgramacionPagos();
        List<ActPagoProgramacion> actPagoProgramacionList = actPagoProgramacionRepository.findAll().stream()
                .filter(predicate -> predicate.getActContrato() != null)
                .filter(predicate -> predicate.getActContrato().getCnfLocal().getCnfEmpresa().getId() == empresaId)
                .collect(Collectors.toList());

        Double total = actPagoRepository.findAll().stream()
                .filter(predicate -> predicate.getCnfLocal() != null && predicate.getCnfLocal().getCnfEmpresa().getId() == empresaId)
                .collect(Collectors.summingDouble(e -> e.getTotal().doubleValue()));

        List<ActContrato> actContratoList = (List<ActContrato>) actContratoRepository.findAll().stream()
                .filter(predicate -> predicate.getCnfLocal().getCnfEmpresa().getId() == empresaId)
                .map(map -> {
                    ActPagoProgramacion actPagoProgramacionEnDeuda
                            = actPagoProgramacionList.stream()
                                    .filter(data -> data.getActContrato() != null && data.getActContrato().getId() == map.getId())
                                    .filter(predicate -> Integer.parseInt(predicate.getFechaVencimiento().format(YYYYMM_FORMATER)) <= Integer.parseInt(LocalDate.now().format(YYYYMM_FORMATER))
                                    && predicate.getMontoPendiente().compareTo(BigDecimal.ZERO) > 0)
                                    .findFirst().orElse(new ActPagoProgramacion());

                    if (actPagoProgramacionEnDeuda.getId() == 0) {
                        map.setEstadoDescripcion("Vigente");//error
                    } else {
                        map.setEstadoDescripcion("Afecto a Corte");
                    }
                    return map;
                })
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("cantidadContratos", actContratoList.size());
        map.put("cantidadDeudores", actContratoList.stream().filter(predicate -> predicate.getEstadoDescripcion().equals("Afecto a Corte")).count());
        map.put("ratioEndeudamiento", actContratoList.stream().filter(predicate -> predicate.getEstadoDescripcion().equals("Vigente")).count() / (actContratoList.isEmpty() ? 1 : actContratoList.size()));
        map.put("totalMontoPagos", total);
        return map;
    }

    @Override
    @Transactional
    public List<UploadResponse> importExcel(MultipartFile reapExcelDataFile, CnfLocal cnfLocal) throws IOException {
        List<Object[]> list = Util.importExcel(reapExcelDataFile, false);
        List<ActContrato> listActContrato = new ArrayList<>();

        SecurityFilterDto f = listRoles();
        List<CnfTipoDocumento> listTipoDoc = cnfTipoDocumentoService.getAllCnfTipoDocumento();
        List<CnfPlanContrato> listPlan = cnfPlanContratoService.getAllCnfPlanContrato();
        List<CnfZona> listZona = cnfZonaService.getAllCnfZona();

        List<CnfMaestro> listCnfMaestroInDb = cnfMaestroService.getAllCnfMaestroByCnfEmpresa(f.getEmpresaId());
        List<ActContrato> listActContratoInDb = getAllActContrato();
        ActContrato actContrato = new ActContrato();
        List<UploadResponse> listResponse = new ArrayList<>();
        int row = -1, col = 0;
        List<String> errorMessage = new ArrayList<>();
        List<CnfMaestro> listCnfMaestro = new ArrayList<>();
        CnfTipoDocumento cnfTipoDocumento = null;
        CnfPlanContrato cnfPlanContrato;
        CnfZona cnfZona = null;
        CnfMaestro cnfMaestro;
        boolean existsErrors = false;
        SegUsuario user = auth.getLoggedUserdata();
        if (cnfLocal == null) {
            throw new BusinessException("Debe seleccionar el local");
        }
        CnfEmpresa empresa = cnfEmpresaService.getCnfEmpresa(f.getEmpresaId());
        CnfTipoComprobante cnfTipoComprobante = cnfTipoComprobanteService.getAllCnfTipoComprobante().stream()
                .filter(predicate -> predicate.getCodigo().equals(Constantes.COD_TIPO_COMPROBANTE_CONTRATO)).findFirst().orElse(null);
        if (cnfTipoComprobante == null) {
            throw new BusinessException("No existe el tipo de comprobante para el registro de Contratos. Consulte con administrador TI");
        }
        //String[] cabecera = {
//        "Apellido Paterno", 0
//        "Apellido materno", 1
//        "Nombres", 2
//        "Razón Social", 3
//        "Tipo de documento (1= DNI,6=RUC)",4
        //    "Número de documento",5
        //    "fecha de instalación (DD/MM/YYYY)",6
//        "Plan",7
//        "Zona",8
//        "Dirección",9
//        "Telefono",10
//        "Correo"};11
//        "nro poste";12
        try {
            for (Object[] object : list) {
                row++;
                col = 0;
                errorMessage = new ArrayList<>();
                if (object.length != 13) {
                    errorMessage.add("Se esperaban 13 columnas");
                    existsErrors = true;
                    listResponse.add(new UploadResponse(row, errorMessage));
                } else {
                    String apellidoPaterno = Util.getStringValue(object[col++]);
                    String apellidoMaterno = Util.getStringValue(object[col++]);
                    String nombres = Util.getStringValue(object[col++]);
                    String razonSocial = Util.getStringValue(object[col++]);
                    String tipoDocumento = Util.getStringValue(object[col++]);
                    String numDocumento = Util.getStringValue(object[col++]);
                    String fechaInstalacion = Util.getStringValue(object[col++]);
                    String plan = Util.getStringValue(object[col++]);
                    String zona = Util.getStringValue(object[col++]);
                    String direccion = Util.getStringValue(object[col++]);
                    String telefono = Util.getStringValue(object[col++]);
                    String correo = Util.getStringValue(object[col++]);
                    String nroPoste = Util.getStringValue(object[col++]);

                    if (Util.isNullOrEmpty(numDocumento)) {
                        errorMessage.add("Número de documento no debe estar vació");
                    }
                    if (Util.isNullOrEmpty(plan)) {
                        errorMessage.add("Plan no debe estar vació");
                    }
                    actContrato = listActContratoInDb.stream()
                            .filter(predicate -> predicate.getCnfMaestro().getNroDoc().equals(numDocumento)
                            && predicate.getCnfPlanContrato().getNombre().equals(plan))
                            .findFirst().orElse(null);
                    if (actContrato != null) {
                        errorMessage.add("Ya existe contrato para el cliente con el plan y mismo cliente");
                        existsErrors = true;
                        listResponse.add(new UploadResponse(row, errorMessage));
                        continue;
                    }
                    actContrato = new ActContrato();

                    if (Util.isNullOrEmpty(tipoDocumento)) {
                        errorMessage.add("Código de tipo de documento no existe");
                    }
                    try {
                        Float tipoDoc = Float.parseFloat(tipoDocumento.trim());
                        cnfTipoDocumento = listTipoDoc.stream()
                                .filter(predicate -> predicate.getCodigoSunat().equals(
                                String.valueOf(tipoDoc.intValue())))
                                .findFirst().orElse(null);
                        if (cnfTipoDocumento == null) {
                            errorMessage.add("Tipo de documento no existe");
                        }
                    } catch (Exception e) {
                        errorMessage.add("Formato de tipo de documento incorrecto");
                    }

                    cnfPlanContrato = listPlan.stream().filter(predicate -> predicate.getNombre().equals(plan.trim()))
                            .findFirst().orElse(null);
                    if (cnfPlanContrato == null) {
                        errorMessage.add("Plan no existe. Debe registrar el plan primero");
                    }

                    if (Util.isNullOrEmpty(zona)) {
                        errorMessage.add("Debe ingresar la zona");
                    } else {
                        cnfZona = listZona.stream().filter(predicate -> predicate.getNombre().equals(zona.trim()))
                                .findFirst().orElse(null);
                        if (cnfZona == null) {
                            cnfZona = new CnfZona(zona, empresa);
//                        cnfZona = cnfZonaService.save(new CnfZona(zona, empresa));
                            listZona.add(cnfZona);
                        } else {
                            listZona.add(cnfZona);
                        }
                    }

                    if (tipoDocumento.equals("1") && numDocumento.length() != 8) {
                        errorMessage.add("El Número de documento para el tipo de documento DNI debe tener 8 dígitos");
                    }
                    if (tipoDocumento.equals("6") && numDocumento.length() != 11) {
                        errorMessage.add("El Número de documento para el tipo de documento RUC debe tener 11 dígitos");
                    }
                    if (tipoDocumento.equals("1") && nombres == null) {
                        errorMessage.add("Si el Tipo de documento es DNI debe ingresar al menos los nombres del cliente");
                    }
                    if (tipoDocumento.equals("6") && razonSocial == null) {
                        errorMessage.add("Si el Tipo de documento es RUC debe ingresar la razón social");
                    }
                    if (direccion == null) {
                        errorMessage.add("Dirección no pude estar vacío");
                    }
                    if (tipoDocumento.equals("1")) {
                        razonSocial = "";
                        if (!Util.isNullOrEmpty(apellidoPaterno)) {
                            razonSocial = razonSocial + apellidoPaterno + " ";
                        }
                        if (!Util.isNullOrEmpty(apellidoMaterno)) {
                            razonSocial = razonSocial + apellidoMaterno + " ";
                        }
                        if (!Util.isNullOrEmpty(nombres)) {
                            razonSocial = razonSocial + nombres + " ";
                        }
                        razonSocial = razonSocial.trim();
                    }
                    cnfMaestro = listCnfMaestroInDb.stream()
                            .filter(predicate -> predicate.getNroDoc().equals(numDocumento)).findFirst().orElse(null);
                    if (cnfMaestro != null) {
                        actContrato.setCnfMaestro(cnfMaestro);
                        //errorMessage.add("Cliente ya existe");
                    } else {
                        cnfMaestro = new CnfMaestro(numDocumento,
                                nombres, apellidoPaterno, apellidoMaterno,
                                razonSocial, direccion, correo,
                                telefono,
                                cnfTipoDocumento,
                                empresa);
                        listCnfMaestroInDb.add(cnfMaestro);
                        listCnfMaestro.add(cnfMaestro);
                        actContrato.setCnfMaestro(cnfMaestro);
                    }

                    if (!Util.validateFormatDate(fechaInstalacion)) {
                        errorMessage.add("Fecha de instalación no puede estar en blanco o en formato incorrecto");
                    } else {
                        actContrato.setFecha(Util.getDateFromString(fechaInstalacion, YYYY_MM_D_FORMATER));
                    }

                    

                    

                    if (errorMessage.isEmpty()) {
                        actContrato.setDireccion(direccion);
                        actContrato.setCnfPlanContrato(cnfPlanContrato);
                        actContrato.setCnfZona(cnfZona);
                        actContrato.setFechaRegistro(LocalDateTime.now());
                        actContrato.setCnfLocal(cnfLocal);
                        actContrato.setFlagEstado(Constantes.COD_ESTADO_ACTIVO);
                        actContrato.setCnfTipoComprobante(cnfTipoComprobante);
                        actContrato.setUrlMap("");
                        actContrato.setNroPoste(nroPoste);
                        actContrato.setFlagImported("1");
                        actContrato.setSegUsuario(user);
                        try {
                            setSerieAndNumero(actContrato);

                        } catch (Exception e) {
                            throw new BusinessException("Hubo un problema al asignar serie y número a los contratos");
                        }
                        //validar nombrs para otro nro documento
                        //calcular monto primer pago
                        try {
                            businessService.verifyPlan(null, actContrato);
                        } catch (Exception e) {
                            errorMessage.add(e.getMessage());
                        }
                        listActContrato.add(actContrato);
                        listActContratoInDb.add(actContrato);
                    } else {
                        existsErrors = true;
                    }
                    listResponse.add(new UploadResponse(row, 
                            errorMessage.isEmpty()?Arrays.asList("Ok"):errorMessage));
                    
                }

            }
            if (!existsErrors) {
                cnfZonaService.saveAll(listZona);
                cnfMaestroRepository.saveAll(listCnfMaestro);
                actContratoRepository.saveAll(listActContrato);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("1 "+ e.getCause());
//            System.out.println("2" + e.getLocalizedMessage());
            listResponse.add(new UploadResponse(row,Arrays.asList("Error inesperado: " 
                    + e.toString())));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return listResponse;
    }
}

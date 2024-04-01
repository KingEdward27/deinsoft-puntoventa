package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.service.ActPagoProgramacionService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.business.model.ActPagoDetalle;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.ActContratoRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoRepository;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActPagoProgramacionServiceImpl
        extends CommonServiceImpl<ActPagoProgramacion, ActPagoProgramacionRepository> implements ActPagoProgramacionService {

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;
    
    @Autowired
    ActContratoRepository actContratoRepository;
    

    @Autowired
    ActPagoRepository actPagoRepository;

    public List<ActPagoProgramacion> getAllActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {

        List<ActPagoProgramacion> actPagoProgramacionList = (List<ActPagoProgramacion>) actPagoProgramacionRepository.getAllActPagoProgramacion(listRoles());
        return actPagoProgramacionList.stream().filter(item -> !item.getActComprobante().getFlagIsventa().equals("1"))
//                .filter(item -> listRoles().stream()
//                .anyMatch(predicate -> predicate.getEmpresa().getId() == item.getActComprobante().getCnfLocal().getCnfEmpresa().getId()))
                .collect(Collectors.toList());
    }

    public ActPagoProgramacion getActPagoProgramacion(Long id) {
        ActPagoProgramacion actPagoProgramacion = null;
        Optional<ActPagoProgramacion> actPagoProgramacionOptional = actPagoProgramacionRepository.findById(id);
        if (actPagoProgramacionOptional.isPresent()) {
            actPagoProgramacion = actPagoProgramacionOptional.get();
        }
        return actPagoProgramacion;
    }

    public ActPagoProgramacion saveActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {
        ActPagoProgramacion actPagoProgramacionResult = actPagoProgramacionRepository.save(actPagoProgramacion);
        return actPagoProgramacionResult;
    }

    public List<ActPagoProgramacion> getAllActPagoProgramacion() {
        List<ActPagoProgramacion> actPagoProgramacionList = (List<ActPagoProgramacion>) actPagoProgramacionRepository.findAll();
        return actPagoProgramacionList;
    }

    public List<ActPagoProgramacion> getAllActPagoProgramacionByActComprobante(long id) {
        List<ActPagoProgramacion> ActPagoProgramacionList = (List<ActPagoProgramacion>) actPagoProgramacionRepository.findByActComprobanteId(id);
        return ActPagoProgramacionList;
    }

    @Override
    public void delete(long id) {
        ActPagoProgramacion actPagoProgramacion = null;
        Optional<ActPagoProgramacion> actPagoProgramacionOptional = actPagoProgramacionRepository.findById(id);

        if (actPagoProgramacionOptional.isPresent()) {
            actPagoProgramacion = actPagoProgramacionOptional.get();
            actPagoProgramacionRepository.delete(actPagoProgramacion);
        }
    }

    public List<ActPagoProgramacion> getAllActPagoProgramacionByCnfMaestro(long id, LocalDate fechaVencimiento, long cnfLocalId, boolean onlyPendientes) {
        List<ActPagoProgramacion> actPagoProgramacionList
                = (List<ActPagoProgramacion>) actPagoProgramacionRepository.findByCnfMaestroId(id, fechaVencimiento,listRoles());

        List<ActPago> actPagoList = actPagoRepository.findAll();

        List<ActPagoProgramacion> ActPagoProgramacionList
                = actPagoProgramacionList
                        .stream()
                        .filter(item -> {
                            System.out.println("getActComprobante " + item.toString());
                            return (item.getActComprobante() != null
                                    && item.getActComprobante().getFlagIsventa().equals("1"))
                                    || item.getActComprobante() == null;
                        })
                        .filter(item -> (item.getActContrato() != null
                        ? item.getActContrato().getCnfLocal().getId()
                        : item.getActComprobante().getCnfLocal().getId()) == cnfLocalId)
                        .filter(predicate -> (onlyPendientes && predicate.getMontoPendiente().compareTo(BigDecimal.ZERO) > 0) || !onlyPendientes)
                        .map(data -> {
                            if (data.getFechaVencimiento().compareTo(LocalDate.now()) >= 0) {
                                data.setColor("yellow");
                            } else {
                                data.setColor("red");
                            }
                            data.setMes(data.getFechaVencimiento().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase());

                            ActPago actPago
                                    = actPagoList.stream()
                                            .filter(data2 -> data2.getListActPagoDetalle().stream()
                                            .anyMatch(predicate -> predicate.getActPagoProgramacion() != null
                                            && predicate.getActPagoProgramacion().getId() == data.getId()))
                                            .findFirst().orElse(new ActPago());

                            if (actPago.getId() != 0) {
                                ActPagoDetalle actPagoDetalle
                                        = actPago.getListActPagoDetalle().stream()
                                                .filter(predicate -> predicate.getActPagoProgramacion().getId() == data.getId())
                                                .findFirst().orElse(new ActPagoDetalle());

                                //mapper.setUltimoPago(actPago.getSerie() + "-" + actPago.getNumero() + " (" + actPago.getFecha().format(DateTimeFormatter.ISO_DATE) + "): " + actPago.getTotal());
                                data.setUltimoPago(actPago.getSerie() + "-" + actPago.getNumero() + ": S/ " + actPagoDetalle.getMonto());
                            }

                            return data;
                        })
                        .sorted(Comparator.comparing(a -> a.getActContrato() != null? a.getActContrato().getId(): a.getActComprobante().getId()))
                        .collect(Collectors.toList());
        return ActPagoProgramacionList;
    }
    @Override
    public void refreshProgramacionPagos (){
       List<ActContrato> list =  actContratoRepository.findAll();
        for (ActContrato actContrato : list) {
            //cuando forma de pago es repetitivo se debe poner el detalle, en este caso se va a agregar programacion cada mes
            if (actContrato.getCnfFormaPago() != null) {
                continue;
            }
            if (!actContrato.getFlagEstado().equals("1")) {
                continue;
            }
            ActPagoProgramacion programacionMesActual = actPagoProgramacionRepository.findByActContratoId(actContrato.getId()).stream()
                    .filter(predicate -> predicate.getFechaVencimiento().withDayOfMonth(1)
                            .compareTo(LocalDate.now().withDayOfMonth(1)) == 0)
                    .findFirst().orElse(null);
            if (programacionMesActual != null) {
                if (programacionMesActual.getFechaVencimiento().withDayOfMonth(1)
                            .compareTo(LocalDate.now().withDayOfMonth(1)) < 0) {
                    ActPagoProgramacion actPayment = new ActPagoProgramacion();
                    actPayment.setActContrato(actContrato);
                    actPayment.setFecha(actContrato.getFecha());
                    actPayment.setFechaVencimiento(programacionMesActual.getFechaVencimiento().plusMonths(1));

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
            
        }
    }
    public List<ActPagoProgramacion> getAllActPagoProgramacionCompraByCnfMaestro(long id, LocalDate fechaVencimiento) {

        List<ActPagoProgramacion> ActPagoProgramacionList
                = (List<ActPagoProgramacion>) actPagoProgramacionRepository.findByCnfMaestroId(id, fechaVencimiento,listRoles())
                        .stream().filter(item -> item.getActComprobante() != null
                        && !item.getActComprobante().getFlagIsventa().equals("1"))
//                        .filter(item -> listRoles().stream()
//                        .anyMatch(predicate
//                                -> predicate.getEmpresa().getId() == item.getActComprobante().getCnfLocal().getCnfEmpresa().getId()))
                        .collect(Collectors.toList());
        return ActPagoProgramacionList;
    }
//    @Override
//    public List<ActPagoProgramacion> getReportActPagoProgramacionContratos(ParamBean paramBean) {
//        List<ActPagoProgramacion> actCajaOperacionList = (List<ActPagoProgramacion>) actPagoProgramacionRepository.getReportActPagoProgramacionContratos(paramBean);
//
//        List<ActPagoProgramacion> actPagoProgramacionList2 = getAllActPagoProgramacion();
//        
//        return actCajaOperacionList.stream()
//                .filter(predicate -> {
//                    if (paramBean.getCnfLocal().getId() == 0) {
//                        return true;
//                    } else {
//                        if (predicate.getActComprobante() != null 
//                                && predicate.getActComprobante().getCnfLocal().getId() == paramBean.getCnfLocal().getId()) {
//                            return true;
//                        } else if (predicate.getActContrato()!= null) {
//                            return predicate.getActContrato().getCnfLocal().getId() == paramBean.getCnfLocal().getId();
//                        }
//                        return false;
//                    }
//                })
//                .filter(predicate -> {
//                    if (paramBean.getCnfMaestro().getId() == 0) {
//                        return true;
//                    } else {
//                        if (predicate.getActComprobante() != null 
//                                && predicate.getActComprobante().getCnfMaestro().getId() == paramBean.getCnfMaestro().getId()) {
//                            return true;
//                        } else if (predicate.getActContrato()!= null) {
//                            return predicate.getActContrato().getCnfMaestro().getId() == paramBean.getCnfMaestro().getId();
//                        }
//                        return false;
//                    }
//
//                })
//                .map(mapper -> {
//                    actPagoProgramacionList2.forEach(action -> {
//                        if (true) {
//                            
//                        }
//                    });
//                    if (mapper.) {
//                
//            }
//                    return mapper;
//                })
//                .collect(Collectors.toList());
//    }

}

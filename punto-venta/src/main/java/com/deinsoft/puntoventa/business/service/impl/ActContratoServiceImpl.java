package com.deinsoft.puntoventa.business.service.impl;

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
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActContratoRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoDetalleRepository;
import com.deinsoft.puntoventa.business.service.ActContratoService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    public List<ActContrato> getAllActContrato(ActContrato actContrato) {
        List<ActContrato> actContratoList = (List<ActContrato>) actContratoRepository.getAllActContrato(
                actContrato.getSerie().toUpperCase(),
                actContrato.getNumero().toUpperCase(),
                actContrato.getObservacion().toUpperCase(), actContrato.getFlagEstado().toUpperCase(),
                actContrato.getNroPoste().toUpperCase(), actContrato.getUrlMap().toUpperCase());
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
    public ActContrato saveActContrato(ActContrato actContrato) throws Exception{
        if (actContrato.getId() == 0) {
            actContrato.setFechaRegistro(LocalDateTime.now());
        }
        ActContrato actContratoResult = actContratoRepository.save(actContrato);
        
        saveActPagoProgramacionOrCajaOperacion(actContratoResult);
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
            actContratoRepository.delete(actContrato);
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
}

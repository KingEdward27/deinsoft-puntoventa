package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.business.repository.ActPagoRepository;
import com.deinsoft.puntoventa.business.service.ActPagoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional
public class ActPagoServiceImpl extends CommonServiceImpl<ActPago, ActPagoRepository> implements ActPagoService {

    @Autowired
    ActPagoRepository actPagoRepository;

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;
    
    @Autowired
    ActCajaOperacionRepository actCajaOperacionRepository;
    
    public List<ActPago> getAllActPago(ActPago actPago) {
        List<ActPago> actPagoList = (List<ActPago>) actPagoRepository.getAllActPago();
        return actPagoList;
    }

    public ActPago getActPago(Long id) {
        ActPago actPago = null;
        Optional<ActPago> actPagoOptional = actPagoRepository.findById(id);
        if (actPagoOptional.isPresent()) {
            actPago = actPagoOptional.get();
        }
        return actPago;
    }

    public ActPago saveActPago(ActPago actPago) {
        ActPago actPagoResult = actPagoRepository.save(actPago);
        return actPagoResult;
    }

    public List<ActPago> getAllActPago() {
        List<ActPago> actPagoList = (List<ActPago>) actPagoRepository.findAll();
        return actPagoList;
    }

    public List<ActPago> getAllActPagoByActPagoProgramacion(long id) {
        List<ActPago> ActPagoList = (List<ActPago>) actPagoRepository.findByActPagoProgramacionId(id);
        return ActPagoList;
    }

    @Override
    public void delete(long id) {
        ActPago actPago = null;
        Optional<ActPago> actPagoOptional = actPagoRepository.findById(id);

        if (actPagoOptional.isPresent()) {
            actPago = actPagoOptional.get();
            actPagoRepository.delete(actPago);
        }
    }
    @Override
    @Transactional
    public List<ActPago> saveActPaymentDetailFromList(List<ActPagoProgramacion> listActPayment) {
        List<ActPago> list = new ArrayList<>();
        for (ActPagoProgramacion actPayment : listActPayment) {
            if (actPayment.getAmtToPay().compareTo(BigDecimal.ZERO) == 1) {
                ActPago detail = new ActPago();
                detail.setActPagoProgramacion(actPayment);
                detail.setFecha(LocalDate.now());
                detail.setMonto(actPayment.getAmtToPay());
                list.add(detail);

                ActPagoProgramacion actPaymentToSave = actPayment;
                actPaymentToSave.setMontoPendiente(actPayment.getMontoPendiente().subtract(detail.getMonto()));
                actPagoProgramacionRepository.save(actPaymentToSave);
                
                
//                ActCajaOperacion actCajaOperacion = new ActCajaOperacion();
//                actCajaOperacion.setActCajaTurno(null);
//                actCajaOperacion.setActPago(detail);
//                actCajaOperacion.setActComprobante(null);
//                actCajaOperacion.setFecha(LocalDate.now());
//                actCajaOperacion.setFechaRegistro(LocalDateTime.now());
//                actCajaOperacion.setMonto(actPayment.getAmtToPay());
//                actCajaOperacionRepository.save(actCajaOperacion);
            }
            
        }
        List<ActPago> listActPaymentDetailResult = actPagoRepository.saveAll(list);
        return listActPaymentDetailResult;
    }
}

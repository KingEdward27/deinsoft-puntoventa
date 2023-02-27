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
import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.model.ActPagoDetalle;
import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActPagoServiceImpl extends CommonServiceImpl<ActPago, ActPagoRepository> implements ActPagoService {

    @Autowired
    ActPagoRepository actPagoRepository;

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;

    @Autowired
    ActCajaOperacionRepository actCajaOperacionRepository;

    @Autowired
    ActCajaTurnoRepository actCajaTurnoRepository;

    @Autowired
    CnfNumComprobanteRepository cnfNumComprobanteRepository;
    
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

    public ActPago saveActPago(ActPago actPago) throws Exception {
        //tiene que obedecer un parametro de configuración caja turno, por ahora null
        List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(actPago.getSegUsuario().getId());
        actCajaTurno = actCajaTurno.stream()
                .filter(item -> item.getEstado().equals("1"))
                .collect(Collectors.toList());
        if (actCajaTurno.isEmpty()) {
            throw new Exception("El usuario no tiene caja aperturada");
        }
        List<CnfNumComprobante> numComprobante = 
                cnfNumComprobanteRepository.findByCnfTipoComprobanteIdAndCnfLocalId(
                    actPago.getCnfTipoComprobante().getId(),
                    actPago.getListActPagoDetalle().iterator().next()
                            .getActPagoProgramacion().getActContrato().getCnfLocal().getId());
            if (numComprobante.isEmpty()) {
                throw new Exception("No existe numeración para el tipo de comprobante y el local");
            }
        for (ActPagoDetalle actPagoDetalle : actPago.getListActPagoDetalle()) {
            if (actPagoDetalle.getActPagoProgramacion().getAmtToPay().compareTo(BigDecimal.ZERO) == 1) {

                ActPagoProgramacion actPaymentToSave = actPagoDetalle.getActPagoProgramacion();
                actPaymentToSave.setMontoPendiente(
                        actPagoDetalle.getActPagoProgramacion().getMontoPendiente().subtract(actPagoDetalle.getMonto()));
                actPagoProgramacionRepository.save(actPaymentToSave);

            }

        }
        actPago.setFechaRegistro(LocalDateTime.now());
        actPago.setNumero(String.valueOf(numComprobante.get(0).getUltimoNro() + 1));
        actPago.getListActPagoDetalle().forEach(data -> {
            actPago.addActPagoDetalle(data);
        });

        ActPago actPagoResult = actPagoRepository.save(actPago);

        ActCajaOperacion actCajaOperacion = new ActCajaOperacion();
        actCajaOperacion.setActCajaTurno(actCajaTurno == null ? null : actCajaTurno.get(0));
        actCajaOperacion.setActPago(actPago);
        actCajaOperacion.setActComprobante(null);
        actCajaOperacion.setFecha(LocalDate.now());
        actCajaOperacion.setFechaRegistro(LocalDateTime.now());
        actCajaOperacion.setMonto(actPago.getTotal());
        actCajaOperacion.setFlagIngreso("1");
        actCajaOperacion.setEstado("1");
        actCajaOperacionRepository.save(actCajaOperacion);

        return actPagoResult;
    }

    public List<ActPago> getAllActPago() {

        List<ActPago> actPagoList = (List<ActPago>) actPagoRepository.findAll();
        return actPagoList;
    }

//    public List<ActPago> getAllActPagoByActPagoProgramacion(long id) {
//        List<ActPago> ActPagoList = (List<ActPago>) actPagoRepository.findByActPagoProgramacionId(id);
//        return ActPagoList;
//    }

    @Override
    public void delete(long id) {
        ActPago actPago = null;
        Optional<ActPago> actPagoOptional = actPagoRepository.findById(id);

        if (actPagoOptional.isPresent()) {
            actPago = actPagoOptional.get();
            actPagoRepository.delete(actPago);
        }
    }

//    @Override
//    @Transactional
//    public List<ActPago> saveActPaymentDetailFromList(List<ActPagoProgramacion> listActPayment) throws Exception {
//        List<ActPago> list = new ArrayList<>();
//        for (ActPagoProgramacion actPayment : listActPayment) {
//            List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(actPayment.getSegUsuario().getId());
//            actCajaTurno = actCajaTurno.stream()
//                    .filter(item -> item.getEstado().equals("1"))
//                    .collect(Collectors.toList());
//            if (actCajaTurno.isEmpty()) {
//                throw new Exception("El usuario no tiene caja aperturada");
//            }
//            if (actPayment.getAmtToPay().compareTo(BigDecimal.ZERO) == 1) {
//                ActPago detail = new ActPago();
////                detail.setActPagoProgramacion(actPayment);
//                detail.setFecha(LocalDate.now());
//                detail.setTotal(actPayment.getAmtToPay());
//                detail.setSegUsuario(actPayment.getSegUsuario());
//                list.add(detail);
//
//                ActPagoProgramacion actPaymentToSave = actPayment;
//                actPaymentToSave.setMontoPendiente(actPayment.getMontoPendiente().subtract(detail.getTotal()));
//                actPagoProgramacionRepository.save(actPaymentToSave);
//
//            }
//
//        }
//        List<ActPago> listActPaymentDetailResult = actPagoRepository.saveAll(list);
//
//        for (ActPago actPago : listActPaymentDetailResult) {
//            List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(actPago.getSegUsuario().getId());
//            actCajaTurno = actCajaTurno.stream()
//                    .filter(item -> item.getEstado().equals("1"))
//                    .collect(Collectors.toList());
//            if (actCajaTurno.isEmpty()) {
//                throw new Exception("El usuario no tiene caja aperturada");
//            }
//            ActCajaOperacion actCajaOperacion = new ActCajaOperacion();
//            actCajaOperacion.setActCajaTurno(actCajaTurno == null ? null : actCajaTurno.get(0));
//            actCajaOperacion.setActPago(actPago);
//            actCajaOperacion.setActComprobante(null);
//            actCajaOperacion.setFecha(LocalDate.now());
//            actCajaOperacion.setFechaRegistro(LocalDateTime.now());
//            actCajaOperacion.setMonto(actPago.getTotal());
////            actCajaOperacion.setFlagIngreso(actPago.getActPagoProgramacion().getActComprobante().getFlagIsventa());
//            actCajaOperacion.setEstado("1");
//            actCajaOperacionRepository.save(actCajaOperacion);
//        }
//        return listActPaymentDetailResult;
//    }
}

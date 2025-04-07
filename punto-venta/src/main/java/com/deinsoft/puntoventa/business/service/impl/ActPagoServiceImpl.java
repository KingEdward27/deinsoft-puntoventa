package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
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
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.model.CnfMaestro;
import com.deinsoft.puntoventa.business.model.CnfNumComprobante;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoDetalleRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.repository.CnfNumComprobanteRepository;
import com.deinsoft.puntoventa.business.service.BusinessService;
import com.deinsoft.puntoventa.config.AppConfig;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActPagoServiceImpl extends CommonServiceImpl<ActPago,Long, ActPagoRepository> implements ActPagoService {

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

    @Autowired
    ActPagoDetalleRepository actPagoDetalleRepository;

    @Autowired
    BusinessService businessService;

    @Autowired
    AppConfig appConfig;

    public List<ActPago> getAllActPago(ActPago actPago) {
        List<ActPago> actPagoList = (List<ActPago>) actPagoRepository.getAllActPago(
                actPago.getSerie().toUpperCase(), actPago.getNumero().toUpperCase());
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
        List<ActCajaTurno> actCajaTurno = actCajaTurnoRepository.findBySegUsuarioId(actPago.getSegUsuario().getId(),listRoles());
        actCajaTurno = actCajaTurno.stream()
                .filter(item -> item.getEstado().equals("1"))
                .collect(Collectors.toList());
        if (actCajaTurno.isEmpty()) {
            throw new Exception("El usuario no tiene caja aperturada");
        }
        if (actPago.getCnfTipoComprobante().getFlagElectronico().equals("1")) {
            throw new Exception("No puede generar comprobantes electrónicos en esta opción");
        }
        CnfLocal local;
        if (actPago.getListActPagoDetalle().iterator().next()
                .getActPagoProgramacion().getActContrato() != null) {
            local = actPago.getListActPagoDetalle().iterator().next()
                    .getActPagoProgramacion().getActContrato().getCnfLocal();
        } else {
            local = actPago.getListActPagoDetalle().iterator().next()
                    .getActPagoProgramacion().getActComprobante().getCnfLocal();
        }
        List<CnfNumComprobante> numComprobante
                = cnfNumComprobanteRepository.findByCnfTipoComprobanteIdAndCnfLocalId(
                        actPago.getCnfTipoComprobante().getId(), local.getId());
        if (numComprobante.isEmpty()) {
            throw new Exception("No existe numeración para el tipo de comprobante y el local");
        }
        BigDecimal total = BigDecimal.ZERO;
        String detail = "";
        CnfMaestro cnfMaestro = null;

        for (ActPagoDetalle actPagoDetalle : actPago.getListActPagoDetalle()) {
            List<ActPagoProgramacion> list = actPagoProgramacionRepository.findByCnfMaestroId(
                    actPagoDetalle.getActPagoProgramacion().getActComprobante() != null
                    ? actPagoDetalle.getActPagoProgramacion().getActComprobante().getCnfMaestro().getId() : 
                            actPagoDetalle.getActPagoProgramacion().getActContrato().getCnfMaestro().getId(),
                    actPagoDetalle.getActPagoProgramacion().getFechaVencimiento(),listRoles())
                    .stream().filter(predicate -> predicate.getMontoPendiente().compareTo(BigDecimal.ZERO) > 0)
                    .collect(Collectors.toList());

            for (ActPagoProgramacion pendientesAnteriores : list) {
                if (pendientesAnteriores.getFechaVencimiento().compareTo(actPagoDetalle.getActPagoProgramacion().getFechaVencimiento()) < 0
                        && pendientesAnteriores.getMontoPendiente().compareTo(BigDecimal.ZERO) > 0 
                        && !actPago.getListActPagoDetalle().stream()
                                .anyMatch(predicate -> predicate.getActPagoProgramacion().getFechaVencimiento().compareTo(pendientesAnteriores.getFechaVencimiento()) != 0)) {
                    throw new Exception("Debe cancelar las cuotas anteriores. Mes pendiente: " + pendientesAnteriores.getFechaVencimiento().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase());
                }
//                if (pendientesAnteriores.getFechaVencimiento().compareTo(actPagoDetalle.getActPagoProgramacion().getFechaVencimiento()) == 0
//                        && pendientesAnteriores.getMontoPendiente().compareTo(actPagoDetalle.getMonto()) > 0) {
//                    throw new Exception("Debe cancelar las cuotas anteriores. Mes pendiente: " + pendientesAnteriores.getFechaVencimiento().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase());
//                }
            }

        }
        for (ActPagoDetalle actPagoDetalle : actPago.getListActPagoDetalle()) {
            if (actPagoDetalle.getActPagoProgramacion().getAmtToPay().compareTo(BigDecimal.ZERO) == 1) {

//                if (actPagoDetalle.getMonto().compareTo(actPagoDetalle.getActPagoProgramacion().getMontoPendiente()) >= 0) {
//                    actPagoDetalle.setMonto(actPagoDetalle.getActPagoProgramacion().getMontoPendiente());
//                    actPagoDetalle.set(actPagoDetalle.getActPagoProgramacion().getMontoPendiente());
//                }
                ActPagoProgramacion actPaymentToSave = actPagoDetalle.getActPagoProgramacion();
                actPaymentToSave.setMontoPendiente(
                        actPagoDetalle.getActPagoProgramacion().getMontoPendiente().subtract(actPagoDetalle.getMonto()));
                actPagoProgramacionRepository.save(actPaymentToSave);
                if (actPagoDetalle.getActPagoProgramacion().getActComprobante() != null) {
                    cnfMaestro = actPagoDetalle.getActPagoProgramacion().getActComprobante().getCnfMaestro();
                    detail = detail + actPagoDetalle.getActPagoProgramacion().getActComprobante().getSerie() + "-" + actPagoDetalle.getActPagoProgramacion().getActComprobante().getNumero() + "(" + actPagoDetalle.getActPagoProgramacion().getFechaVencimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "), ";
                } else {
                    cnfMaestro = actPagoDetalle.getActPagoProgramacion().getActContrato().getCnfMaestro();
                    detail = detail + actPagoDetalle.getActPagoProgramacion().getActContrato().getSerie() + "-" + actPagoDetalle.getActPagoProgramacion().getActContrato().getNumero() + "(" + actPagoDetalle.getActPagoProgramacion().getFechaVencimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "), ";
                }

            }
            total = total.add(actPagoDetalle.getMonto());
        }
        
        
        CnfNumComprobante cnfNumComprobante = numComprobante.get(0);
        cnfNumComprobante.setUltimoNro(numComprobante.get(0).getUltimoNro() + 1);
        cnfNumComprobanteRepository.save(cnfNumComprobante);
        
        detail = detail.substring(0, detail.length() - 2);
        actPago.setFechaRegistro(LocalDateTime.now());
        actPago.setNumero(String.valueOf(numComprobante.get(0).getUltimoNro() + 1));
        actPago.setTotal(total);
        actPago.setCnfMaestro(cnfMaestro);
        actPago.getListActPagoDetalle().forEach(data -> {
            actPago.addActPagoDetalle(data);
        });

        ActPago actPagoResult = actPagoRepository.save(actPago);

        ActCajaOperacion actCajaOperacion = new ActCajaOperacion();
        actCajaOperacion.setActCajaTurno(actCajaTurno == null ? null : actCajaTurno.get(0));
        actCajaOperacion.setActPago(actPagoResult);
        actCajaOperacion.setActComprobante(null);
        actCajaOperacion.setFecha(LocalDate.now());
        actCajaOperacion.setFechaRegistro(LocalDateTime.now());
        actCajaOperacion.setMonto(total);
        actCajaOperacion.setFlagIngreso("1");
        actCajaOperacion.setEstado("1");
        actCajaOperacion.setDetail(detail);
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

            actCajaOperacionRepository.deleteByActPago(actPago);

            for (ActPagoDetalle actPagoDetalle : actPago.getListActPagoDetalle()) {

                ActPagoProgramacion actPagoProgramacion = actPagoDetalle.getActPagoProgramacion();

                actPagoProgramacion.setMontoPendiente(actPagoProgramacion.getMontoPendiente().add(actPagoDetalle.getMonto()));
                actPagoProgramacionRepository.save(actPagoProgramacion);
            }

            actPagoDetalleRepository.deleteByActPago(actPago);
            actPagoRepository.delete(actPago);
        }
    }

    @Override
    public List<ActPago> getReportActPago(ParamBean paramBean) {
        List<ActPago> actCajaOperacionList = (List<ActPago>) actPagoRepository.getReportActPago(paramBean);

        return actCajaOperacionList.stream()
                .filter(predicate -> {
                    if (paramBean.getCnfLocal().getId() == 0) {
                        return true;
                    } else {

                        return predicate.getListActPagoDetalle().stream().anyMatch(data -> {
                            if (data.getActPagoProgramacion().getActComprobante() != null) {
                                return data.getActPagoProgramacion().getActComprobante().getCnfLocal().getId() == paramBean.getCnfLocal().getId();
                            }
                            if (data.getActPagoProgramacion().getActContrato() != null) {
                                return data.getActPagoProgramacion().getActContrato().getCnfLocal().getId() == paramBean.getCnfLocal().getId();
                            }
                            return false;
                        });
                    }
                })
                .filter(predicate -> {
                    if (paramBean.getCnfMaestro().getId() == 0) {
                        return true;
                    } else {
                        return predicate.getListActPagoDetalle().stream().anyMatch(data -> {
                            if (data.getActPagoProgramacion().getActComprobante() != null) {
                                return data.getActPagoProgramacion().getActComprobante().getCnfMaestro().getId() == paramBean.getCnfMaestro().getId();
                            }
                            if (data.getActPagoProgramacion().getActContrato() != null) {
                                return data.getActPagoProgramacion().getActContrato().getCnfMaestro().getId() == paramBean.getCnfMaestro().getId();
                            }
                            return false;
                        });
                    }

                })
                .collect(Collectors.toList());
    }

    @Override
    public byte[] getPDFLocal(long id, int tipo) throws ParseException, Exception {
        byte[] bytes = businessService.printPago(appConfig.getStaticResourcesPath(), tipo, getActPago(id), false);
        return bytes;
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

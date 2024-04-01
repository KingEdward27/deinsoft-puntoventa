package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.bean.ParamBean;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.service.ActCajaOperacionService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.repository.CnfLocalRepository;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActCajaOperacionServiceImpl extends CommonServiceImpl<ActCajaOperacion, ActCajaOperacionRepository> implements ActCajaOperacionService {

    @Autowired
    ActCajaOperacionRepository actCajaOperacionRepository;

    @Autowired
    CnfLocalRepository cnfLocalRepository;
    
    public List<ActCajaOperacion> getAllActCajaOperacion(ActCajaOperacion actCajaOperacion) {
        List<ActCajaOperacion> actCajaOperacionList 
                = (List<ActCajaOperacion>) actCajaOperacionRepository.getAllActCajaOperacion(actCajaOperacion.getEstado().toUpperCase(),listRoles());
        return actCajaOperacionList;//.stream()
//                .filter(item -> listRoles().stream()
//                .anyMatch(predicate -> predicate.getEmpresa().getId() == item.getActCajaTurno().getActCaja().getCnfEmpresa().getId()))
//                .collect(Collectors.toList());
    }

    public ActCajaOperacion getActCajaOperacion(Long id) {
        ActCajaOperacion actCajaOperacion = null;
        Optional<ActCajaOperacion> actCajaOperacionOptional = actCajaOperacionRepository.findById(id);
        if (actCajaOperacionOptional.isPresent()) {
            actCajaOperacion = actCajaOperacionOptional.get();
        }
        return actCajaOperacion;
    }

    public ActCajaOperacion saveActCajaOperacion(ActCajaOperacion actCajaOperacion) {
        ActCajaOperacion actCajaOperacionResult = actCajaOperacionRepository.save(actCajaOperacion);
        return actCajaOperacionResult;
    }

    public List<ActCajaOperacion> getAllActCajaOperacion() {
        List<ActCajaOperacion> actCajaOperacionList = (List<ActCajaOperacion>) actCajaOperacionRepository.findAll();
        return actCajaOperacionList;
    }

    public List<ActCajaOperacion> getAllActCajaOperacionByActCajaTurno(long id) {
        List<ActCajaOperacion> ActCajaOperacionList = (List<ActCajaOperacion>) actCajaOperacionRepository.findByActCajaTurnoId(id);
        return ActCajaOperacionList;
    }

    public List<ActCajaOperacion> getAllActCajaOperacionByActComprobante(long id) {
        List<ActCajaOperacion> ActCajaOperacionList = (List<ActCajaOperacion>) actCajaOperacionRepository.findByActComprobanteId(id);
        return ActCajaOperacionList;
    }

    public List<ActCajaOperacion> getAllActCajaOperacionByActPago(long id) {
        List<ActCajaOperacion> ActCajaOperacionList = (List<ActCajaOperacion>) actCajaOperacionRepository.findByActPagoId(id);
        return ActCajaOperacionList;
    }

    @Override
    public void delete(long id) {
        ActCajaOperacion actCajaOperacion = null;
        Optional<ActCajaOperacion> actCajaOperacionOptional = actCajaOperacionRepository.findById(id);

        if (actCajaOperacionOptional.isPresent()) {
            actCajaOperacion = actCajaOperacionOptional.get();
            actCajaOperacionRepository.delete(actCajaOperacion);
        }
    }

    @Override
    public List<ActCajaOperacion> getReportActCajaOperacion(ParamBean paramBean) {
        List<ActCajaOperacion> actCajaOperacionList = (List<ActCajaOperacion>) actCajaOperacionRepository.getReportActCajaOperacion(paramBean);
        CnfLocal cnfLocal = cnfLocalRepository.getById(paramBean.getCnfLocal().getId());
        return actCajaOperacionList.stream()
                .filter(predicate -> {
                    System.out.println(predicate.toString());
                    if (paramBean.getCnfLocal().getId() == 0) {
                        return true;
                    } else {
                        if (predicate.getActComprobante() != null && predicate.getActComprobante().getCnfLocal().getId() == paramBean.getCnfLocal().getId()) {
                            return true;
                        } else if (predicate.getActPago() != null) {
                            return predicate.getActPago().getListActPagoDetalle().stream().anyMatch(data -> {
                                if (data.getActPagoProgramacion().getActComprobante() != null) {
                                    return data.getActPagoProgramacion().getActComprobante().getCnfLocal().getId() == paramBean.getCnfLocal().getId();
                                }
                                if (data.getActPagoProgramacion().getActContrato() != null) {
                                    return data.getActPagoProgramacion().getActContrato().getCnfLocal().getId() == paramBean.getCnfLocal().getId();
                                }
                                return false;
                            });
                        } else if (predicate.getActComprobante()== null && predicate.getActPago() == null 
                                && predicate.getActCajaTurno().getActCaja().getCnfEmpresa().getId() == cnfLocal.getCnfEmpresa().getId()) {
                            System.out.println(predicate.toString());
                            return true;
                        }
                        return false;
                    }
                })
                .filter(predicate -> {
                    if (paramBean.getCnfMaestro().getId() == 0) {
                        return true;
                    } else {
                        if (predicate.getActComprobante() != null && predicate.getActComprobante().getCnfMaestro().getId() == paramBean.getCnfMaestro().getId()) {
                            return true;
                        } else if (predicate.getActPago() != null) {
                            return predicate.getActPago().getListActPagoDetalle().stream().anyMatch(data -> {
                                if (data.getActPagoProgramacion().getActComprobante() != null) {
                                    return data.getActPagoProgramacion().getActComprobante().getCnfMaestro().getId() == paramBean.getCnfMaestro().getId();
                                }
                                if (data.getActPagoProgramacion().getActContrato() != null) {
                                    return data.getActPagoProgramacion().getActContrato().getCnfMaestro().getId() == paramBean.getCnfMaestro().getId();
                                }
                                return false;
                            });
                        } else if (predicate.getActComprobante() == null && predicate.getActPago() == null 
                                && predicate.getActCajaTurno().getActCaja().getCnfEmpresa().getId() == cnfLocal.getCnfEmpresa().getId()) {
                            return true;
                        }
                        return false;
                    }

                })
                .collect(Collectors.toList());
    }
}

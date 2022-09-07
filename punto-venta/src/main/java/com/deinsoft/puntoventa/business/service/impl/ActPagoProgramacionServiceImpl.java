package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActPagoProgramacion;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.business.service.ActPagoProgramacionService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActPagoProgramacionServiceImpl
        extends CommonServiceImpl<ActPagoProgramacion, ActPagoProgramacionRepository> implements ActPagoProgramacionService {

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;

    public List<ActPagoProgramacion> getAllActPagoProgramacion(ActPagoProgramacion actPagoProgramacion) {
        
        List<ActPagoProgramacion> actPagoProgramacionList = (List<ActPagoProgramacion>) actPagoProgramacionRepository.getAllActPagoProgramacion();
        return actPagoProgramacionList.stream().filter(item -> !item.getActComprobante().getFlagIsventa().equals("1"))
                        .filter(item -> listRoles().stream()
                                .anyMatch(predicate -> predicate.getEmpresa().getId() == item.getActComprobante().getCnfLocal().getCnfEmpresa().getId()))
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

    public List<ActPagoProgramacion> getAllActPagoProgramacionByCnfMaestro(long id, LocalDate fechaVencimiento) {
        List<ActPagoProgramacion> ActPagoProgramacionList
                = (List<ActPagoProgramacion>) actPagoProgramacionRepository.findByCnfMaestroId(id,fechaVencimiento)
                        .stream().filter(item -> item.getActComprobante().getFlagIsventa().equals("1"))
                        .collect(Collectors.toList());
        return ActPagoProgramacionList;
    }
    public List<ActPagoProgramacion> getAllActPagoProgramacionCompraByCnfMaestro(long id, LocalDate fechaVencimiento) {
        
        List<ActPagoProgramacion> ActPagoProgramacionList
                = (List<ActPagoProgramacion>) actPagoProgramacionRepository.findByCnfMaestroId(id,fechaVencimiento)
                        .stream().filter(item -> !item.getActComprobante().getFlagIsventa().equals("1"))
                        .filter(item -> listRoles().stream()
                                .anyMatch(predicate -> predicate.getEmpresa().getId() == item.getActComprobante().getCnfLocal().getCnfEmpresa().getId()))
                        .collect(Collectors.toList());
        return ActPagoProgramacionList;
    }
    
}

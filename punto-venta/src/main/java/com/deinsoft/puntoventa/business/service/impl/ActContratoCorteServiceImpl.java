package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActContratoMov;
import com.deinsoft.puntoventa.business.repository.ActContratoCorteRepository;
import com.deinsoft.puntoventa.business.service.ActContratoCorteService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.SegUsuario;
import com.deinsoft.puntoventa.business.repository.ActContratoRepository;
import com.deinsoft.puntoventa.business.repository.ActPagoProgramacionRepository;
import com.deinsoft.puntoventa.framework.security.AuthenticationHelper;
import java.math.BigDecimal;

@Service
@Transactional
public class ActContratoCorteServiceImpl extends CommonServiceImpl<ActContratoMov, ActContratoCorteRepository> implements ActContratoCorteService {

    @Autowired
    ActContratoCorteRepository actContratoCorteRepository;
    
    @Autowired
    ActContratoRepository actContratoRepository;

    @Autowired
    ActPagoProgramacionRepository actPagoProgramacionRepository;
    
    @Autowired
    AuthenticationHelper auth;
    
    public List<ActContratoMov> getAllActContratoCorte(ActContratoMov actContratoCorte) {
        List<ActContratoMov> actContratoCorteList = (List<ActContratoMov>) actContratoCorteRepository.getAllActContratoCorte(
                actContratoCorte.getFlagEstado().toUpperCase());
        return actContratoCorteList;
    }

    public ActContratoMov getActContratoCorte(Long id) {
        ActContratoMov actContratoCorte = null;
        Optional<ActContratoMov> actContratoCorteOptional = actContratoCorteRepository.findById(id);
        if (actContratoCorteOptional.isPresent()) {
            actContratoCorte = actContratoCorteOptional.get();
        }
        return actContratoCorte;
    }

    @Transactional
    public ActContratoMov saveActContratoCorte(ActContratoMov actContratoCorte) {
        SegUsuario segUsuario = auth.getLoggedUserdata();
        actContratoCorte.setSegUsuario(segUsuario);
        actContratoCorte.setFlagTipo("0");
        ActContratoMov actContratoCorteResult = actContratoCorteRepository.save(actContratoCorte);
        
//        actPagoProgramacionRepository.deleteByCorte();
        
        actContratoCorte.getActContrato().setFlagEstado("2");
        actContratoRepository.save(actContratoCorte.getActContrato());
        
//        if (actContratoCorte.getActContrato().getCnfPlanContrato().getPrecioReinstalacion().compareTo(BigDecimal.ZERO) > 0) {
//            
//        }
        
        return actContratoCorteResult;
    }

    public List<ActContratoMov> getAllActContratoCorte() {
        List<ActContratoMov> actContratoCorteList = (List<ActContratoMov>) actContratoCorteRepository.findAll();
        return actContratoCorteList;
    }

    public List<ActContratoMov> getAllActContratoCorteByActContrato(long id) {
        List<ActContratoMov> ActContratoCorteList = (List<ActContratoMov>) actContratoCorteRepository.findByActContratoId(id);
        return ActContratoCorteList;
    }

    public List<ActContratoMov> getAllActContratoCorteBySegUsuario(long id) {
        List<ActContratoMov> ActContratoCorteList = (List<ActContratoMov>) actContratoCorteRepository.findBySegUsuarioId(id);
        return ActContratoCorteList;
    }

    @Override
    public void delete(long id) {
        ActContratoMov actContratoCorte = null;
        Optional<ActContratoMov> actContratoCorteOptional = actContratoCorteRepository.findById(id);

        if (actContratoCorteOptional.isPresent()) {
            actContratoCorte = actContratoCorteOptional.get();
            actContratoCorteRepository.delete(actContratoCorte);
        }
    }
}

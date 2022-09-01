package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActCajaTurno;
import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.service.ActCajaTurnoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActCajaTurnoServiceImpl extends CommonServiceImpl<ActCajaTurno, ActCajaTurnoRepository> 
        implements ActCajaTurnoService {

    @Autowired
    ActCajaTurnoRepository actCajaTurnoRepository;

    public List<ActCajaTurno> getAllActCajaTurno(ActCajaTurno actCajaTurno) {
        List<ActCajaTurno> actCajaTurnoList = (List<ActCajaTurno>) actCajaTurnoRepository.getAllActCajaTurno(actCajaTurno.getEstado().toUpperCase());
        return actCajaTurnoList;
    }

    public ActCajaTurno getActCajaTurno(Long id) {
        ActCajaTurno actCajaTurno = null;
        Optional<ActCajaTurno> actCajaTurnoOptional = actCajaTurnoRepository.findById(id);
        if (actCajaTurnoOptional.isPresent()) {
            actCajaTurno = actCajaTurnoOptional.get();
        }
        return actCajaTurno;
    }

    public ActCajaTurno saveActCajaTurno(ActCajaTurno actCajaTurno) throws Exception {
        
        List<ActCajaTurno> actCajaTurnoList = (List<ActCajaTurno>) actCajaTurnoRepository.getAllActCajaTurno("1");
        
        actCajaTurnoList = actCajaTurnoList.stream()
                .filter(item -> item.getActCaja().getId() == actCajaTurno.getActCaja().getId())
                .collect(Collectors.toList());
        
        if (actCajaTurno.getId() == 0 && !actCajaTurnoList.isEmpty()){
            throw new Exception("La caja " + actCajaTurno.getActCaja().getNombre() + " ya se encuentra aperturada");
        }
        ActCajaTurno actCajaTurnoResult = actCajaTurnoRepository.save(actCajaTurno);
        return actCajaTurnoResult;
    }

    public List<ActCajaTurno> getAllActCajaTurno() {
        List<ActCajaTurno> actCajaTurnoList = (List<ActCajaTurno>) actCajaTurnoRepository.findAll();
        return actCajaTurnoList;
    }

    public List<ActCajaTurno> getAllActCajaTurnoBySegUsuario(long id) {
        List<ActCajaTurno> actCajaTurnoList = (List<ActCajaTurno>) actCajaTurnoRepository.findBySegUsuarioId(id);
        return actCajaTurnoList;
    }

    @Override
    public void delete(long id) {
        ActCajaTurno actCajaTurno = null;
        Optional<ActCajaTurno> actCajaTurnoOptional = actCajaTurnoRepository.findById(id);

        if (actCajaTurnoOptional.isPresent()) {
            actCajaTurno = actCajaTurnoOptional.get();
            actCajaTurnoRepository.delete(actCajaTurno);
        }
    }
}

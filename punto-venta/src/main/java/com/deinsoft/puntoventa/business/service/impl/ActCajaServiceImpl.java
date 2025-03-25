package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActCaja;
import com.deinsoft.puntoventa.business.repository.ActCajaRepository;
import com.deinsoft.puntoventa.business.service.ActCajaService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class ActCajaServiceImpl extends CommonServiceImpl<ActCaja,Long, ActCajaRepository> implements ActCajaService {

    @Autowired
    ActCajaRepository actCajaRepository;

    public List<ActCaja> getAllActCaja(ActCaja actCaja) {
        List<ActCaja> actCajaList = (List<ActCaja>) actCajaRepository.getAllActCaja(actCaja.getNombre().toUpperCase(), actCaja.getEstado().toUpperCase());
        return actCajaList;
    }

    public ActCaja getActCaja(Long id) {
        ActCaja actCaja = null;
        Optional<ActCaja> actCajaOptional = actCajaRepository.findById(id);
        if (actCajaOptional.isPresent()) {
            actCaja = actCajaOptional.get();
        }
        return actCaja;
    }

    public ActCaja saveActCaja(ActCaja actCaja) {
        ActCaja actCajaResult = actCajaRepository.save(actCaja);
        return actCajaResult;
    }

    public List<ActCaja> getAllActCaja() {
        List<ActCaja> actCajaList = (List<ActCaja>) actCajaRepository.findAll();
        return actCajaList;
    }

    @Override
    public void delete(long id) {
        ActCaja actCaja = null;
        Optional<ActCaja> actCajaOptional = actCajaRepository.findById(id);

        if (actCajaOptional.isPresent()) {
            actCaja = actCajaOptional.get();
            actCajaRepository.delete(actCaja);
        }
    }
    public List<ActCaja> getAllActCajaByCnfEmpresa(long id) {
        List<ActCaja> CnfLocalList = (List<ActCaja>) actCajaRepository.findByCnfEmpresaId(id);
        return CnfLocalList;
    }
}

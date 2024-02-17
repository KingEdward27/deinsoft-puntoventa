package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.model.ActContratoCorte;

@Service
@Transactional
public interface ActContratoCorteService extends CommonService<ActContratoCorte> {

    public List<ActContratoCorte> getAllActContratoCorte(ActContratoCorte actContratoCorte);

    public ActContratoCorte getActContratoCorte(Long id);

    public ActContratoCorte saveActContratoCorte(ActContratoCorte actContratoCorte);

    public List<ActContratoCorte> getAllActContratoCorte();

    public List<ActContratoCorte> getAllActContratoCorteByActContrato(long id);

    public List<ActContratoCorte> getAllActContratoCorteBySegUsuario(long id);

    public void delete(long id);
}

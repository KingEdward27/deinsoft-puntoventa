package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.model.ActContratoMov;

@Service
@Transactional
public interface ActContratoCorteService extends CommonService<ActContratoMov,Long> {

    public List<ActContratoMov> getAllActContratoCorte(ActContratoMov actContratoCorte);

    public ActContratoMov getActContratoCorte(Long id);

    public ActContratoMov saveActContratoCorte(ActContratoMov actContratoCorte);

    public List<ActContratoMov> getAllActContratoCorte();

    public List<ActContratoMov> getAllActContratoCorteByActContrato(long id);

    public List<ActContratoMov> getAllActContratoCorteBySegUsuario(long id);

    public void delete(long id);
}

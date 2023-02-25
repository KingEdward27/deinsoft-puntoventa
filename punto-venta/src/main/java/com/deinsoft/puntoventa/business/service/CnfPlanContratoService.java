package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.commons.service.CommonService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfPlanContrato;

@Service
@Transactional
public interface CnfPlanContratoService extends CommonService<CnfPlanContrato> {

    public List<CnfPlanContrato> getAllCnfPlanContrato(CnfPlanContrato cnfPlanContrato);

    public CnfPlanContrato getCnfPlanContrato(Long id);

    public CnfPlanContrato saveCnfPlanContrato(CnfPlanContrato cnfPlanContrato);

    public List<CnfPlanContrato> getAllCnfPlanContrato();

    public List<CnfPlanContrato> getAllCnfPlanContratoByCnfEmpresa(long id);

    public void delete(long id);
}

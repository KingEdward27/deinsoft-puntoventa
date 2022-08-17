package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfUnidadMedida;

@Service
@Transactional
public interface CnfUnidadMedidaService extends CommonService<CnfUnidadMedida>{
	
	public List<CnfUnidadMedida> getAllCnfUnidadMedida(CnfUnidadMedida cnfUnidadMedida);
	public CnfUnidadMedida getCnfUnidadMedida(Long id);
	public CnfUnidadMedida saveCnfUnidadMedida(CnfUnidadMedida cnfUnidadMedida);
	public List<CnfUnidadMedida> getAllCnfUnidadMedida();
	public void delete(long id);
}

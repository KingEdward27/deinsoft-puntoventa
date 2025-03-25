package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfPais;

@Service
@Transactional
public interface CnfPaisService extends CommonService<CnfPais,Long>{
	
	public List<CnfPais> getAllCnfPais(CnfPais cnfPais);
	public CnfPais getCnfPais(Long id);
	public CnfPais saveCnfPais(CnfPais cnfPais);
	public List<CnfPais> getAllCnfPais();
	public void delete(long id);
}

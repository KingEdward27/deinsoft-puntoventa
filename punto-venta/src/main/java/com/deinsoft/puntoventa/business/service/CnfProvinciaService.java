package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfProvincia;

@Service
@Transactional
public interface CnfProvinciaService extends CommonService<CnfProvincia>{
	
	public List<CnfProvincia> getAllCnfProvincia(CnfProvincia cnfProvincia);
	public CnfProvincia getCnfProvincia(Long id);
	public CnfProvincia saveCnfProvincia(CnfProvincia cnfProvincia);
	public List<CnfProvincia> getAllCnfProvincia();
  public List<CnfProvincia> getAllCnfProvinciaByCnfRegion(long id);
	public void delete(long id);
}

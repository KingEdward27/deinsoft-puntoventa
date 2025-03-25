package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfRegion;

@Service
@Transactional
public interface CnfRegionService extends CommonService<CnfRegion>{
	
	public List<CnfRegion> getAllCnfRegion(CnfRegion cnfRegion);
	public CnfRegion getCnfRegion(Long id);
	public CnfRegion saveCnfRegion(CnfRegion cnfRegion);
	public List<CnfRegion> getAllCnfRegion();
  public List<CnfRegion> getAllCnfRegionByCnfPais(long id);
	public void delete(long id);
}

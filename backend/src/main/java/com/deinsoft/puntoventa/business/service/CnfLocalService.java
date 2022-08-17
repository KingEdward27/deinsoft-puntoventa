package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.CnfLocal;

@Service
@Transactional
public interface CnfLocalService extends CommonService<CnfLocal>{
	
	public List<CnfLocal> getAllCnfLocal(CnfLocal cnfLocal);
	public CnfLocal getCnfLocal(Long id);
	public CnfLocal saveCnfLocal(CnfLocal cnfLocal);
	public List<CnfLocal> getAllCnfLocal();
  public List<CnfLocal> getAllCnfLocalByCnfEmpresa(long id);
	public void delete(long id);
}

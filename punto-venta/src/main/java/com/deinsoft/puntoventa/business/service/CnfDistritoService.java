package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfDistrito;

@Service
@Transactional
public interface CnfDistritoService extends CommonService<CnfDistrito>{
	
	public List<CnfDistrito> getAllCnfDistrito(CnfDistrito cnfDistrito);
	public CnfDistrito getCnfDistrito(Long id);
	public CnfDistrito saveCnfDistrito(CnfDistrito cnfDistrito);
	public List<CnfDistrito> getAllCnfDistrito();
  public List<CnfDistrito> getAllCnfDistritoByCnfProvincia(long id);
	public void delete(long id);
}

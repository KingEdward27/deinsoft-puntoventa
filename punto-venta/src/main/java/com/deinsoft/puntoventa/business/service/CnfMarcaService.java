package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfMarca;

@Service
@Transactional
public interface CnfMarcaService extends CommonService<CnfMarca>{
	
	public List<CnfMarca> getAllCnfMarca(CnfMarca cnfMarca);
	public CnfMarca getCnfMarca(Long id);
	public CnfMarca saveCnfMarca(CnfMarca cnfMarca);
	public List<CnfMarca> getAllCnfMarca();
  public List<CnfMarca> getAllCnfMarcaByCnfEmpresa(long id);
	public void delete(long id);
}

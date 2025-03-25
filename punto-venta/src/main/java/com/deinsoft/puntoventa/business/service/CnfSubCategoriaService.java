package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfSubCategoria;

@Service
@Transactional
public interface CnfSubCategoriaService extends CommonService<CnfSubCategoria,Long>{
	
	public List<CnfSubCategoria> getAllCnfSubCategoria(CnfSubCategoria cnfSubCategoria);
	public CnfSubCategoria getCnfSubCategoria(Long id);
	public CnfSubCategoria saveCnfSubCategoria(CnfSubCategoria cnfSubCategoria);
	public List<CnfSubCategoria> getAllCnfSubCategoria();
  public List<CnfSubCategoria> getAllCnfSubCategoriaByCnfCategoria(long id);
  public List<CnfSubCategoria> getAllCnfSubCategoriaByCnfEmpresa(long id);
	public void delete(long id);
}

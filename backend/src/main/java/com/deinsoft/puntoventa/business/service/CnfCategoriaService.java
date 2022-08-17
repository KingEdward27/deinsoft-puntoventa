package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.CnfCategoria;

@Service
@Transactional
public interface CnfCategoriaService extends CommonService<CnfCategoria>{
	
	public List<CnfCategoria> getAllCnfCategoria(CnfCategoria cnfCategoria);
	public CnfCategoria getCnfCategoria(Long id);
	public CnfCategoria saveCnfCategoria(CnfCategoria cnfCategoria);
	public List<CnfCategoria> getAllCnfCategoria();
  public List<CnfCategoria> getAllCnfCategoriaByCnfEmpresa(long id);
	public void delete(long id);
}

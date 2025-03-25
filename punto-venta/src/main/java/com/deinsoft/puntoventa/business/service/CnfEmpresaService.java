package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfEmpresa;

@Service
@Transactional
public interface CnfEmpresaService extends CommonService<CnfEmpresa,Long>{
	
	public List<CnfEmpresa> getAllCnfEmpresa(CnfEmpresa cnfEmpresa);
	public CnfEmpresa getCnfEmpresa(Long id);
	public CnfEmpresa saveCnfEmpresa(CnfEmpresa cnfEmpresa);
	public List<CnfEmpresa> getAllCnfEmpresa();
  public List<CnfEmpresa> getAllCnfEmpresaByCnfTipoDocumento(long id);
  public List<CnfEmpresa> getAllCnfEmpresaByCnfDistrito(long id);
	public void delete(long id);
}

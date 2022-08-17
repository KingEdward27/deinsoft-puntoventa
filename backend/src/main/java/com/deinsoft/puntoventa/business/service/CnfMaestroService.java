package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.CnfMaestro;

@Service
@Transactional
public interface CnfMaestroService extends CommonService<CnfMaestro>{
	
	public List<CnfMaestro> getAllCnfMaestro(CnfMaestro cnfMaestro);
	public CnfMaestro getCnfMaestro(Long id);
	public CnfMaestro saveCnfMaestro(CnfMaestro cnfMaestro);
	public List<CnfMaestro> getAllCnfMaestro();
  public List<CnfMaestro> getAllCnfMaestroByCnfTipoDocumento(long id);
  public List<CnfMaestro> getAllCnfMaestroByCnfEmpresa(long id);
  public List<CnfMaestro> getAllCnfMaestroByCnfDistrito(long id);
	public void delete(long id);
}

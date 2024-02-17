package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.CnfMenuTipoSistema;

@Service
@Transactional
public interface CnfMenuTipoSistemaService extends CommonService<CnfMenuTipoSistema>{
	
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistema(CnfMenuTipoSistema cnfMenuTipoSistema);
	public CnfMenuTipoSistema getCnfMenuTipoSistema(Long id);
	public CnfMenuTipoSistema saveCnfMenuTipoSistema(CnfMenuTipoSistema cnfMenuTipoSistema);
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistema();
  public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistemaBySegMenu(long id);
	public void delete(long id);
}

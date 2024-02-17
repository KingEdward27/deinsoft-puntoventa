package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.CnfTipoSistema;

@Service
@Transactional
public interface CnfTipoSistemaService extends CommonService<CnfTipoSistema>{
	
	public List<CnfTipoSistema> getAllCnfTipoSistema(CnfTipoSistema cnfTipoSistema);
	public CnfTipoSistema getCnfTipoSistema(Long id);
	public CnfTipoSistema saveCnfTipoSistema(CnfTipoSistema cnfTipoSistema);
	public List<CnfTipoSistema> getAllCnfTipoSistema();
	public void delete(long id);
}

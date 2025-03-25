package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfTipoDocumento;

@Service
@Transactional
public interface CnfTipoDocumentoService extends CommonService<CnfTipoDocumento,Long>{
	
	public List<CnfTipoDocumento> getAllCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento);
	public CnfTipoDocumento getCnfTipoDocumento(Long id);
	public CnfTipoDocumento saveCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento);
	public List<CnfTipoDocumento> getAllCnfTipoDocumento();
	public void delete(long id);
}

package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfImpuestoCondicion;

@Service
@Transactional
public interface CnfImpuestoCondicionService extends CommonService<CnfImpuestoCondicion,Long>{
	
	public List<CnfImpuestoCondicion> getAllCnfImpuestoCondicion(CnfImpuestoCondicion cnfImpuestoCondicion);
	public CnfImpuestoCondicion getCnfImpuestoCondicion(Long id);
	public CnfImpuestoCondicion saveCnfImpuestoCondicion(CnfImpuestoCondicion cnfImpuestoCondicion);
	public List<CnfImpuestoCondicion> getAllCnfImpuestoCondicion();
	public void delete(long id);
}

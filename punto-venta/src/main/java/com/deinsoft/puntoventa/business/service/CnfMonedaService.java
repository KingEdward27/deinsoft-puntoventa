package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfMoneda;

@Service
@Transactional
public interface CnfMonedaService extends CommonService<CnfMoneda>{
	
	public List<CnfMoneda> getAllCnfMoneda(CnfMoneda cnfMoneda);
	public CnfMoneda getCnfMoneda(Long id);
	public CnfMoneda saveCnfMoneda(CnfMoneda cnfMoneda);
	public List<CnfMoneda> getAllCnfMoneda();
	public void delete(long id);
}

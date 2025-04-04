package com.deinsoft.puntoventa.business.service;

import com.deinsoft.puntoventa.business.commons.service.CommonService;
import com.deinsoft.puntoventa.business.model.CnfMedioPago;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CnfMedioPagoService extends CommonService<CnfMedioPago,Long>{
	
	public List<CnfMedioPago> getAllCnfMedioPago(CnfMedioPago cnfMedioPago);
	public CnfMedioPago getCnfMedioPago(Long id);
	public CnfMedioPago saveCnfMedioPago(CnfMedioPago cnfMedioPago);
	public List<CnfMedioPago> getAllCnfMedioPago();
  public List<CnfMedioPago> getAllCnfMedioPagoByCnfEmpresa(long id);
	public void delete(long id);
}

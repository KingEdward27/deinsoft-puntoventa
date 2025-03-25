package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfFormaPago;

@Service
@Transactional
public interface CnfFormaPagoService extends CommonService<CnfFormaPago,Long>{
	
	public List<CnfFormaPago> getAllCnfFormaPago(CnfFormaPago cnfFormaPago);
	public CnfFormaPago getCnfFormaPago(Long id);
	public CnfFormaPago saveCnfFormaPago(CnfFormaPago cnfFormaPago);
	public List<CnfFormaPago> getAllCnfFormaPago();
  public List<CnfFormaPago> getAllCnfFormaPagoByCnfEmpresa(long id);
	public void delete(long id);
}

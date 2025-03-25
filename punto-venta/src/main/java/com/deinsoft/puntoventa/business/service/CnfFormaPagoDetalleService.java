package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.CnfFormaPagoDetalle;

@Service
@Transactional
public interface CnfFormaPagoDetalleService extends CommonService<CnfFormaPagoDetalle,Long>{
	
	public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalle(CnfFormaPagoDetalle cnfFormaPagoDetalle);
	public CnfFormaPagoDetalle getCnfFormaPagoDetalle(Long id);
	public CnfFormaPagoDetalle saveCnfFormaPagoDetalle(CnfFormaPagoDetalle cnfFormaPagoDetalle);
	public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalle();
  public List<CnfFormaPagoDetalle> getAllCnfFormaPagoDetalleByCnfFormaPago(long id);
	public void delete(long id);
}

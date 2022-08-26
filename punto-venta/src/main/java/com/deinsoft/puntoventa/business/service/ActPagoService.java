package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.ActPago;

@Service
@Transactional
public interface ActPagoService extends CommonService<ActPago>{
	
	public List<ActPago> getAllActPago(ActPago actPago);
	public ActPago getActPago(Long id);
	public ActPago saveActPago(ActPago actPago);
	public List<ActPago> getAllActPago();
  public List<ActPago> getAllActPagoByActPagoProgramacion(long id);
	public void delete(long id);
}

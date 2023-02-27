package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.ActPagoDetalle;

@Service
@Transactional
public interface ActPagoDetalleService extends CommonService<ActPagoDetalle>{
	
	public List<ActPagoDetalle> getAllActPagoDetalle(ActPagoDetalle actPagoDetalle);
	public ActPagoDetalle getActPagoDetalle(Long id);
	public ActPagoDetalle saveActPagoDetalle(ActPagoDetalle actPagoDetalle);
	public List<ActPagoDetalle> getAllActPagoDetalle();
  public List<ActPagoDetalle> getAllActPagoDetalleByActPago(long id);
  public List<ActPagoDetalle> getAllActPagoDetalleByActPagoProgramacion(long id);
	public void delete(long id);
}

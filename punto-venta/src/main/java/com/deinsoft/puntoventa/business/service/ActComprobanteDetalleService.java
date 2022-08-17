package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;

@Service
@Transactional
public interface ActComprobanteDetalleService extends CommonService<ActComprobanteDetalle>{
	
	public List<ActComprobanteDetalle> getAllActComprobanteDetalle(ActComprobanteDetalle actComprobanteDetalle);
	public ActComprobanteDetalle getActComprobanteDetalle(Long id);
	public ActComprobanteDetalle saveActComprobanteDetalle(ActComprobanteDetalle actComprobanteDetalle);
	public List<ActComprobanteDetalle> getAllActComprobanteDetalle();
  public List<ActComprobanteDetalle> getAllActComprobanteDetalleByActComprobante(long id);
  public List<ActComprobanteDetalle> getAllActComprobanteDetalleByCnfProducto(long id);
  public List<ActComprobanteDetalle> getAllActComprobanteDetalleByCnfImpuestoCondicion(long id);
	public void delete(long id);
}

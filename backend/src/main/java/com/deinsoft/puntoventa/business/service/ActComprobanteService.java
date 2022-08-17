package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.ActComprobante;

@Service
@Transactional
public interface ActComprobanteService extends CommonService<ActComprobante>{
	
	public List<ActComprobante> getAllActComprobante(ActComprobante actComprobante);
	public ActComprobante getActComprobante(Long id);
	public ActComprobante saveActComprobante(ActComprobante actComprobante);
	public List<ActComprobante> getAllActComprobante();
  public List<ActComprobante> getAllActComprobanteByActComprobante(long id);
  public List<ActComprobante> getAllActComprobanteByCnfMaestro(long id);
  public List<ActComprobante> getAllActComprobanteByCnfFormaPago(long id);
  public List<ActComprobante> getAllActComprobanteByCnfMoneda(long id);
  public List<ActComprobante> getAllActComprobanteByCnfLocal(long id);
  public List<ActComprobante> getAllActComprobanteByCnfTipoComprobante(long id);
  public List<ActComprobante> getAllActComprobanteByInvAlmacen(long id);
	public void delete(long id);
}

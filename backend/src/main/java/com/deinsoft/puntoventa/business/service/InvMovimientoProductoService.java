package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.InvMovimientoProducto;

@Service
@Transactional
public interface InvMovimientoProductoService extends CommonService<InvMovimientoProducto>{
	
	public List<InvMovimientoProducto> getAllInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto);
	public InvMovimientoProducto getInvMovimientoProducto(Long id);
	public InvMovimientoProducto saveInvMovimientoProducto(InvMovimientoProducto invMovimientoProducto);
	public List<InvMovimientoProducto> getAllInvMovimientoProducto();
  public List<InvMovimientoProducto> getAllInvMovimientoProductoByInvAlmacen(long id);
  public List<InvMovimientoProducto> getAllInvMovimientoProductoByCnfProducto(long id);
  public List<InvMovimientoProducto> getAllInvMovimientoProductoByActComprobante(long id);
	public void delete(long id);
}

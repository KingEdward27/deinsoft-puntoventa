package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.InvAlmacenProducto;

@Service
@Transactional
public interface InvAlmacenProductoService extends CommonService<InvAlmacenProducto>{
	
	public List<InvAlmacenProducto> getAllInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto);
	public InvAlmacenProducto getInvAlmacenProducto(Long id);
	public InvAlmacenProducto saveInvAlmacenProducto(InvAlmacenProducto invAlmacenProducto);
	public List<InvAlmacenProducto> getAllInvAlmacenProducto();
  public List<InvAlmacenProducto> getAllInvAlmacenProductoByInvAlmacen(long id);
  public List<InvAlmacenProducto> getAllInvAlmacenProductoByCnfProducto(long id);
	public void delete(long id);
}

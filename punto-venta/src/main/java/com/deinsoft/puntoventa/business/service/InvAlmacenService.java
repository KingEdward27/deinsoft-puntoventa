package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.commons.service.CommonService;

import com.deinsoft.puntoventa.business.model.InvAlmacen;

@Service
@Transactional
public interface InvAlmacenService extends CommonService<InvAlmacen,Long>{
	
	public List<InvAlmacen> getAllInvAlmacen(InvAlmacen invAlmacen);
	public InvAlmacen getInvAlmacen(Long id);
	public InvAlmacen saveInvAlmacen(InvAlmacen invAlmacen);
	public List<InvAlmacen> getAllInvAlmacen();
  public List<InvAlmacen> getAllInvAlmacenByCnfLocal(long id);
	public void delete(long id);
}

package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.InvMovAlmacenDet;

@Service
@Transactional
public interface InvMovAlmacenDetService extends CommonService<InvMovAlmacenDet>{
	
	public List<InvMovAlmacenDet> getAllInvMovAlmacenDet(InvMovAlmacenDet invMovAlmacenDet);
	public InvMovAlmacenDet getInvMovAlmacenDet(Long id);
	public InvMovAlmacenDet saveInvMovAlmacenDet(InvMovAlmacenDet invMovAlmacenDet);
	public List<InvMovAlmacenDet> getAllInvMovAlmacenDet();
  public List<InvMovAlmacenDet> getAllInvMovAlmacenDetByInvMovAlmacen(long id);
  public List<InvMovAlmacenDet> getAllInvMovAlmacenDetByCnfProducto(long id);
	public void delete(long id);
}

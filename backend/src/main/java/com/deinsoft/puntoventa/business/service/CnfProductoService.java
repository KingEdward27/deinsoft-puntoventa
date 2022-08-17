package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.CnfProducto;

@Service
@Transactional
public interface CnfProductoService extends CommonService<CnfProducto>{
	
	public List<CnfProducto> getAllCnfProducto(CnfProducto cnfProducto);
	public CnfProducto getCnfProducto(Long id);
	public CnfProducto saveCnfProducto(CnfProducto cnfProducto);
	public List<CnfProducto> getAllCnfProducto();
  public List<CnfProducto> getAllCnfProductoByCnfUnidadMedida(long id);
  public List<CnfProducto> getAllCnfProductoByCnfEmpresa(long id);
  public List<CnfProducto> getAllCnfProductoByCnfSubCategoria(long id);
  public List<CnfProducto> getAllCnfProductoByCnfMarca(long id);
	public void delete(long id);
}

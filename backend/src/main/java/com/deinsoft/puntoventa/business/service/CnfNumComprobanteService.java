package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.CnfNumComprobante;

@Service
@Transactional
public interface CnfNumComprobanteService extends CommonService<CnfNumComprobante>{
	
	public List<CnfNumComprobante> getAllCnfNumComprobante(CnfNumComprobante cnfNumComprobante);
	public CnfNumComprobante getCnfNumComprobante(Long id);
	public CnfNumComprobante saveCnfNumComprobante(CnfNumComprobante cnfNumComprobante);
	public List<CnfNumComprobante> getAllCnfNumComprobante();
  public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfTipoComprobante(long id);
  public List<CnfNumComprobante> getAllCnfNumComprobanteByCnfLocal(long id);
	public void delete(long id);
}

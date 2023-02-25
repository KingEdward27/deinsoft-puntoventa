package com.deinsoft.puntoventa.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deinsoft.puntoventa.business.service.commons.CommonService;

import com.deinsoft.puntoventa.business.model.ActContrato;

@Service
@Transactional
public interface ActContratoService extends CommonService<ActContrato>{
	
	public List<ActContrato> getAllActContrato(ActContrato actContrato);
	public ActContrato getActContrato(Long id);
	public ActContrato saveActContrato(ActContrato actContrato);
	public List<ActContrato> getAllActContrato();
  public List<ActContrato> getAllActContratoByCnfMaestro(long id);
  public List<ActContrato> getAllActContratoByCnfLocal(long id);
  public List<ActContrato> getAllActContratoByCnfTipoComprobante(long id);
  public List<ActContrato> getAllActContratoByCnfFormaPago(long id);
  public List<ActContrato> getAllActContratoByCnfPlanContrato(long id);
	public void delete(long id);
}

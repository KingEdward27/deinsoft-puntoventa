package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActContrato;
import com.deinsoft.puntoventa.business.repository.ActContratoRepository;
import com.deinsoft.puntoventa.business.service.ActContratoService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class ActContratoServiceImpl extends CommonServiceImpl<ActContrato,ActContratoRepository> implements ActContratoService  {
	@Autowired 
	ActContratoRepository actContratoRepository;
	
	public List<ActContrato> getAllActContrato(ActContrato actContrato) {
		List<ActContrato> actContratoList = (List<ActContrato>)actContratoRepository.getAllActContrato(actContrato.getSerie().toUpperCase(),actContrato.getNumero().toUpperCase(),actContrato.getObservacion().toUpperCase(),actContrato.getFlagEstado().toUpperCase(),actContrato.getNroPoste().toUpperCase(),actContrato.getUrlMap().toUpperCase());
		return actContratoList;
	}
	public ActContrato getActContrato(Long id) {
		ActContrato actContrato = null; 
		Optional<ActContrato> actContratoOptional = actContratoRepository.findById(id);
		if (actContratoOptional.isPresent()) {
			actContrato = actContratoOptional.get();
		}
		return actContrato;
	}
	
	public ActContrato saveActContrato(ActContrato actContrato) {
		ActContrato actContratoResult = actContratoRepository.save(actContrato);
		return actContratoResult;
	}
	public List<ActContrato> getAllActContrato() {
		List<ActContrato> actContratoList = (List<ActContrato>)actContratoRepository.findAll();
		return actContratoList;
	}
	public List<ActContrato> getAllActContratoByCnfMaestro(long id) {
		List<ActContrato> ActContratoList = (List<ActContrato>)actContratoRepository.findByCnfMaestroId(id);
		return ActContratoList;
	}
	public List<ActContrato> getAllActContratoByCnfLocal(long id) {
		List<ActContrato> ActContratoList = (List<ActContrato>)actContratoRepository.findByCnfLocalId(id);
		return ActContratoList;
	}
	public List<ActContrato> getAllActContratoByCnfTipoComprobante(long id) {
		List<ActContrato> ActContratoList = (List<ActContrato>)actContratoRepository.findByCnfTipoComprobanteId(id);
		return ActContratoList;
	}
	public List<ActContrato> getAllActContratoByCnfFormaPago(long id) {
		List<ActContrato> ActContratoList = (List<ActContrato>)actContratoRepository.findByCnfFormaPagoId(id);
		return ActContratoList;
	}
	public List<ActContrato> getAllActContratoByCnfPlanContrato(long id) {
		List<ActContrato> ActContratoList = (List<ActContrato>)actContratoRepository.findByCnfPlanContratoId(id);
		return ActContratoList;
	}
	@Override
	public void delete(long id) {
		ActContrato actContrato =null;
		Optional<ActContrato> actContratoOptional = actContratoRepository.findById(id);
		
		if (actContratoOptional.isPresent()) {
			actContrato = actContratoOptional.get();
				actContratoRepository.delete(actContrato);
		}
	}
}

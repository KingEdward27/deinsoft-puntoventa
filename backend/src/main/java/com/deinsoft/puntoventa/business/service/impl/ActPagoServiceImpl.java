package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActPago;
import com.deinsoft.puntoventa.business.repository.ActPagoRepository;
import com.deinsoft.puntoventa.business.service.ActPagoService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class ActPagoServiceImpl extends CommonServiceImpl<ActPago,ActPagoRepository> implements ActPagoService  {
	@Autowired 
	ActPagoRepository actPagoRepository;
	
	public List<ActPago> getAllActPago(ActPago actPago) {
		List<ActPago> actPagoList = (List<ActPago>)actPagoRepository.getAllActPago(actPago.getSerie().toUpperCase(),actPago.getNumero().toUpperCase());
		return actPagoList;
	}
	public ActPago getActPago(Long id) {
		ActPago actPago = null; 
		Optional<ActPago> actPagoOptional = actPagoRepository.findById(id);
		if (actPagoOptional.isPresent()) {
			actPago = actPagoOptional.get();
		}
		return actPago;
	}
	
	public ActPago saveActPago(ActPago actPago) {
		ActPago actPagoResult = actPagoRepository.save(actPago);
		return actPagoResult;
	}
	public List<ActPago> getAllActPago() {
		List<ActPago> actPagoList = (List<ActPago>)actPagoRepository.findAll();
		return actPagoList;
	}
	public List<ActPago> getAllActPagoByCnfTipoComprobante(long id) {
		List<ActPago> ActPagoList = (List<ActPago>)actPagoRepository.findByCnfTipoComprobanteId(id);
		return ActPagoList;
	}
	@Override
	public void delete(long id) {
		ActPago actPago =null;
		Optional<ActPago> actPagoOptional = actPagoRepository.findById(id);
		
		if (actPagoOptional.isPresent()) {
			actPago = actPagoOptional.get();
				actPagoRepository.delete(actPago);
		}
	}
}

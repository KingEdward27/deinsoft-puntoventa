package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActPagoDetalle;
import com.deinsoft.puntoventa.business.repository.ActPagoDetalleRepository;
import com.deinsoft.puntoventa.business.service.ActPagoDetalleService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class ActPagoDetalleServiceImpl extends CommonServiceImpl<ActPagoDetalle,ActPagoDetalleRepository> implements ActPagoDetalleService  {
	@Autowired 
	ActPagoDetalleRepository actPagoDetalleRepository;
	
	public List<ActPagoDetalle> getAllActPagoDetalle(ActPagoDetalle actPagoDetalle) {
		List<ActPagoDetalle> actPagoDetalleList = (List<ActPagoDetalle>)actPagoDetalleRepository.getAllActPagoDetalle();
		return actPagoDetalleList;
	}
	public ActPagoDetalle getActPagoDetalle(Long id) {
		ActPagoDetalle actPagoDetalle = null; 
		Optional<ActPagoDetalle> actPagoDetalleOptional = actPagoDetalleRepository.findById(id);
		if (actPagoDetalleOptional.isPresent()) {
			actPagoDetalle = actPagoDetalleOptional.get();
		}
		return actPagoDetalle;
	}
	
	public ActPagoDetalle saveActPagoDetalle(ActPagoDetalle actPagoDetalle) {
		ActPagoDetalle actPagoDetalleResult = actPagoDetalleRepository.save(actPagoDetalle);
		return actPagoDetalleResult;
	}
	public List<ActPagoDetalle> getAllActPagoDetalle() {
		List<ActPagoDetalle> actPagoDetalleList = (List<ActPagoDetalle>)actPagoDetalleRepository.findAll();
		return actPagoDetalleList;
	}
	public List<ActPagoDetalle> getAllActPagoDetalleByActPago(long id) {
		List<ActPagoDetalle> ActPagoDetalleList = (List<ActPagoDetalle>)actPagoDetalleRepository.findByActPagoId(id);
		return ActPagoDetalleList;
	}
	public List<ActPagoDetalle> getAllActPagoDetalleByActPagoProgramacion(long id) {
		List<ActPagoDetalle> ActPagoDetalleList = (List<ActPagoDetalle>)actPagoDetalleRepository.findByActPagoProgramacionId(id);
		return ActPagoDetalleList;
	}
	@Override
	public void delete(long id) {
		ActPagoDetalle actPagoDetalle =null;
		Optional<ActPagoDetalle> actPagoDetalleOptional = actPagoDetalleRepository.findById(id);
		
		if (actPagoDetalleOptional.isPresent()) {
			actPagoDetalle = actPagoDetalleOptional.get();
				actPagoDetalleRepository.delete(actPagoDetalle);
		}
	}
}

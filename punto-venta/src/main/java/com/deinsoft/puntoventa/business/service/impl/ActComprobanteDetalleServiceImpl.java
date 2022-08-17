package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActComprobanteDetalle;
import com.deinsoft.puntoventa.business.repository.ActComprobanteDetalleRepository;
import com.deinsoft.puntoventa.business.service.ActComprobanteDetalleService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class ActComprobanteDetalleServiceImpl extends CommonServiceImpl<ActComprobanteDetalle,ActComprobanteDetalleRepository> implements ActComprobanteDetalleService  {
	@Autowired 
	ActComprobanteDetalleRepository actComprobanteDetalleRepository;
	
	public List<ActComprobanteDetalle> getAllActComprobanteDetalle(ActComprobanteDetalle actComprobanteDetalle) {
		List<ActComprobanteDetalle> actComprobanteDetalleList = (List<ActComprobanteDetalle>)actComprobanteDetalleRepository.getAllActComprobanteDetalle(actComprobanteDetalle.getDescripcion().toUpperCase());
		return actComprobanteDetalleList;
	}
	public ActComprobanteDetalle getActComprobanteDetalle(Long id) {
		ActComprobanteDetalle actComprobanteDetalle = null; 
		Optional<ActComprobanteDetalle> actComprobanteDetalleOptional = actComprobanteDetalleRepository.findById(id);
		if (actComprobanteDetalleOptional.isPresent()) {
			actComprobanteDetalle = actComprobanteDetalleOptional.get();
		}
		return actComprobanteDetalle;
	}
	
	public ActComprobanteDetalle saveActComprobanteDetalle(ActComprobanteDetalle actComprobanteDetalle) {
		ActComprobanteDetalle actComprobanteDetalleResult = actComprobanteDetalleRepository.save(actComprobanteDetalle);
		return actComprobanteDetalleResult;
	}
	public List<ActComprobanteDetalle> getAllActComprobanteDetalle() {
		List<ActComprobanteDetalle> actComprobanteDetalleList = (List<ActComprobanteDetalle>)actComprobanteDetalleRepository.findAll();
		return actComprobanteDetalleList;
	}
	public List<ActComprobanteDetalle> getAllActComprobanteDetalleByActComprobante(long id) {
		List<ActComprobanteDetalle> ActComprobanteDetalleList = (List<ActComprobanteDetalle>)actComprobanteDetalleRepository.findByActComprobanteId(id);
		return ActComprobanteDetalleList;
	}
	public List<ActComprobanteDetalle> getAllActComprobanteDetalleByCnfProducto(long id) {
		List<ActComprobanteDetalle> ActComprobanteDetalleList = (List<ActComprobanteDetalle>)actComprobanteDetalleRepository.findByCnfProductoId(id);
		return ActComprobanteDetalleList;
	}
	public List<ActComprobanteDetalle> getAllActComprobanteDetalleByCnfImpuestoCondicion(long id) {
		List<ActComprobanteDetalle> ActComprobanteDetalleList = (List<ActComprobanteDetalle>)actComprobanteDetalleRepository.findByCnfImpuestoCondicionId(id);
		return ActComprobanteDetalleList;
	}
	@Override
	public void delete(long id) {
		ActComprobanteDetalle actComprobanteDetalle =null;
		Optional<ActComprobanteDetalle> actComprobanteDetalleOptional = actComprobanteDetalleRepository.findById(id);
		
		if (actComprobanteDetalleOptional.isPresent()) {
			actComprobanteDetalle = actComprobanteDetalleOptional.get();
				actComprobanteDetalleRepository.delete(actComprobanteDetalle);
		}
	}
}

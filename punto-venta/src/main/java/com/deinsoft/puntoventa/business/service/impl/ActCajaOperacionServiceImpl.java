package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.ActCajaOperacion;
import com.deinsoft.puntoventa.business.repository.ActCajaOperacionRepository;
import com.deinsoft.puntoventa.business.service.ActCajaOperacionService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class ActCajaOperacionServiceImpl extends CommonServiceImpl<ActCajaOperacion,ActCajaOperacionRepository> implements ActCajaOperacionService  {
	@Autowired 
	ActCajaOperacionRepository actCajaOperacionRepository;
	
	public List<ActCajaOperacion> getAllActCajaOperacion(ActCajaOperacion actCajaOperacion) {
		List<ActCajaOperacion> actCajaOperacionList = (List<ActCajaOperacion>)actCajaOperacionRepository.getAllActCajaOperacion(actCajaOperacion.getEstado().toUpperCase());
		return actCajaOperacionList;
	}
	public ActCajaOperacion getActCajaOperacion(Long id) {
		ActCajaOperacion actCajaOperacion = null; 
		Optional<ActCajaOperacion> actCajaOperacionOptional = actCajaOperacionRepository.findById(id);
		if (actCajaOperacionOptional.isPresent()) {
			actCajaOperacion = actCajaOperacionOptional.get();
		}
		return actCajaOperacion;
	}
	
	public ActCajaOperacion saveActCajaOperacion(ActCajaOperacion actCajaOperacion) {
		ActCajaOperacion actCajaOperacionResult = actCajaOperacionRepository.save(actCajaOperacion);
		return actCajaOperacionResult;
	}
	public List<ActCajaOperacion> getAllActCajaOperacion() {
		List<ActCajaOperacion> actCajaOperacionList = (List<ActCajaOperacion>)actCajaOperacionRepository.findAll();
		return actCajaOperacionList;
	}
	public List<ActCajaOperacion> getAllActCajaOperacionByActCajaTurno(long id) {
		List<ActCajaOperacion> ActCajaOperacionList = (List<ActCajaOperacion>)actCajaOperacionRepository.findByActCajaTurnoId(id);
		return ActCajaOperacionList;
	}
	public List<ActCajaOperacion> getAllActCajaOperacionByActComprobante(long id) {
		List<ActCajaOperacion> ActCajaOperacionList = (List<ActCajaOperacion>)actCajaOperacionRepository.findByActComprobanteId(id);
		return ActCajaOperacionList;
	}
	public List<ActCajaOperacion> getAllActCajaOperacionByActPago(long id) {
		List<ActCajaOperacion> ActCajaOperacionList = (List<ActCajaOperacion>)actCajaOperacionRepository.findByActPagoId(id);
		return ActCajaOperacionList;
	}
	@Override
	public void delete(long id) {
		ActCajaOperacion actCajaOperacion =null;
		Optional<ActCajaOperacion> actCajaOperacionOptional = actCajaOperacionRepository.findById(id);
		
		if (actCajaOperacionOptional.isPresent()) {
			actCajaOperacion = actCajaOperacionOptional.get();
				actCajaOperacionRepository.delete(actCajaOperacion);
		}
	}
}

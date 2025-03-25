package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.SegAccion;
import com.deinsoft.puntoventa.business.repository.SegAccionRepository;
import com.deinsoft.puntoventa.business.service.SegAccionService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class SegAccionServiceImpl extends CommonServiceImpl<SegAccion,Long,SegAccionRepository> implements SegAccionService  {
	@Autowired 
	SegAccionRepository segAccionRepository;
	
	public List<SegAccion> getAllSegAccion(SegAccion segAccion) {
		List<SegAccion> segAccionList = (List<SegAccion>)segAccionRepository.getAllSegAccion(segAccion.getNombre().toUpperCase(),segAccion.getDescripcion().toUpperCase());
		return segAccionList;
	}
	public SegAccion getSegAccion(Long id) {
		SegAccion segAccion = null; 
		Optional<SegAccion> segAccionOptional = segAccionRepository.findById(id);
		if (segAccionOptional.isPresent()) {
			segAccion = segAccionOptional.get();
		}
		return segAccion;
	}
	
	public SegAccion saveSegAccion(SegAccion segAccion) {
		SegAccion segAccionResult = segAccionRepository.save(segAccion);
		return segAccionResult;
	}
	public List<SegAccion> getAllSegAccion() {
		List<SegAccion> segAccionList = (List<SegAccion>)segAccionRepository.findAll();
		return segAccionList;
	}
	@Override
	public void delete(long id) {
		SegAccion segAccion =null;
		Optional<SegAccion> segAccionOptional = segAccionRepository.findById(id);
		
		if (segAccionOptional.isPresent()) {
			segAccion = segAccionOptional.get();
				segAccionRepository.delete(segAccion);
		}
	}
}

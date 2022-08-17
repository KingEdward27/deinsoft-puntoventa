package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfImpuestoCondicion;
import com.deinsoft.puntoventa.business.repository.CnfImpuestoCondicionRepository;
import com.deinsoft.puntoventa.business.service.CnfImpuestoCondicionService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfImpuestoCondicionServiceImpl extends CommonServiceImpl<CnfImpuestoCondicion,CnfImpuestoCondicionRepository> implements CnfImpuestoCondicionService  {
	@Autowired 
	CnfImpuestoCondicionRepository cnfImpuestoCondicionRepository;
	
	public List<CnfImpuestoCondicion> getAllCnfImpuestoCondicion(CnfImpuestoCondicion cnfImpuestoCondicion) {
		List<CnfImpuestoCondicion> cnfImpuestoCondicionList = (List<CnfImpuestoCondicion>)cnfImpuestoCondicionRepository.getAllCnfImpuestoCondicion(cnfImpuestoCondicion.getCodigoSunat().toUpperCase(),cnfImpuestoCondicion.getNombre().toUpperCase());
		return cnfImpuestoCondicionList;
	}
	public CnfImpuestoCondicion getCnfImpuestoCondicion(Long id) {
		CnfImpuestoCondicion cnfImpuestoCondicion = null; 
		Optional<CnfImpuestoCondicion> cnfImpuestoCondicionOptional = cnfImpuestoCondicionRepository.findById(id);
		if (cnfImpuestoCondicionOptional.isPresent()) {
			cnfImpuestoCondicion = cnfImpuestoCondicionOptional.get();
		}
		return cnfImpuestoCondicion;
	}
	
	public CnfImpuestoCondicion saveCnfImpuestoCondicion(CnfImpuestoCondicion cnfImpuestoCondicion) {
		CnfImpuestoCondicion cnfImpuestoCondicionResult = cnfImpuestoCondicionRepository.save(cnfImpuestoCondicion);
		return cnfImpuestoCondicionResult;
	}
	public List<CnfImpuestoCondicion> getAllCnfImpuestoCondicion() {
		List<CnfImpuestoCondicion> cnfImpuestoCondicionList = (List<CnfImpuestoCondicion>)cnfImpuestoCondicionRepository.findAll();
		return cnfImpuestoCondicionList;
	}
	@Override
	public void delete(long id) {
		CnfImpuestoCondicion cnfImpuestoCondicion =null;
		Optional<CnfImpuestoCondicion> cnfImpuestoCondicionOptional = cnfImpuestoCondicionRepository.findById(id);
		
		if (cnfImpuestoCondicionOptional.isPresent()) {
			cnfImpuestoCondicion = cnfImpuestoCondicionOptional.get();
				cnfImpuestoCondicionRepository.delete(cnfImpuestoCondicion);
		}
	}
}

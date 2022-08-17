package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfMoneda;
import com.deinsoft.puntoventa.business.repository.CnfMonedaRepository;
import com.deinsoft.puntoventa.business.service.CnfMonedaService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfMonedaServiceImpl extends CommonServiceImpl<CnfMoneda,CnfMonedaRepository> implements CnfMonedaService  {
	@Autowired 
	CnfMonedaRepository cnfMonedaRepository;
	
	public List<CnfMoneda> getAllCnfMoneda(CnfMoneda cnfMoneda) {
		List<CnfMoneda> cnfMonedaList = (List<CnfMoneda>)cnfMonedaRepository.getAllCnfMoneda(cnfMoneda.getCodigo().toUpperCase(),cnfMoneda.getNombre().toUpperCase(),cnfMoneda.getFlagEstado().toUpperCase());
		return cnfMonedaList;
	}
	public CnfMoneda getCnfMoneda(Long id) {
		CnfMoneda cnfMoneda = null; 
		Optional<CnfMoneda> cnfMonedaOptional = cnfMonedaRepository.findById(id);
		if (cnfMonedaOptional.isPresent()) {
			cnfMoneda = cnfMonedaOptional.get();
		}
		return cnfMoneda;
	}
	
	public CnfMoneda saveCnfMoneda(CnfMoneda cnfMoneda) {
		CnfMoneda cnfMonedaResult = cnfMonedaRepository.save(cnfMoneda);
		return cnfMonedaResult;
	}
	public List<CnfMoneda> getAllCnfMoneda() {
		List<CnfMoneda> cnfMonedaList = (List<CnfMoneda>)cnfMonedaRepository.findAll();
		return cnfMonedaList;
	}
	@Override
	public void delete(long id) {
		CnfMoneda cnfMoneda =null;
		Optional<CnfMoneda> cnfMonedaOptional = cnfMonedaRepository.findById(id);
		
		if (cnfMonedaOptional.isPresent()) {
			cnfMoneda = cnfMonedaOptional.get();
				cnfMonedaRepository.delete(cnfMoneda);
		}
	}
}

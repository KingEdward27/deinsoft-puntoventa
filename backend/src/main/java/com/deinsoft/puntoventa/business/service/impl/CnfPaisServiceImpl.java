package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfPais;
import com.deinsoft.puntoventa.business.repository.CnfPaisRepository;
import com.deinsoft.puntoventa.business.service.CnfPaisService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfPaisServiceImpl extends CommonServiceImpl<CnfPais,CnfPaisRepository> implements CnfPaisService  {
	@Autowired 
	CnfPaisRepository cnfPaisRepository;
	
	public List<CnfPais> getAllCnfPais(CnfPais cnfPais) {
		List<CnfPais> cnfPaisList = (List<CnfPais>)cnfPaisRepository.getAllCnfPais(cnfPais.getNombre().toUpperCase(),cnfPais.getIsocode().toUpperCase());
		return cnfPaisList;
	}
	public CnfPais getCnfPais(Long id) {
		CnfPais cnfPais = null; 
		Optional<CnfPais> cnfPaisOptional = cnfPaisRepository.findById(id);
		if (cnfPaisOptional.isPresent()) {
			cnfPais = cnfPaisOptional.get();
		}
		return cnfPais;
	}
	
	public CnfPais saveCnfPais(CnfPais cnfPais) {
		CnfPais cnfPaisResult = cnfPaisRepository.save(cnfPais);
		return cnfPaisResult;
	}
	public List<CnfPais> getAllCnfPais() {
		List<CnfPais> cnfPaisList = (List<CnfPais>)cnfPaisRepository.findAll();
		return cnfPaisList;
	}
	@Override
	public void delete(long id) {
		CnfPais cnfPais =null;
		Optional<CnfPais> cnfPaisOptional = cnfPaisRepository.findById(id);
		
		if (cnfPaisOptional.isPresent()) {
			cnfPais = cnfPaisOptional.get();
				cnfPaisRepository.delete(cnfPais);
		}
	}
}

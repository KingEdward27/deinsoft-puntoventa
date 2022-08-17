package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfProvincia;
import com.deinsoft.puntoventa.business.repository.CnfProvinciaRepository;
import com.deinsoft.puntoventa.business.service.CnfProvinciaService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfProvinciaServiceImpl extends CommonServiceImpl<CnfProvincia,CnfProvinciaRepository> implements CnfProvinciaService  {
	@Autowired 
	CnfProvinciaRepository cnfProvinciaRepository;
	
	public List<CnfProvincia> getAllCnfProvincia(CnfProvincia cnfProvincia) {
		List<CnfProvincia> cnfProvinciaList = (List<CnfProvincia>)cnfProvinciaRepository.getAllCnfProvincia(cnfProvincia.getNombre().toUpperCase());
		return cnfProvinciaList;
	}
	public CnfProvincia getCnfProvincia(Long id) {
		CnfProvincia cnfProvincia = null; 
		Optional<CnfProvincia> cnfProvinciaOptional = cnfProvinciaRepository.findById(id);
		if (cnfProvinciaOptional.isPresent()) {
			cnfProvincia = cnfProvinciaOptional.get();
		}
		return cnfProvincia;
	}
	
	public CnfProvincia saveCnfProvincia(CnfProvincia cnfProvincia) {
		CnfProvincia cnfProvinciaResult = cnfProvinciaRepository.save(cnfProvincia);
		return cnfProvinciaResult;
	}
	public List<CnfProvincia> getAllCnfProvincia() {
		List<CnfProvincia> cnfProvinciaList = (List<CnfProvincia>)cnfProvinciaRepository.findAll();
		return cnfProvinciaList;
	}
	public List<CnfProvincia> getAllCnfProvinciaByCnfRegion(long id) {
		List<CnfProvincia> CnfProvinciaList = (List<CnfProvincia>)cnfProvinciaRepository.findByCnfRegionId(id);
		return CnfProvinciaList;
	}
	@Override
	public void delete(long id) {
		CnfProvincia cnfProvincia =null;
		Optional<CnfProvincia> cnfProvinciaOptional = cnfProvinciaRepository.findById(id);
		
		if (cnfProvinciaOptional.isPresent()) {
			cnfProvincia = cnfProvinciaOptional.get();
				cnfProvinciaRepository.delete(cnfProvincia);
		}
	}
}

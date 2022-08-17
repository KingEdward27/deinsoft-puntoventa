package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfRegion;
import com.deinsoft.puntoventa.business.repository.CnfRegionRepository;
import com.deinsoft.puntoventa.business.service.CnfRegionService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfRegionServiceImpl extends CommonServiceImpl<CnfRegion,CnfRegionRepository> implements CnfRegionService  {
	@Autowired 
	CnfRegionRepository cnfRegionRepository;
	
	public List<CnfRegion> getAllCnfRegion(CnfRegion cnfRegion) {
		List<CnfRegion> cnfRegionList = (List<CnfRegion>)cnfRegionRepository.getAllCnfRegion(cnfRegion.getNombre().toUpperCase());
		return cnfRegionList;
	}
	public CnfRegion getCnfRegion(Long id) {
		CnfRegion cnfRegion = null; 
		Optional<CnfRegion> cnfRegionOptional = cnfRegionRepository.findById(id);
		if (cnfRegionOptional.isPresent()) {
			cnfRegion = cnfRegionOptional.get();
		}
		return cnfRegion;
	}
	
	public CnfRegion saveCnfRegion(CnfRegion cnfRegion) {
		CnfRegion cnfRegionResult = cnfRegionRepository.save(cnfRegion);
		return cnfRegionResult;
	}
	public List<CnfRegion> getAllCnfRegion() {
		List<CnfRegion> cnfRegionList = (List<CnfRegion>)cnfRegionRepository.findAll();
		return cnfRegionList;
	}
	public List<CnfRegion> getAllCnfRegionByCnfPais(long id) {
		List<CnfRegion> CnfRegionList = (List<CnfRegion>)cnfRegionRepository.findByCnfPaisId(id);
		return CnfRegionList;
	}
	@Override
	public void delete(long id) {
		CnfRegion cnfRegion =null;
		Optional<CnfRegion> cnfRegionOptional = cnfRegionRepository.findById(id);
		
		if (cnfRegionOptional.isPresent()) {
			cnfRegion = cnfRegionOptional.get();
				cnfRegionRepository.delete(cnfRegion);
		}
	}
}

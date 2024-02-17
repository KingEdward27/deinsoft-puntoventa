package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfMenuTipoSistema;
import com.deinsoft.puntoventa.business.repository.CnfMenuTipoSistemaRepository;
import com.deinsoft.puntoventa.business.service.CnfMenuTipoSistemaService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfMenuTipoSistemaServiceImpl extends CommonServiceImpl<CnfMenuTipoSistema,CnfMenuTipoSistemaRepository> implements CnfMenuTipoSistemaService  {
	@Autowired 
	CnfMenuTipoSistemaRepository cnfMenuTipoSistemaRepository;
	
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistema(CnfMenuTipoSistema cnfMenuTipoSistema) {
		List<CnfMenuTipoSistema> cnfMenuTipoSistemaList = (List<CnfMenuTipoSistema>)cnfMenuTipoSistemaRepository.getAllCnfMenuTipoSistema();
		return cnfMenuTipoSistemaList;
	}
	public CnfMenuTipoSistema getCnfMenuTipoSistema(Long id) {
		CnfMenuTipoSistema cnfMenuTipoSistema = null; 
		Optional<CnfMenuTipoSistema> cnfMenuTipoSistemaOptional = cnfMenuTipoSistemaRepository.findById(id);
		if (cnfMenuTipoSistemaOptional.isPresent()) {
			cnfMenuTipoSistema = cnfMenuTipoSistemaOptional.get();
		}
		return cnfMenuTipoSistema;
	}
	
	public CnfMenuTipoSistema saveCnfMenuTipoSistema(CnfMenuTipoSistema cnfMenuTipoSistema) {
		CnfMenuTipoSistema cnfMenuTipoSistemaResult = cnfMenuTipoSistemaRepository.save(cnfMenuTipoSistema);
		return cnfMenuTipoSistemaResult;
	}
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistema() {
		List<CnfMenuTipoSistema> cnfMenuTipoSistemaList = (List<CnfMenuTipoSistema>)cnfMenuTipoSistemaRepository.findAll();
		return cnfMenuTipoSistemaList;
	}
	public List<CnfMenuTipoSistema> getAllCnfMenuTipoSistemaBySegMenu(long id) {
		List<CnfMenuTipoSistema> CnfMenuTipoSistemaList = (List<CnfMenuTipoSistema>)cnfMenuTipoSistemaRepository.findBySegMenuId(id);
		return CnfMenuTipoSistemaList;
	}
	@Override
	public void delete(long id) {
		CnfMenuTipoSistema cnfMenuTipoSistema =null;
		Optional<CnfMenuTipoSistema> cnfMenuTipoSistemaOptional = cnfMenuTipoSistemaRepository.findById(id);
		
		if (cnfMenuTipoSistemaOptional.isPresent()) {
			cnfMenuTipoSistema = cnfMenuTipoSistemaOptional.get();
				cnfMenuTipoSistemaRepository.delete(cnfMenuTipoSistema);
		}
	}
}

package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfTipoSistema;
import com.deinsoft.puntoventa.business.repository.CnfTipoSistemaRepository;
import com.deinsoft.puntoventa.business.service.CnfTipoSistemaService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfTipoSistemaServiceImpl extends CommonServiceImpl<CnfTipoSistema,CnfTipoSistemaRepository> implements CnfTipoSistemaService  {
	@Autowired 
	CnfTipoSistemaRepository cnfTipoSistemaRepository;
	
	public List<CnfTipoSistema> getAllCnfTipoSistema(CnfTipoSistema cnfTipoSistema) {
		List<CnfTipoSistema> cnfTipoSistemaList = (List<CnfTipoSistema>)cnfTipoSistemaRepository.getAllCnfTipoSistema(cnfTipoSistema.getNombre().toUpperCase());
		return cnfTipoSistemaList;
	}
	public CnfTipoSistema getCnfTipoSistema(Long id) {
		CnfTipoSistema cnfTipoSistema = null; 
		Optional<CnfTipoSistema> cnfTipoSistemaOptional = cnfTipoSistemaRepository.findById(id);
		if (cnfTipoSistemaOptional.isPresent()) {
			cnfTipoSistema = cnfTipoSistemaOptional.get();
		}
		return cnfTipoSistema;
	}
	
	public CnfTipoSistema saveCnfTipoSistema(CnfTipoSistema cnfTipoSistema) {
		CnfTipoSistema cnfTipoSistemaResult = cnfTipoSistemaRepository.save(cnfTipoSistema);
		return cnfTipoSistemaResult;
	}
	public List<CnfTipoSistema> getAllCnfTipoSistema() {
		List<CnfTipoSistema> cnfTipoSistemaList = (List<CnfTipoSistema>)cnfTipoSistemaRepository.findAll();
		return cnfTipoSistemaList;
	}
	@Override
	public void delete(long id) {
		CnfTipoSistema cnfTipoSistema =null;
		Optional<CnfTipoSistema> cnfTipoSistemaOptional = cnfTipoSistemaRepository.findById(id);
		
		if (cnfTipoSistemaOptional.isPresent()) {
			cnfTipoSistema = cnfTipoSistemaOptional.get();
				cnfTipoSistemaRepository.delete(cnfTipoSistema);
		}
	}
}

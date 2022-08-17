package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfLocal;
import com.deinsoft.puntoventa.business.repository.CnfLocalRepository;
import com.deinsoft.puntoventa.business.service.CnfLocalService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfLocalServiceImpl extends CommonServiceImpl<CnfLocal,CnfLocalRepository> implements CnfLocalService  {
	@Autowired 
	CnfLocalRepository cnfLocalRepository;
	
	public List<CnfLocal> getAllCnfLocal(CnfLocal cnfLocal) {
		List<CnfLocal> cnfLocalList = (List<CnfLocal>)cnfLocalRepository.getAllCnfLocal(cnfLocal.getNombre().toUpperCase(),cnfLocal.getDireccion().toUpperCase());
		return cnfLocalList;
	}
	public CnfLocal getCnfLocal(Long id) {
		CnfLocal cnfLocal = null; 
		Optional<CnfLocal> cnfLocalOptional = cnfLocalRepository.findById(id);
		if (cnfLocalOptional.isPresent()) {
			cnfLocal = cnfLocalOptional.get();
		}
		return cnfLocal;
	}
	
	public CnfLocal saveCnfLocal(CnfLocal cnfLocal) {
		CnfLocal cnfLocalResult = cnfLocalRepository.save(cnfLocal);
		return cnfLocalResult;
	}
	public List<CnfLocal> getAllCnfLocal() {
		List<CnfLocal> cnfLocalList = (List<CnfLocal>)cnfLocalRepository.findAll();
		return cnfLocalList;
	}
	public List<CnfLocal> getAllCnfLocalByCnfEmpresa(long id) {
		List<CnfLocal> CnfLocalList = (List<CnfLocal>)cnfLocalRepository.findByCnfEmpresaId(id);
		return CnfLocalList;
	}
	@Override
	public void delete(long id) {
		CnfLocal cnfLocal =null;
		Optional<CnfLocal> cnfLocalOptional = cnfLocalRepository.findById(id);
		
		if (cnfLocalOptional.isPresent()) {
			cnfLocal = cnfLocalOptional.get();
				cnfLocalRepository.delete(cnfLocal);
		}
	}
}

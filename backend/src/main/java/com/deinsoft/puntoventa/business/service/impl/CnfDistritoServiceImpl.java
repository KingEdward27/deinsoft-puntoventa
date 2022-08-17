package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfDistrito;
import com.deinsoft.puntoventa.business.repository.CnfDistritoRepository;
import com.deinsoft.puntoventa.business.service.CnfDistritoService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfDistritoServiceImpl extends CommonServiceImpl<CnfDistrito,CnfDistritoRepository> implements CnfDistritoService  {
	@Autowired 
	CnfDistritoRepository cnfDistritoRepository;
	
	public List<CnfDistrito> getAllCnfDistrito(CnfDistrito cnfDistrito) {
		List<CnfDistrito> cnfDistritoList = (List<CnfDistrito>)cnfDistritoRepository.getAllCnfDistrito(cnfDistrito.getNombre().toUpperCase());
		return cnfDistritoList;
	}
	public CnfDistrito getCnfDistrito(Long id) {
		CnfDistrito cnfDistrito = null; 
		Optional<CnfDistrito> cnfDistritoOptional = cnfDistritoRepository.findById(id);
		if (cnfDistritoOptional.isPresent()) {
			cnfDistrito = cnfDistritoOptional.get();
		}
		return cnfDistrito;
	}
	
	public CnfDistrito saveCnfDistrito(CnfDistrito cnfDistrito) {
		CnfDistrito cnfDistritoResult = cnfDistritoRepository.save(cnfDistrito);
		return cnfDistritoResult;
	}
	public List<CnfDistrito> getAllCnfDistrito() {
		List<CnfDistrito> cnfDistritoList = (List<CnfDistrito>)cnfDistritoRepository.findAll();
		return cnfDistritoList;
	}
	public List<CnfDistrito> getAllCnfDistritoByCnfProvincia(long id) {
		List<CnfDistrito> CnfDistritoList = (List<CnfDistrito>)cnfDistritoRepository.findByCnfProvinciaId(id);
		return CnfDistritoList;
	}
	@Override
	public void delete(long id) {
		CnfDistrito cnfDistrito =null;
		Optional<CnfDistrito> cnfDistritoOptional = cnfDistritoRepository.findById(id);
		
		if (cnfDistritoOptional.isPresent()) {
			cnfDistrito = cnfDistritoOptional.get();
				cnfDistritoRepository.delete(cnfDistrito);
		}
	}
}

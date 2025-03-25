package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfCategoria;
import com.deinsoft.puntoventa.business.repository.CnfCategoriaRepository;
import com.deinsoft.puntoventa.business.service.CnfCategoriaService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfCategoriaServiceImpl extends CommonServiceImpl<CnfCategoria,Long,CnfCategoriaRepository> implements CnfCategoriaService  {
	@Autowired 
	CnfCategoriaRepository cnfCategoriaRepository;
	
	public List<CnfCategoria> getAllCnfCategoria(CnfCategoria cnfCategoria) {
		List<CnfCategoria> cnfCategoriaList = (List<CnfCategoria>)cnfCategoriaRepository.getAllCnfCategoria(cnfCategoria.getNombre().toUpperCase(),cnfCategoria.getFlagEstado().toUpperCase());
		return cnfCategoriaList;
	}
	public CnfCategoria getCnfCategoria(Long id) {
		CnfCategoria cnfCategoria = null; 
		Optional<CnfCategoria> cnfCategoriaOptional = cnfCategoriaRepository.findById(id);
		if (cnfCategoriaOptional.isPresent()) {
			cnfCategoria = cnfCategoriaOptional.get();
		}
		return cnfCategoria;
	}
	
	public CnfCategoria saveCnfCategoria(CnfCategoria cnfCategoria) {
		CnfCategoria cnfCategoriaResult = cnfCategoriaRepository.save(cnfCategoria);
		return cnfCategoriaResult;
	}
	public List<CnfCategoria> getAllCnfCategoria() {
		List<CnfCategoria> cnfCategoriaList = (List<CnfCategoria>)cnfCategoriaRepository.findAll();
		return cnfCategoriaList;
	}
	public List<CnfCategoria> getAllCnfCategoriaByCnfEmpresa(long id) {
		List<CnfCategoria> CnfCategoriaList = (List<CnfCategoria>)cnfCategoriaRepository.findByCnfEmpresaId(id);
		return CnfCategoriaList;
	}
	@Override
	public void delete(long id) {
		CnfCategoria cnfCategoria =null;
		Optional<CnfCategoria> cnfCategoriaOptional = cnfCategoriaRepository.findById(id);
		
		if (cnfCategoriaOptional.isPresent()) {
			cnfCategoria = cnfCategoriaOptional.get();
				cnfCategoriaRepository.delete(cnfCategoria);
		}
	}
}

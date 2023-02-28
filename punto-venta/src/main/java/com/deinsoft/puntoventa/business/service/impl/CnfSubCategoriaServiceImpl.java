package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfSubCategoria;
import com.deinsoft.puntoventa.business.repository.CnfSubCategoriaRepository;
import com.deinsoft.puntoventa.business.service.CnfSubCategoriaService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfSubCategoriaServiceImpl extends CommonServiceImpl<CnfSubCategoria,CnfSubCategoriaRepository> implements CnfSubCategoriaService  {
	@Autowired 
	CnfSubCategoriaRepository cnfSubCategoriaRepository;
	
	public List<CnfSubCategoria> getAllCnfSubCategoria(CnfSubCategoria cnfSubCategoria) {
		List<CnfSubCategoria> cnfSubCategoriaList = (List<CnfSubCategoria>)cnfSubCategoriaRepository
                        .getAllCnfSubCategoria(
                                cnfSubCategoria.getCnfEmpresaId(),
                                cnfSubCategoria.getNombre().toUpperCase()
                                ,cnfSubCategoria.getFlagEstado().toUpperCase());
		return cnfSubCategoriaList;
	}
	public CnfSubCategoria getCnfSubCategoria(Long id) {
		CnfSubCategoria cnfSubCategoria = null; 
		Optional<CnfSubCategoria> cnfSubCategoriaOptional = cnfSubCategoriaRepository.findById(id);
		if (cnfSubCategoriaOptional.isPresent()) {
			cnfSubCategoria = cnfSubCategoriaOptional.get();
		}
		return cnfSubCategoria;
	}
	
	public CnfSubCategoria saveCnfSubCategoria(CnfSubCategoria cnfSubCategoria) {
		CnfSubCategoria cnfSubCategoriaResult = cnfSubCategoriaRepository.save(cnfSubCategoria);
		return cnfSubCategoriaResult;
	}
	public List<CnfSubCategoria> getAllCnfSubCategoria() {
		List<CnfSubCategoria> cnfSubCategoriaList = (List<CnfSubCategoria>)cnfSubCategoriaRepository.findAll();
		return cnfSubCategoriaList;
	}
	public List<CnfSubCategoria> getAllCnfSubCategoriaByCnfCategoria(long id) {
		List<CnfSubCategoria> CnfSubCategoriaList = (List<CnfSubCategoria>)cnfSubCategoriaRepository.findByCnfCategoriaId(id);
		return CnfSubCategoriaList;
	}
	public List<CnfSubCategoria> getAllCnfSubCategoriaByCnfEmpresa(long id) {
		List<CnfSubCategoria> CnfSubCategoriaList = (List<CnfSubCategoria>)cnfSubCategoriaRepository.findByCnfEmpresaId(id);
		return CnfSubCategoriaList;
	}
	@Override
	public void delete(long id) {
		CnfSubCategoria cnfSubCategoria =null;
		Optional<CnfSubCategoria> cnfSubCategoriaOptional = cnfSubCategoriaRepository.findById(id);
		
		if (cnfSubCategoriaOptional.isPresent()) {
			cnfSubCategoria = cnfSubCategoriaOptional.get();
				cnfSubCategoriaRepository.delete(cnfSubCategoria);
		}
	}
}

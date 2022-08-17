package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfMarca;
import com.deinsoft.puntoventa.business.repository.CnfMarcaRepository;
import com.deinsoft.puntoventa.business.service.CnfMarcaService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfMarcaServiceImpl extends CommonServiceImpl<CnfMarca,CnfMarcaRepository> implements CnfMarcaService  {
	@Autowired 
	CnfMarcaRepository cnfMarcaRepository;
	
	public List<CnfMarca> getAllCnfMarca(CnfMarca cnfMarca) {
		List<CnfMarca> cnfMarcaList = (List<CnfMarca>)cnfMarcaRepository.getAllCnfMarca(cnfMarca.getNombre().toUpperCase(),cnfMarca.getFlagEstado().toUpperCase());
		return cnfMarcaList;
	}
	public CnfMarca getCnfMarca(Long id) {
		CnfMarca cnfMarca = null; 
		Optional<CnfMarca> cnfMarcaOptional = cnfMarcaRepository.findById(id);
		if (cnfMarcaOptional.isPresent()) {
			cnfMarca = cnfMarcaOptional.get();
		}
		return cnfMarca;
	}
	
	public CnfMarca saveCnfMarca(CnfMarca cnfMarca) {
		CnfMarca cnfMarcaResult = cnfMarcaRepository.save(cnfMarca);
		return cnfMarcaResult;
	}
	public List<CnfMarca> getAllCnfMarca() {
		List<CnfMarca> cnfMarcaList = (List<CnfMarca>)cnfMarcaRepository.findAll();
		return cnfMarcaList;
	}
	public List<CnfMarca> getAllCnfMarcaByCnfEmpresa(long id) {
		List<CnfMarca> CnfMarcaList = (List<CnfMarca>)cnfMarcaRepository.findByCnfEmpresaId(id);
		return CnfMarcaList;
	}
	@Override
	public void delete(long id) {
		CnfMarca cnfMarca =null;
		Optional<CnfMarca> cnfMarcaOptional = cnfMarcaRepository.findById(id);
		
		if (cnfMarcaOptional.isPresent()) {
			cnfMarca = cnfMarcaOptional.get();
				cnfMarcaRepository.delete(cnfMarca);
		}
	}
}

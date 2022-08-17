package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfEmpresa;
import com.deinsoft.puntoventa.business.repository.CnfEmpresaRepository;
import com.deinsoft.puntoventa.business.service.CnfEmpresaService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfEmpresaServiceImpl extends CommonServiceImpl<CnfEmpresa,CnfEmpresaRepository> implements CnfEmpresaService  {
	@Autowired 
	CnfEmpresaRepository cnfEmpresaRepository;
	
	public List<CnfEmpresa> getAllCnfEmpresa(CnfEmpresa cnfEmpresa) {
		List<CnfEmpresa> cnfEmpresaList = (List<CnfEmpresa>)cnfEmpresaRepository.getAllCnfEmpresa(cnfEmpresa.getNombre().toUpperCase(),cnfEmpresa.getDescripcion().toUpperCase(),cnfEmpresa.getNroDocumento().toUpperCase(),cnfEmpresa.getDireccion().toUpperCase(),cnfEmpresa.getTelefono().toUpperCase(),cnfEmpresa.getEstado().toUpperCase(),cnfEmpresa.getToken().toUpperCase());
		return cnfEmpresaList;
	}
	public CnfEmpresa getCnfEmpresa(Long id) {
		CnfEmpresa cnfEmpresa = null; 
		Optional<CnfEmpresa> cnfEmpresaOptional = cnfEmpresaRepository.findById(id);
		if (cnfEmpresaOptional.isPresent()) {
			cnfEmpresa = cnfEmpresaOptional.get();
		}
		return cnfEmpresa;
	}
	
	public CnfEmpresa saveCnfEmpresa(CnfEmpresa cnfEmpresa) {
		CnfEmpresa cnfEmpresaResult = cnfEmpresaRepository.save(cnfEmpresa);
		return cnfEmpresaResult;
	}
	public List<CnfEmpresa> getAllCnfEmpresa() {
		List<CnfEmpresa> cnfEmpresaList = (List<CnfEmpresa>)cnfEmpresaRepository.findAll();
		return cnfEmpresaList;
	}
	public List<CnfEmpresa> getAllCnfEmpresaByCnfTipoDocumento(long id) {
		List<CnfEmpresa> CnfEmpresaList = (List<CnfEmpresa>)cnfEmpresaRepository.findByCnfTipoDocumentoId(id);
		return CnfEmpresaList;
	}
	public List<CnfEmpresa> getAllCnfEmpresaByCnfDistrito(long id) {
		List<CnfEmpresa> CnfEmpresaList = (List<CnfEmpresa>)cnfEmpresaRepository.findByCnfDistritoId(id);
		return CnfEmpresaList;
	}
	@Override
	public void delete(long id) {
		CnfEmpresa cnfEmpresa =null;
		Optional<CnfEmpresa> cnfEmpresaOptional = cnfEmpresaRepository.findById(id);
		
		if (cnfEmpresaOptional.isPresent()) {
			cnfEmpresa = cnfEmpresaOptional.get();
				cnfEmpresaRepository.delete(cnfEmpresa);
		}
	}
}

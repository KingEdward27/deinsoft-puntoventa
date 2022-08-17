package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfMaestro;
import com.deinsoft.puntoventa.business.repository.CnfMaestroRepository;
import com.deinsoft.puntoventa.business.service.CnfMaestroService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfMaestroServiceImpl extends CommonServiceImpl<CnfMaestro,CnfMaestroRepository> implements CnfMaestroService  {
	@Autowired 
	CnfMaestroRepository cnfMaestroRepository;
	
	public List<CnfMaestro> getAllCnfMaestro(CnfMaestro cnfMaestro) {
		List<CnfMaestro> cnfMaestroList = (List<CnfMaestro>)cnfMaestroRepository.getAllCnfMaestro(cnfMaestro.getNroDoc().toUpperCase(),cnfMaestro.getNombres().toUpperCase(),cnfMaestro.getApellidoPaterno().toUpperCase(),cnfMaestro.getApellidoMaterno().toUpperCase(),cnfMaestro.getRazonSocial().toUpperCase(),cnfMaestro.getDireccion().toUpperCase(),cnfMaestro.getCorreo().toUpperCase(),cnfMaestro.getTelefono().toUpperCase(),cnfMaestro.getFlagEstado().toUpperCase());
		return cnfMaestroList;
	}
	public CnfMaestro getCnfMaestro(Long id) {
		CnfMaestro cnfMaestro = null; 
		Optional<CnfMaestro> cnfMaestroOptional = cnfMaestroRepository.findById(id);
		if (cnfMaestroOptional.isPresent()) {
			cnfMaestro = cnfMaestroOptional.get();
		}
		return cnfMaestro;
	}
	
	public CnfMaestro saveCnfMaestro(CnfMaestro cnfMaestro) {
		CnfMaestro cnfMaestroResult = cnfMaestroRepository.save(cnfMaestro);
		return cnfMaestroResult;
	}
	public List<CnfMaestro> getAllCnfMaestro() {
		List<CnfMaestro> cnfMaestroList = (List<CnfMaestro>)cnfMaestroRepository.findAll();
		return cnfMaestroList;
	}
	public List<CnfMaestro> getAllCnfMaestroByCnfTipoDocumento(long id) {
		List<CnfMaestro> CnfMaestroList = (List<CnfMaestro>)cnfMaestroRepository.findByCnfTipoDocumentoId(id);
		return CnfMaestroList;
	}
	public List<CnfMaestro> getAllCnfMaestroByCnfEmpresa(long id) {
		List<CnfMaestro> CnfMaestroList = (List<CnfMaestro>)cnfMaestroRepository.findByCnfEmpresaId(id);
		return CnfMaestroList;
	}
	public List<CnfMaestro> getAllCnfMaestroByCnfDistrito(long id) {
		List<CnfMaestro> CnfMaestroList = (List<CnfMaestro>)cnfMaestroRepository.findByCnfDistritoId(id);
		return CnfMaestroList;
	}
	@Override
	public void delete(long id) {
		CnfMaestro cnfMaestro =null;
		Optional<CnfMaestro> cnfMaestroOptional = cnfMaestroRepository.findById(id);
		
		if (cnfMaestroOptional.isPresent()) {
			cnfMaestro = cnfMaestroOptional.get();
				cnfMaestroRepository.delete(cnfMaestro);
		}
	}
}

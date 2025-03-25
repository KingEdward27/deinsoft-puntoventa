package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfTipoDocumento;
import com.deinsoft.puntoventa.business.repository.CnfTipoDocumentoRepository;
import com.deinsoft.puntoventa.business.service.CnfTipoDocumentoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfTipoDocumentoServiceImpl extends CommonServiceImpl<CnfTipoDocumento,Long,CnfTipoDocumentoRepository> implements CnfTipoDocumentoService  {
	@Autowired 
	CnfTipoDocumentoRepository cnfTipoDocumentoRepository;
	
	public List<CnfTipoDocumento> getAllCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento) {
		List<CnfTipoDocumento> cnfTipoDocumentoList = (List<CnfTipoDocumento>)cnfTipoDocumentoRepository.getAllCnfTipoDocumento(cnfTipoDocumento.getAbreviatura().toUpperCase(),cnfTipoDocumento.getNombre().toUpperCase(),cnfTipoDocumento.getCodigoSunat().toUpperCase(),cnfTipoDocumento.getFlagEstado().toUpperCase());
		return cnfTipoDocumentoList;
	}
	public CnfTipoDocumento getCnfTipoDocumento(Long id) {
		CnfTipoDocumento cnfTipoDocumento = null; 
		Optional<CnfTipoDocumento> cnfTipoDocumentoOptional = cnfTipoDocumentoRepository.findById(id);
		if (cnfTipoDocumentoOptional.isPresent()) {
			cnfTipoDocumento = cnfTipoDocumentoOptional.get();
		}
		return cnfTipoDocumento;
	}
	
	public CnfTipoDocumento saveCnfTipoDocumento(CnfTipoDocumento cnfTipoDocumento) {
		CnfTipoDocumento cnfTipoDocumentoResult = cnfTipoDocumentoRepository.save(cnfTipoDocumento);
		return cnfTipoDocumentoResult;
	}
	public List<CnfTipoDocumento> getAllCnfTipoDocumento() {
		List<CnfTipoDocumento> cnfTipoDocumentoList = (List<CnfTipoDocumento>)cnfTipoDocumentoRepository.findAll();
		return cnfTipoDocumentoList;
	}
	@Override
	public void delete(long id) {
		CnfTipoDocumento cnfTipoDocumento =null;
		Optional<CnfTipoDocumento> cnfTipoDocumentoOptional = cnfTipoDocumentoRepository.findById(id);
		
		if (cnfTipoDocumentoOptional.isPresent()) {
			cnfTipoDocumento = cnfTipoDocumentoOptional.get();
				cnfTipoDocumentoRepository.delete(cnfTipoDocumento);
		}
	}
}

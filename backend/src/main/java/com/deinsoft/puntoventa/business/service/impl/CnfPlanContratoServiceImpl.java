package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfPlanContrato;
import com.deinsoft.puntoventa.business.repository.CnfPlanContratoRepository;
import com.deinsoft.puntoventa.business.service.CnfPlanContratoService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class CnfPlanContratoServiceImpl extends CommonServiceImpl<CnfPlanContrato,CnfPlanContratoRepository> implements CnfPlanContratoService  {
	@Autowired 
	CnfPlanContratoRepository cnfPlanContratoRepository;
	
	public List<CnfPlanContrato> getAllCnfPlanContrato(CnfPlanContrato cnfPlanContrato) {
		List<CnfPlanContrato> cnfPlanContratoList = (List<CnfPlanContrato>)cnfPlanContratoRepository.getAllCnfPlanContrato(cnfPlanContrato.getNombre().toUpperCase());
		return cnfPlanContratoList;
	}
	public CnfPlanContrato getCnfPlanContrato(Long id) {
		CnfPlanContrato cnfPlanContrato = null; 
		Optional<CnfPlanContrato> cnfPlanContratoOptional = cnfPlanContratoRepository.findById(id);
		if (cnfPlanContratoOptional.isPresent()) {
			cnfPlanContrato = cnfPlanContratoOptional.get();
		}
		return cnfPlanContrato;
	}
	
	public CnfPlanContrato saveCnfPlanContrato(CnfPlanContrato cnfPlanContrato) {
		CnfPlanContrato cnfPlanContratoResult = cnfPlanContratoRepository.save(cnfPlanContrato);
		return cnfPlanContratoResult;
	}
	public List<CnfPlanContrato> getAllCnfPlanContrato() {
		List<CnfPlanContrato> cnfPlanContratoList = (List<CnfPlanContrato>)cnfPlanContratoRepository.findAll();
		return cnfPlanContratoList;
	}
	public List<CnfPlanContrato> getAllCnfPlanContratoByCnfEmpresa(long id) {
		List<CnfPlanContrato> CnfPlanContratoList = (List<CnfPlanContrato>)cnfPlanContratoRepository.findByCnfEmpresaId(id);
		return CnfPlanContratoList;
	}
	@Override
	public void delete(long id) {
		CnfPlanContrato cnfPlanContrato =null;
		Optional<CnfPlanContrato> cnfPlanContratoOptional = cnfPlanContratoRepository.findById(id);
		
		if (cnfPlanContratoOptional.isPresent()) {
			cnfPlanContrato = cnfPlanContratoOptional.get();
				cnfPlanContratoRepository.delete(cnfPlanContrato);
		}
	}
}

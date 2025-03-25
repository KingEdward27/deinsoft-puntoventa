package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfUnidadMedida;
import com.deinsoft.puntoventa.business.repository.CnfUnidadMedidaRepository;
import com.deinsoft.puntoventa.business.service.CnfUnidadMedidaService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfUnidadMedidaServiceImpl extends CommonServiceImpl<CnfUnidadMedida,Long,CnfUnidadMedidaRepository> implements CnfUnidadMedidaService  {
	@Autowired 
	CnfUnidadMedidaRepository cnfUnidadMedidaRepository;
	
	public List<CnfUnidadMedida> getAllCnfUnidadMedida(CnfUnidadMedida cnfUnidadMedida) {
		List<CnfUnidadMedida> cnfUnidadMedidaList = (List<CnfUnidadMedida>)cnfUnidadMedidaRepository.getAllCnfUnidadMedida(cnfUnidadMedida.getCodigoSunat().toUpperCase(),cnfUnidadMedida.getNombre().toUpperCase(),cnfUnidadMedida.getFlagEstado().toUpperCase());
		return cnfUnidadMedidaList;
	}
	public CnfUnidadMedida getCnfUnidadMedida(Long id) {
		CnfUnidadMedida cnfUnidadMedida = null; 
		Optional<CnfUnidadMedida> cnfUnidadMedidaOptional = cnfUnidadMedidaRepository.findById(id);
		if (cnfUnidadMedidaOptional.isPresent()) {
			cnfUnidadMedida = cnfUnidadMedidaOptional.get();
		}
		return cnfUnidadMedida;
	}
	
	public CnfUnidadMedida saveCnfUnidadMedida(CnfUnidadMedida cnfUnidadMedida) {
		CnfUnidadMedida cnfUnidadMedidaResult = cnfUnidadMedidaRepository.save(cnfUnidadMedida);
		return cnfUnidadMedidaResult;
	}
	public List<CnfUnidadMedida> getAllCnfUnidadMedida() {
		List<CnfUnidadMedida> cnfUnidadMedidaList = (List<CnfUnidadMedida>)cnfUnidadMedidaRepository.findAll();
		return cnfUnidadMedidaList;
	}
	@Override
	public void delete(long id) {
		CnfUnidadMedida cnfUnidadMedida =null;
		Optional<CnfUnidadMedida> cnfUnidadMedidaOptional = cnfUnidadMedidaRepository.findById(id);
		
		if (cnfUnidadMedidaOptional.isPresent()) {
			cnfUnidadMedida = cnfUnidadMedidaOptional.get();
				cnfUnidadMedidaRepository.delete(cnfUnidadMedida);
		}
	}
}

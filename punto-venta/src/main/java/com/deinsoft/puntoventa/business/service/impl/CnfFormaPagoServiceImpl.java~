package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.CnfFormaPago;
import com.deinsoft.puntoventa.business.repository.CnfFormaPagoRepository;
import com.deinsoft.puntoventa.business.service.CnfFormaPagoService;
import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;

@Service
@Transactional
public class CnfFormaPagoServiceImpl extends CommonServiceImpl<CnfFormaPago,CnfFormaPagoRepository> implements CnfFormaPagoService  {
	@Autowired 
	CnfFormaPagoRepository cnfFormaPagoRepository;
	
	public List<CnfFormaPago> getAllCnfFormaPago(CnfFormaPago cnfFormaPago) {
		List<CnfFormaPago> cnfFormaPagoList = (List<CnfFormaPago>)cnfFormaPagoRepository.getAllCnfFormaPago(cnfFormaPago.getNombre().toUpperCase(),cnfFormaPago.getFlagEstado().toUpperCase());
		return cnfFormaPagoList;
	}
	public CnfFormaPago getCnfFormaPago(Long id) {
		CnfFormaPago cnfFormaPago = null; 
		Optional<CnfFormaPago> cnfFormaPagoOptional = cnfFormaPagoRepository.findById(id);
		if (cnfFormaPagoOptional.isPresent()) {
			cnfFormaPago = cnfFormaPagoOptional.get();
		}
		return cnfFormaPago;
	}
	
	public CnfFormaPago saveCnfFormaPago(CnfFormaPago cnfFormaPago) {
		CnfFormaPago cnfFormaPagoResult = cnfFormaPagoRepository.save(cnfFormaPago);
		return cnfFormaPagoResult;
	}
	public List<CnfFormaPago> getAllCnfFormaPago() {
		List<CnfFormaPago> cnfFormaPagoList = (List<CnfFormaPago>)cnfFormaPagoRepository.findAll();
		return cnfFormaPagoList;
	}
	public List<CnfFormaPago> getAllCnfFormaPagoByCnfEmpresa(long id) {
		List<CnfFormaPago> CnfFormaPagoList = (List<CnfFormaPago>)cnfFormaPagoRepository.findByCnfEmpresaId(id);
		return CnfFormaPagoList;
	}
	@Override
	public void delete(long id) {
		CnfFormaPago cnfFormaPago =null;
		Optional<CnfFormaPago> cnfFormaPagoOptional = cnfFormaPagoRepository.findById(id);
		
		if (cnfFormaPagoOptional.isPresent()) {
			cnfFormaPago = cnfFormaPagoOptional.get();
				cnfFormaPagoRepository.delete(cnfFormaPago);
		}
	}
}

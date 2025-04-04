package com.deinsoft.puntoventa.business.service.impl;

import com.deinsoft.puntoventa.business.commons.service.CommonServiceImpl;
import com.deinsoft.puntoventa.business.model.CnfMedioPago;
import com.deinsoft.puntoventa.business.repository.CnfMedioPagoRepository;
import com.deinsoft.puntoventa.business.service.CnfMedioPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CnfMedioPagoServiceImpl extends CommonServiceImpl<CnfMedioPago,Long,CnfMedioPagoRepository> implements CnfMedioPagoService  {
	@Autowired 
	CnfMedioPagoRepository cnfMedioPagoRepository;
	
	public List<CnfMedioPago> getAllCnfMedioPago(CnfMedioPago cnfMedioPago) {
		List<CnfMedioPago> cnfMedioPagoList = (List<CnfMedioPago>)cnfMedioPagoRepository.getAllCnfMedioPago(cnfMedioPago.getNombre().toUpperCase(),cnfMedioPago.getFlagEstado().toUpperCase());
		return cnfMedioPagoList;
	}
	public CnfMedioPago getCnfMedioPago(Long id) {
		CnfMedioPago cnfMedioPago = null; 
		Optional<CnfMedioPago> cnfMedioPagoOptional = cnfMedioPagoRepository.findById(id);
		if (cnfMedioPagoOptional.isPresent()) {
			cnfMedioPago = cnfMedioPagoOptional.get();
		}
		return cnfMedioPago;
	}
	
	public CnfMedioPago saveCnfMedioPago(CnfMedioPago cnfMedioPago) {
		CnfMedioPago cnfMedioPagoResult = cnfMedioPagoRepository.save(cnfMedioPago);
		return cnfMedioPagoResult;
	}
	public List<CnfMedioPago> getAllCnfMedioPago() {
		List<CnfMedioPago> cnfMedioPagoList = (List<CnfMedioPago>)cnfMedioPagoRepository.findAll();
		return cnfMedioPagoList;
	}
	public List<CnfMedioPago> getAllCnfMedioPagoByCnfEmpresa(long id) {
		List<CnfMedioPago> CnfMedioPagoList = (List<CnfMedioPago>)cnfMedioPagoRepository.findByCnfEmpresaId(id);
		return CnfMedioPagoList;
	}
	@Override
	public void delete(long id) {
		CnfMedioPago cnfMedioPago =null;
		Optional<CnfMedioPago> cnfMedioPagoOptional = cnfMedioPagoRepository.findById(id);
		
		if (cnfMedioPagoOptional.isPresent()) {
			cnfMedioPago = cnfMedioPagoOptional.get();
				cnfMedioPagoRepository.delete(cnfMedioPago);
		}
	}
}

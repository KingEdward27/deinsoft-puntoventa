package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvMovAlmacen;
import com.deinsoft.puntoventa.business.repository.InvMovAlmacenRepository;
import com.deinsoft.puntoventa.business.service.InvMovAlmacenService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class InvMovAlmacenServiceImpl extends CommonServiceImpl<InvMovAlmacen,InvMovAlmacenRepository> implements InvMovAlmacenService  {
	@Autowired 
	InvMovAlmacenRepository invMovAlmacenRepository;
	
	public List<InvMovAlmacen> getAllInvMovAlmacen(InvMovAlmacen invMovAlmacen) {
		List<InvMovAlmacen> invMovAlmacenList = (List<InvMovAlmacen>)invMovAlmacenRepository.getAllInvMovAlmacen(invMovAlmacen.getSerie().toUpperCase(),invMovAlmacen.getNumero().toUpperCase(),invMovAlmacen.getNumeroRef().toUpperCase(),invMovAlmacen.getObservacion().toUpperCase(),invMovAlmacen.getFlagEstado().toUpperCase());
		return invMovAlmacenList;
	}
	public InvMovAlmacen getInvMovAlmacen(Long id) {
		InvMovAlmacen invMovAlmacen = null; 
		Optional<InvMovAlmacen> invMovAlmacenOptional = invMovAlmacenRepository.findById(id);
		if (invMovAlmacenOptional.isPresent()) {
			invMovAlmacen = invMovAlmacenOptional.get();
		}
		return invMovAlmacen;
	}
	
	public InvMovAlmacen saveInvMovAlmacen(InvMovAlmacen invMovAlmacen) {
		InvMovAlmacen invMovAlmacenResult = invMovAlmacenRepository.save(invMovAlmacen);
		return invMovAlmacenResult;
	}
	public List<InvMovAlmacen> getAllInvMovAlmacen() {
		List<InvMovAlmacen> invMovAlmacenList = (List<InvMovAlmacen>)invMovAlmacenRepository.findAll();
		return invMovAlmacenList;
	}
	public List<InvMovAlmacen> getAllInvMovAlmacenByInvTipoMovAlmacen(long id) {
		List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>)invMovAlmacenRepository.findByInvTipoMovAlmacenId(id);
		return InvMovAlmacenList;
	}
	public List<InvMovAlmacen> getAllInvMovAlmacenByCnfMaestro(long id) {
		List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>)invMovAlmacenRepository.findByCnfMaestroId(id);
		return InvMovAlmacenList;
	}
	public List<InvMovAlmacen> getAllInvMovAlmacenByCnfLocal(long id) {
		List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>)invMovAlmacenRepository.findByCnfLocalId(id);
		return InvMovAlmacenList;
	}
	public List<InvMovAlmacen> getAllInvMovAlmacenByCnfTipoComprobante(long id) {
		List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>)invMovAlmacenRepository.findByCnfTipoComprobanteId(id);
		return InvMovAlmacenList;
	}
	public List<InvMovAlmacen> getAllInvMovAlmacenByInvAlmacen(long id) {
		List<InvMovAlmacen> InvMovAlmacenList = (List<InvMovAlmacen>)invMovAlmacenRepository.findByInvAlmacenId(id);
		return InvMovAlmacenList;
	}
	@Override
	public void delete(long id) {
		InvMovAlmacen invMovAlmacen =null;
		Optional<InvMovAlmacen> invMovAlmacenOptional = invMovAlmacenRepository.findById(id);
		
		if (invMovAlmacenOptional.isPresent()) {
			invMovAlmacen = invMovAlmacenOptional.get();
				invMovAlmacenRepository.delete(invMovAlmacen);
		}
	}
}

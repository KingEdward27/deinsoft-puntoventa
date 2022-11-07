package com.deinsoft.puntoventa.business.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deinsoft.puntoventa.business.model.InvTipoMovAlmacen;
import com.deinsoft.puntoventa.business.repository.InvTipoMovAlmacenRepository;
import com.deinsoft.puntoventa.business.service.InvTipoMovAlmacenService;
import com.deinsoft.puntoventa.business.service.commons.CommonServiceImpl;

@Service
@Transactional
public class InvTipoMovAlmacenServiceImpl extends CommonServiceImpl<InvTipoMovAlmacen,InvTipoMovAlmacenRepository> implements InvTipoMovAlmacenService  {
	@Autowired 
	InvTipoMovAlmacenRepository invTipoMovAlmacenRepository;
	
	public List<InvTipoMovAlmacen> getAllInvTipoMovAlmacen(InvTipoMovAlmacen invTipoMovAlmacen) {
		List<InvTipoMovAlmacen> invTipoMovAlmacenList = (List<InvTipoMovAlmacen>)invTipoMovAlmacenRepository.getAllInvTipoMovAlmacen(invTipoMovAlmacen.getNombre().toUpperCase(),invTipoMovAlmacen.getCodigoSunat().toUpperCase(),invTipoMovAlmacen.getNaturaleza().toUpperCase());
		return invTipoMovAlmacenList;
	}
	public InvTipoMovAlmacen getInvTipoMovAlmacen(Long id) {
		InvTipoMovAlmacen invTipoMovAlmacen = null; 
		Optional<InvTipoMovAlmacen> invTipoMovAlmacenOptional = invTipoMovAlmacenRepository.findById(id);
		if (invTipoMovAlmacenOptional.isPresent()) {
			invTipoMovAlmacen = invTipoMovAlmacenOptional.get();
		}
		return invTipoMovAlmacen;
	}
	
	public InvTipoMovAlmacen saveInvTipoMovAlmacen(InvTipoMovAlmacen invTipoMovAlmacen) {
		InvTipoMovAlmacen invTipoMovAlmacenResult = invTipoMovAlmacenRepository.save(invTipoMovAlmacen);
		return invTipoMovAlmacenResult;
	}
	public List<InvTipoMovAlmacen> getAllInvTipoMovAlmacen() {
		List<InvTipoMovAlmacen> invTipoMovAlmacenList = (List<InvTipoMovAlmacen>)invTipoMovAlmacenRepository.findAll();
		return invTipoMovAlmacenList;
	}
	@Override
	public void delete(long id) {
		InvTipoMovAlmacen invTipoMovAlmacen =null;
		Optional<InvTipoMovAlmacen> invTipoMovAlmacenOptional = invTipoMovAlmacenRepository.findById(id);
		
		if (invTipoMovAlmacenOptional.isPresent()) {
			invTipoMovAlmacen = invTipoMovAlmacenOptional.get();
				invTipoMovAlmacenRepository.delete(invTipoMovAlmacen);
		}
	}
}
